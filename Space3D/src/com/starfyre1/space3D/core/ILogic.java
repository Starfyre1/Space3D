/* Copyright (C) Starfyre Enterprises 2024. All rights reserved. */

package com.starfyre1.space3D.core;

public interface ILogic {

	void init() throws Exception;

	void input();

	void update();

	void render();

	void cleanup();

}
