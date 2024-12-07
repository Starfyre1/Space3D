/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.core;

import com.starfyre1.space3D.core.entity.Model;
import com.starfyre1.space3D.test.Launcher;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private final WindowManager mWindowManager;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public RenderManager() {
		mWindowManager = Launcher.getWindowManager();

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public void init() throws Exception {
		// DW fix - this seems to be missing

	}

	public void render(Model model) {
		clear();
		GL30.glBindVertexArray(model.getId());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void cleanup() {

	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
