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

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private int	vertexArrayObject;
	private int	vertexBufferObject;

	private int	vertexCount;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Mesh() {

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public boolean create(float vertices[]) {
		vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(vertexArrayObject);

		vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		glBindVertexArray(0);

		vertexCount = vertices.length / 3;

		return true;
	}

	public void destroy() {
		glDeleteBuffers(vertexBufferObject);
		glDeleteVertexArrays(vertexArrayObject);
	}

	public void draw() {
		glBindVertexArray(vertexArrayObject);

		glEnableVertexAttribArray(0);

		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		glDisableVertexAttribArray(0);

		glBindVertexArray(0);
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
