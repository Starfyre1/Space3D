/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

public class Texture {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private int	mTextureObject;

	private int	mWidth;
	private int	mHeight;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Texture() {
	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/
	public boolean create(String texture_file) {
		int x[] = new int[1];
		int y[] = new int[1];
		int component[] = new int[1];

		ByteBuffer pixels = stbi_load(texture_file, x, y, component, 4);

		if (pixels == null) {
			System.err.println("Failed to load texture: " + texture_file); //$NON-NLS-1$
			return false;
		}

		mTextureObject = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, mTextureObject);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		mWidth = x[0];
		mHeight = y[0];
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, mWidth, mHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

		return true;
	}

	public void destroy() {
		glDeleteTextures(mTextureObject);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, mTextureObject);
	}

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/
	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
