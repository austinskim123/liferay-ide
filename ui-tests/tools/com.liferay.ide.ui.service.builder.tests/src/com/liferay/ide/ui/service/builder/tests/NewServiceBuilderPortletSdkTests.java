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

package com.liferay.ide.ui.service.builder.tests;

import com.liferay.ide.ui.liferay.SwtbotBase;
import com.liferay.ide.ui.liferay.base.SdkSupport;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Joye Luo
 * @author Terry Jia
 */
public class NewServiceBuilderPortletSdkTests extends SwtbotBase {

	@ClassRule
	public static SdkSupport sdk = new SdkSupport(bot);

	@Test
	public void buildServiceOnProject() {
		viewAction.switchLiferayPerspective();

		wizardAction.openNewLiferayPluginProjectWizard();

		String projectName = "test-sb-build-services-portlet";

		wizardAction.newPlugin.prepareServiceBuilderPortletSdk(projectName);

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(projectName);

		viewAction.project.runBuildServices(projectName);

		jobAction.waitForConsoleContent("build.xml", "BUILD SUCCESSFUL", 30 * 1000);

		viewAction.project.closeAndDelete(projectName);
	}

	@Test
	public void buildWSDDOnProject() {
		wizardAction.openNewLiferayPluginProjectWizard();

		String projectName = "test-sb-build-wsdd-portlet";

		wizardAction.newPlugin.prepareServiceBuilderPortletSdk(projectName);

		wizardAction.finish();

		jobAction.waitForIvy();

		jobAction.waitForValidate(projectName);

		viewAction.project.runBuildWSDD(projectName);

		jobAction.waitForConsoleContent("build.xml", "BUILD SUCCESSFUL", 300 * 1000);

		viewAction.project.closeAndDelete(projectName);
	}

}