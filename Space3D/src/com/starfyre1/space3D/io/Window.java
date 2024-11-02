/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private long mWindow;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Window() {

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public void createWindow(int width, int height) {
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!"); //$NON-NLS-1$
		}

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		mWindow = glfwCreateWindow(width, height, "My OpenGL Program", 0, 0); //$NON-NLS-1$
		if (mWindow == 0) {
			throw new IllegalStateException("Failed to create window!"); //$NON-NLS-1$
		}
		glfwMakeContextCurrent(mWindow);
		GL.createCapabilities();

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(mWindow, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

		glfwShowWindow(mWindow);
	}

	/**
	 * Cleans up and destroys the window. Also de-initializes GLFW.
	 */
	public void free() {
		glfwDestroyWindow(mWindow);

		glfwTerminate();
	}

	/**
	 * Polls all events of the window.
	 *
	 * @return True, if the window should close.
	 */
	public boolean update() {
		glfwPollEvents();

		if (glfwWindowShouldClose(mWindow)) {
			return true;
		}
		return false;
	}

	/**
	 * Swaps the buffers to display an image.
	 */
	public void swapBuffers() {
		glfwSwapBuffers(mWindow);
	}
	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
