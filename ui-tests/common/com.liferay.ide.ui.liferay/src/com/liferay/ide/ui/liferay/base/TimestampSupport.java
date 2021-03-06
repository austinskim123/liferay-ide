/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.ui.liferay.base;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

/**
 * @author Terry Jia
 * @author Ying Xu
 */
public class TimestampSupport extends SupportBase {

	public TimestampSupport(SWTWorkbenchBot bot) {
		super(bot);
	}

	@Override
	public void after() {
		_timestamp = 0;
	}

	@Override
	public void before() {
		_timestamp = System.currentTimeMillis();
	}

	public String getName() {
		return "test-" + _timestamp;
	}

	public String getName(String name) {
		return name + "-" + _timestamp;
	}

	private long _timestamp = 0;

}