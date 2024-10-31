/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shader {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private int vertexShader, fragmentShader, program;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Shader() {
	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public boolean create(String shader) {
		int success;

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readSource(shader + ".vs")); //$NON-NLS-1$
		glCompileShader(vertexShader);

		success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(vertexShader));
			return false;
		}

		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readSource(shader + ".fs")); //$NON-NLS-1$
		glCompileShader(fragmentShader);

		success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(fragmentShader));
			return false;
		}

		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);

		glLinkProgram(program);
		success = glGetProgrami(program, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(program));
			return false;
		}

		glValidateProgram(program);
		success = glGetProgrami(program, GL_VALIDATE_STATUS);
		if (success == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(program));
			return false;
		}
		return true;
	}

	public void destroy() {
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
	}

	public void useShader() {
		glUseProgram(program);
	}

	private String readSource(String file) {
		StringBuilder builder = new StringBuilder();

		//			reader = new BufferedReader(new FileInputStream("resource/shaders/" + file)); //$NON-NLS-1$
		try (InputStream in = new FileInputStream("resources/shaders/" + file); //$NON-NLS-1$
						BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n"); //$NON-NLS-1$
			}
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load a shader file!" + System.lineSeparator() + ex.getMessage()); //$NON-NLS-1$
		}

		return builder.toString();
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
