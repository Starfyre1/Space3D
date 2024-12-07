/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.core;

import com.starfyre1.space3D.core.utils.Consts;
import com.starfyre1.space3D.test.Launcher;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class EngineManager {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/
	public static final long	NANOSECOND	= 1000000000L;
	public static final long	FRAMERATE	= 1000;

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private static int			sFPS;
	private static float		sFrameTime	= 1.0f / FRAMERATE;

	private boolean				mIsRunning;

	private WindowManager		mWindowManager;
	private GLFWErrorCallback	mErrorCallback;
	private ILogic				mGameLogic;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	private void init() throws Exception {
		GLFW.glfwSetErrorCallback(mErrorCallback = GLFWErrorCallback.createPrint(System.err));
		mWindowManager = Launcher.getWindowManager();
		mGameLogic = Launcher.getGame();
		mWindowManager.init();
		System.out.println(Thread.currentThread().threadId() + " " + mWindowManager.getWindow());
		mGameLogic.init();
	}

	public void start() throws Exception {
		System.out.println(Thread.currentThread().threadId() + " " + (mWindowManager == null ? "0" : mWindowManager.getWindow()));
		init();
		if (mIsRunning) {
			return;
		}
		run();
	}

	public void run() {
		mIsRunning = true;
		int frames = 0;
		long frameCounter = 0;
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;

		while (mIsRunning) {
			boolean render = false;
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime / (double) NANOSECOND;
			frameCounter += passedTime;

			input();

			while (unprocessedTime > sFrameTime) {
				render = true;
				unprocessedTime -= sFrameTime;

				if (mWindowManager.windowShouldClose()) {
					stop();
				}

				if (frameCounter >= NANOSECOND) {
					setFps(frames);
					mWindowManager.setTitle(Consts.TITLE + " - FPS: " + getFps()); //$NON-NLS-1$
					frames = 0;
					frameCounter = 0;
				}
			}

			if (render) {
				update();
				render();
				frames++;
			}
		}

		cleanup();
	}

	private void stop() {
		if (!mIsRunning) {
			return;
		}
		mIsRunning = false;
	}

	private void input() {
		mGameLogic.input();
	}

	private void render() {
		mGameLogic.render();
		mWindowManager.update();
	}

	private void update() {
		mGameLogic.update();
	}

	private void cleanup() {
		mWindowManager.cleanup();
		mGameLogic.cleanup();
		mErrorCallback.free();
		GLFW.glfwTerminate();
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/** @return The fps. */
	public static int getFps() {
		return sFPS;
	}

	/** @param fps The value to set for fps. */
	public static void setFps(int fps) {
		EngineManager.sFPS = fps;
	}

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
