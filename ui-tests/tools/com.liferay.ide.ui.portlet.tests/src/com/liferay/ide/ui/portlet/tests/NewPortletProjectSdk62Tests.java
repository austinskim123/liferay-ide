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

package com.liferay.ide.ui.portlet.tests;

import com.liferay.ide.ui.liferay.SwtbotBase;
import com.liferay.ide.ui.liferay.base.Sdk62Support;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Terry Jia
 */
@Ignore("ignore for more research")
public class NewPortletProjectSdk62Tests extends SwtbotBase {

	@ClassRule
	public static Sdk62Support sdk62 = new Sdk62Support(bot);

	@Test
	public void createMvcPortletProject() {
		if (envAction.notInternal()) {
			return;
		}

		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		String projectName = "test-mvc-portlet";

		wizardAction.newPlugin.preparePortletSdk(projectName);

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(projectName);

		viewAction.project.closeAndDelete(projectName);
	}

}