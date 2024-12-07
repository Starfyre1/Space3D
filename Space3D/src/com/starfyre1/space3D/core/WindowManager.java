/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.core;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/
	public static final float	FOV		= (float) Math.toRadians(60);
	public static final float	Z_NEAR	= 0.01f;
	public static final float	Z_FAR	= 1000f;

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private final String		mTitle;
	private final Matrix4f		mProjectionMatrix;

	private int					mWidth;
	private int					mHeight;
	private long				mWindowID;

	private boolean				mResize;
	private boolean				mVSync;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public WindowManager(String title, int width, int height, boolean vSync) {
		mTitle = title;
		mWidth = width;
		mHeight = height;
		mVSync = vSync;

		mProjectionMatrix = new Matrix4f();
	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public void init() {
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!"); //$NON-NLS-1$
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		boolean maximised = false;
		if (mWidth == 0 || mHeight == 0) {
			mWidth = 100;
			mHeight = 100;
			glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
			maximised = true;
		}

		mWindowID = glfwCreateWindow(mWidth, mHeight, mTitle, 0, 0);
		if (mWindowID == MemoryUtil.NULL) {
			throw new IllegalStateException("Failed to create GLFW window!"); //$NON-NLS-1$
		}

		System.out.println(Thread.currentThread().threadId() + " " + mWindowID); //$NON-NLS-1$
		glfwMakeContextCurrent(mWindowID);

		GL.createCapabilities();

		glfwSetFramebufferSizeCallback(mWindowID, (window, width, height) -> {
			mWidth = width;
			mHeight = height;
			setResize(true);
		});

		glfwSetKeyCallback(mWindowID, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(mWindowID, true);
			}
		});

		if (maximised) {
			glfwMaximizeWindow(mWindowID);
		} else {
			// Get the thread stack and push a new frame
			//			try (MemoryStack stack = MemoryStack.stackPush()) {
			//				IntBuffer pWidth = stack.mallocInt(1); // int*
			//				IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			//				glfwGetWindowSize(mWindowID, pWidth, pHeight);
			GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(mWindowID, (videoMode.width() - mWidth) / 2, (videoMode.height() - mHeight) / 2);
			//			}

		}
		//		GL.setCapabilities(caps);

		//		Callback debugProc = GLUtil.setupDebugMessageCallback();
		//		glfwSwapInterval(1);
		//
		//		if (isVSync()) {
		//			glfwSwapInterval(1);
		//		}

		glfwShowWindow(mWindowID);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/**
	 * Polls all events of the window.
	 */
	public void update() {
		glfwSwapBuffers(mWindowID);
		glfwPollEvents();

	}

	/**
	 * Cleans up and destroys the window. Also de-initializes GLFW.
	 */
	public void cleanup() {
		glfwDestroyWindow(mWindowID);
	}

	public Matrix4f updateProjectionMatrix() {
		float aspectRatio = (float) mWidth / mHeight;
		return mProjectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
	}

	public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / height;
		return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/
	public void setClearColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}

	public boolean isKeyPressed(int keycode) {
		return glfwGetKey(mWindowID, keycode) == GLFW_PRESS;
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(mWindowID);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		glfwSetWindowTitle(mWindowID, title);
	}

	/** @return The resize. */
	public boolean isResize() {
		return mResize;
	}

	/** @param resize The value to set for resize. */
	public void setResize(boolean resize) {
		mResize = resize;
	}

	/** @return The vSync. */
	public boolean isVSync() {
		return mVSync;
	}

	/** @param vSync The value to set for vSync. */
	public void setVSync(boolean vSync) {
		mVSync = vSync;
	}

	/** @return The projectionMatrix. */
	public Matrix4f getProjectionMatrix() {
		return mProjectionMatrix;
	}

	/** @return The width. */
	public int getWidth() {
		return mWidth;
	}

	/** @return The height. */
	public int getHeight() {
		return mHeight;
	}

	/** @return The window. */
	public long getWindow() {
		return mWindowID;
	}

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
