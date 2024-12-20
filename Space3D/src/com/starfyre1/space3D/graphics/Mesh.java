/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/
	public static final int	VERTEX_SIZE	= 5;

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private int				mVertexArrayObject;
	private int				mVertexBufferObject;

	private int				mVertexCount;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Mesh() {

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public boolean create(float vertices[]) {
		mVertexArrayObject = glGenVertexArrays();
		glBindVertexArray(mVertexArrayObject);

		mVertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, mVertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, VERTEX_SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, VERTEX_SIZE * 4, 12);

		glBindVertexArray(0);

		mVertexCount = vertices.length / VERTEX_SIZE;

		return true;
	}

	public void destroy() {
		glDeleteBuffers(mVertexBufferObject);
		glDeleteVertexArrays(mVertexArrayObject);
	}

	public void draw() {
		glBindVertexArray(mVertexArrayObject);

		glDrawArrays(GL_TRIANGLES, 0, mVertexCount);

		glBindVertexArray(0);
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
