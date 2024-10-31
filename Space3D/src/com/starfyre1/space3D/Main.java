/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D;

import static org.lwjgl.opengl.GL11.*;

import com.starfyre1.space3D.graphics.Mesh;
import com.starfyre1.space3D.graphics.Shader;
import com.starfyre1.space3D.io.Window;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Window window = new Window();

		window.createWindow(640, 480);

		Mesh mesh = new Mesh();
		mesh.create(new float[] { //
						-1, -1, 0, //
						0, 1, 0, //
						1, -1, 0
		});

		Shader shader = new Shader();
		shader.create("basic"); //$NON-NLS-1$

		boolean isRunning = true;

		while (isRunning) {
			isRunning = !window.update();

			glClear(GL_COLOR_BUFFER_BIT);

			shader.useShader();
			mesh.draw();

			window.swapBuffers();

			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		mesh.destroy();
		shader.destroy();

		window.free();
	}
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
