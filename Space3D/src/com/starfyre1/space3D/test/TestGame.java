/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.test;

import com.starfyre1.space3D.core.ILogic;
import com.starfyre1.space3D.core.ObjectLoader;
import com.starfyre1.space3D.core.RenderManager;
import com.starfyre1.space3D.core.WindowManager;
import com.starfyre1.space3D.core.entity.Model;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {

	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private int					mDirection	= 0;
	private float				mColor		= 0.0f;

	private final RenderManager	mRenderer;
	private final ObjectLoader	mLoader;
	private final WindowManager	mWindowManager;

	private Model				mModel;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public TestGame() {
		mRenderer = new RenderManager();
		mWindowManager = Launcher.getWindowManager();
		mLoader = new ObjectLoader();

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	@Override
	public void init() throws Exception {
		mRenderer.init();

		float[] vertices = { //
						-0.5f, 0.5f, 0f, //
						-0.5f, -0.5f, 0f, //
						0.5f, -0.5f, 0f, //
						0.5f, -0.5f, 0f, //
						0.5f, 0.5f, 0f, //
						-0.5f, 0.5f, 0f //
		};

		System.out.println(Thread.currentThread().threadId() + " " + mWindowManager.getWindow());
		mModel = mLoader.loadModel(vertices);
	}

	@Override
	public void input() {
		if (mWindowManager.isKeyPressed(GLFW.GLFW_KEY_UP)) {
			mDirection = 1;
		} else if (mWindowManager.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			mDirection = -1;
		} else {
			mDirection = 0;
		}
	}

	@Override
	public void update() {
		mColor += mDirection * 0.01f;
		if (mColor > 1) {
			mColor = 1.0f;
		} else if (mColor <= 0) {
			mColor = 0.0f;
		}
	}

	@Override
	public void render() {
		if (mWindowManager.isResize()) {
			GL11.glViewport(0, 0, mWindowManager.getWidth(), mWindowManager.getHeight());
			mWindowManager.setResize(true);
		}

		mWindowManager.setClearColor(mColor, mColor, mColor, 0.0f);
		mRenderer.render(mModel);
	}

	@Override
	public void cleanup() {
		mRenderer.cleanup();
		mLoader.cleanup();
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
