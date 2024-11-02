/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
	/*****************************************************************************
	 * Constants
	 ****************************************************************************/

	/*****************************************************************************
	 * Member Variables
	 ****************************************************************************/
	private Vector3f	mPosition;
	private Quaternionf	mRotation;
	private Matrix4f	mProjection;

	/*****************************************************************************
	 * Constructors
	 ****************************************************************************/
	public Camera() {
		mPosition = new Vector3f();
		mRotation = new Quaternionf();
		mProjection = new Matrix4f();
	}

	/*****************************************************************************
	 * Methods
	 ****************************************************************************/

	/*****************************************************************************
	 * Setter's and Getter's
	 ****************************************************************************/
	public Matrix4f getTransformation() {
		Matrix4f returnValue = new Matrix4f();

		returnValue.rotate(mRotation.conjugate(new Quaternionf()));
		returnValue.translate(mPosition.mul(-1, new Vector3f()));

		return returnValue;
	}

	public void setOrthographic(float left, float right, float top, float bottom) {
		mProjection.setOrtho2D(left, right, bottom, top);
	}

	public void setPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		mProjection.setPerspective(fov, aspectRatio, zNear, zFar);
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

	public Matrix4f getProjection() {
		return mProjection;
	}
	/*****************************************************************************
	 * Serialization
	 ****************************************************************************/
}
