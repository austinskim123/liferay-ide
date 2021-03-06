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

package com.liferay.ide.project.core.model.internal;

import com.liferay.ide.project.core.ProjectCore;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.sapphire.DefaultValueService;

/**
 * @author Kuo Zhang
 */
public class IncludeSampleCodeDefaultValueService extends DefaultValueService {

	@Override
	protected String compute() {
		return Boolean.toString(_includeSampleCode);
	}

	@Override
	protected void initDefaultValueService() {
		super.initDefaultValueService();

		IScopeContext[] prefContexts = {DefaultScope.INSTANCE, InstanceScope.INSTANCE};

		_includeSampleCode = Platform.getPreferencesService().getBoolean(
			ProjectCore.PLUGIN_ID, ProjectCore.PREF_INCLUDE_SAMPLE_CODE, true, prefContexts);
	}

	private boolean _includeSampleCode;

}