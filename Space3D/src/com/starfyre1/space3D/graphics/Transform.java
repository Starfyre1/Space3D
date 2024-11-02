/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private Vector3f	mPosition;
	private Quaternionf	mRotation;
	private Vector3f	mScale;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Transform() {
		mPosition = new Vector3f();
		mRotation = new Quaternionf();
		mScale = new Vector3f(1);

	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/
	public Matrix4f getTransformation() {
		Matrix4f returnValue = new Matrix4f();

		returnValue.translate(mPosition);
		returnValue.rotate(mRotation);
		returnValue.scale(mScale);

		return returnValue;
	}

	public Vector3f getPosition() {
		return mPosition;
	}

	public void setPosition(Vector3f position) {
		mPosition = position;
	}

	public Quaternionf getRotation() {
		return mRotation;
	}

	public void setRotation(Quaternionf rotation) {
		mRotation = rotation;
	}

	public Vector3f getScale() {
		return mScale;
	}

	public void setScale(Vector3f scale) {
		mScale = scale;
	}
	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
