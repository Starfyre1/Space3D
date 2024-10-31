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
	private long window;

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
			throw new IllegalStateException("Failed to initialized GLFW!"); //$NON-NLS-1$
		}

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "My LWGFL Program", 0, 0); //$NON-NLS-1$
		//		long window = glfwCreateWindow(640, 480, "My LWGFL Program", glfwGetPrimaryMonitor(), 0); // for full screen //$NON-NLS-1$
		if (window == 0) {
			throw new IllegalStateException("Failed to create window!"); //$NON-NLS-1$
		}

		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);

		glfwShowWindow(window);
	}

	public void free() {
		glfwDestroyWindow(window);

		glfwTerminate();

	}

	public boolean update() {
		glfwPollEvents();

		if (glfwWindowShouldClose(window)) {
			return true;
		}
		return false;

	}

	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
