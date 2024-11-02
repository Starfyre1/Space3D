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
	private int	mVertexShader;
	private int	mFragmentShader;
	private int	mProgram;

	private int	mUniMatProjection;
	private int	mUniMatTransformWorld;
	private int	mUniMatTransformObject;

	private int	mUniSampleTexture;

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

		mVertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(mVertexShader, readSource(shader + ".vs")); //$NON-NLS-1$
		glCompileShader(mVertexShader);

		success = glGetShaderi(mVertexShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Vertex: \n" + glGetShaderInfoLog(mVertexShader)); //$NON-NLS-1$
			return false;
		}

		mFragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(mFragmentShader, readSource(shader + ".fs")); //$NON-NLS-1$
		glCompileShader(mFragmentShader);

		success = glGetShaderi(mFragmentShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Fragment: \n" + glGetShaderInfoLog(mFragmentShader)); //$NON-NLS-1$
			return false;
		}

		mProgram = glCreateProgram();
		glAttachShader(mProgram, mVertexShader);
		glAttachShader(mProgram, mFragmentShader);

		glLinkProgram(mProgram);
		success = glGetProgrami(mProgram, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Program Link: \n" + glGetProgramInfoLog(mProgram)); //$NON-NLS-1$
			return false;
		}
		glValidateProgram(mProgram);
		success = glGetProgrami(mProgram, GL_VALIDATE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Program Validate: \n" + glGetProgramInfoLog(mProgram)); //$NON-NLS-1$
			return false;
		}

		mUniMatProjection = glGetUniformLocation(mProgram, "cameraProjection"); //$NON-NLS-1$
		mUniMatTransformWorld = glGetUniformLocation(mProgram, "transformWorld"); //$NON-NLS-1$
		mUniMatTransformObject = glGetUniformLocation(mProgram, "transformObject"); //$NON-NLS-1$
		mUniSampleTexture = glGetUniformLocation(mProgram, "sampleTexture"); //$NON-NLS-1$

		return true;
	}

	public void destroy() {
		glDetachShader(mProgram, mVertexShader);
		glDetachShader(mProgram, mFragmentShader);
		glDeleteShader(mVertexShader);
		glDeleteShader(mFragmentShader);
		glDeleteProgram(mProgram);
	}

	public void useShader() {
		glUseProgram(mProgram);
	}

	public void setSampleTexture(int sample) {
		if (mUniSampleTexture != -1) {
			glUniform1i(mUniSampleTexture, sample);
		}
	}

	public void setCamera(Camera camera) {
		if (mUniMatProjection != -1) {
			float matrix[] = new float[16];
			camera.getProjection().get(matrix);
			glUniformMatrix4fv(mUniMatProjection, false, matrix);
		}
		if (mUniMatTransformWorld != -1) {
			float matrix[] = new float[16];
			camera.getTransformation().get(matrix);
			glUniformMatrix4fv(mUniMatTransformWorld, false, matrix);
		}
	}

	public void setTransform(Transform transform) {
		if (mUniMatTransformObject != -1) {
			float matrix[] = new float[16];
			transform.getTransformation().get(matrix);
			glUniformMatrix4fv(mUniMatTransformObject, false, matrix);
		}
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
