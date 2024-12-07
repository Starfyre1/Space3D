/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.test;

import com.starfyre1.space3D.core.EngineManager;
import com.starfyre1.space3D.core.WindowManager;
import com.starfyre1.space3D.core.utils.Consts;

public class Launcher {

	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private static WindowManager	sWindowManager;
	private static TestGame			sGame;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sWindowManager = new WindowManager(Consts.TITLE, 1600, 900, false);
		sGame = new TestGame();
		EngineManager engine = new EngineManager();

		try {
			System.out.println(Thread.currentThread().threadId() + " " + sWindowManager.getWindow());
			engine.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//	private static long				window	= 0;
	//
	//	public static void main(String[] args) {
	//		if (!GLFW.glfwInit()) {
	//			throw new RuntimeException("Cannot initialize OPenGL");
	//		}
	//
	//		window = GLFW.glfwCreateWindow(1024, 764, "Ya yeet", 0, 0);
	//		if (0 == window) {
	//			GLFW.glfwTerminate();
	//			throw new RuntimeException("Cannot create window");
	//		}
	//
	//		GLFW.glfwMakeContextCurrent(window);
	//		GL.createCapabilities();
	//
	//		String glVersion = GL11.glGetString(GL11.GL_VERSION);
	//		System.out.println(glVersion);
	//	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/
	/** @return The window. */
	public static WindowManager getWindowManager() {
		return sWindowManager;
	}

	/** @return The game. */
	public static TestGame getGame() {
		return sGame;
	}

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
