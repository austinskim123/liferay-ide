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

package com.liferay.ide.ui.liferay.action;

import com.liferay.ide.ui.liferay.Actions;
import com.liferay.ide.ui.liferay.UIAction;
import com.liferay.ide.ui.swtbot.eclipse.page.ConsoleView;
import com.liferay.ide.ui.swtbot.eclipse.page.DeleteResourcesContinueDialog;
import com.liferay.ide.ui.swtbot.eclipse.page.DeleteResourcesDialog;
import com.liferay.ide.ui.swtbot.eclipse.page.PackageExplorerView;
import com.liferay.ide.ui.swtbot.eclipse.page.ProjectExplorerView;
import com.liferay.ide.ui.swtbot.eclipse.page.ServersView;
import com.liferay.ide.ui.swtbot.page.Dialog;
import com.liferay.ide.ui.swtbot.page.Tree;
import com.liferay.ide.ui.swtbot.page.TreeItem;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;

/**
 * @author Terry Jia
 */
public class ViewAction extends UIAction {

	public ViewAction(SWTWorkbenchBot bot) {
		super(bot);
	}

	public void deleteProject(String name) {
		TreeItem item = getProjects().getTreeItem(name);

		item.doAction(Actions.getDelete());

		_deleteResourcesDialog.getDeleteFromDisk().select();

		_deleteResourcesDialog.confirm();

		long origin = SWTBotPreferences.TIMEOUT;

		SWTBotPreferences.TIMEOUT = 2000;

		try {
			_continueDeleteResourcesDialog.confirm();
		}
		catch (Exception e) {
		}

		try {
			_dialog.confirm();
		}
		catch (Exception e) {
		}

		SWTBotPreferences.TIMEOUT = origin;
	}

	public void deleteProject(String... nodes) {
		TreeItem nodesItem = getProjects().expandNode(nodes);

		nodesItem.doAction(Actions.getDelete());

		_deleteResourcesDialog.getDeleteFromDisk().select();

		_deleteResourcesDialog.confirm();

		long origin = SWTBotPreferences.TIMEOUT;

		SWTBotPreferences.TIMEOUT = 2000;

		try {
			_continueDeleteResourcesDialog.confirm();
		}
		catch (Exception e) {
		}

		try {
			_dialog.confirm();
		}
		catch (Exception e) {
		}

		SWTBotPreferences.TIMEOUT = origin;
	}

	public void deleteProjects() {
		Tree projects = getProjects();

		String[] names = projects.getAllItems();

		for (String name : names) {
			deleteProject(name);
		}
	}

	public void deleteProjects(String[] names) {
		for (String name : names) {
			deleteProject(name);
		}
	}

	public void deleteProjectsExcludeNames(String... names) {
		String[] projectNames = getProjects().getAllItems();

		for (String projectName : projectNames) {
			boolean include = false;

			for (String name : names) {
				if (name.equals(projectName)) {
					include = true;

					break;
				}

				TreeItem projectItem = getProjects().getTreeItem(projectName);

				projectItem.collapse();
			}

			if (!include) {
				deleteProject(projectName);
			}
		}
	}

	public void doActionOnProjectFile(List<String> actions, String... files) {
		fetchProjectFile(files).doAction(actions.toArray(new String[0]));
	}

	public TreeItem fetchProjectFile(String... files) {
		return getProjects().expandNode(files);
	}

	public TreeItem getProject(String name) {
		return getProjects().getTreeItem(name);
	}

	public Tree getProjects() {
		try {
			return _projectExplorerView.getProjects();
		}
		catch (Exception e) {
			return _packageExplorerView.getProjects();
		}
	}

	public void openAddAndRemoveDialog(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.contextMenu(ADD_AND_REMOVE);
	}

	public void openLiferayPortalHome(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.contextMenu(OPEN_LIFERAY_PORTAL_HOME);
	}

	public void openProjectFile(String... files) {
		fetchProjectFile(files).doubleClick();
	}

	public void openServerEditor(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.doubleClick();
	}

	public void serverDebug(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.select();

		_serversView.clickDebugBtn();
	}

	public void serverDeployWait(String projectName) {
		_serverWaitInConsole(10000, 1000, 1000, "STARTED " + projectName + "_");
	}

	public void serverStart(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.select();

		_serversView.clickStartBtn();
	}

	public void serverStartWait() throws TimeoutException {
		_serverWaitInConsole(300000, 25000, 10000, "Server startup in");
	}

	public void serverStop(String serverLabel) {
		TreeItem item = _serversView.getServers().getTreeItem(serverLabel);

		item.select();

		_serversView.clickStopBtn();
	}

	public void serverStopWait() {
		_serverWaitInConsole(
			300000, 5000, 1000, "org.apache.coyote.AbstractProtocol.destroy Destroying ProtocolHandler [\"ajp-nio");
	}

	public void serverStopWait62() {
		_serverWaitInConsole(300000, 5000, 1000, "Destroying ProtocolHandler [\"ajp-bio-");
	}

	public void showServersView() {
		ide.showServersView();
	}

	private boolean _hasConsoleLog(String content) {
		String consoleLog = _consoleView.getLog().getText();

		return consoleLog.contains(content);
	}

	private void _serverWaitInConsole(long timeout, long startTime, long endTime, String consoleLog)
		throws TimeoutException {

		ide.sleep(startTime);

		long limit = System.currentTimeMillis() + timeout;

		while (true) {
			if (_hasConsoleLog(consoleLog)) {
				ide.sleep(endTime);

				return;
			}

			ide.sleep(500);

			if (System.currentTimeMillis() > limit) {
				throw new TimeoutException("Timeout after: " + timeout + " ms.");
			}
		}
	}

	private ConsoleView _consoleView = new ConsoleView(bot);
	private DeleteResourcesContinueDialog _continueDeleteResourcesDialog = new DeleteResourcesContinueDialog(bot);
	private DeleteResourcesDialog _deleteResourcesDialog = new DeleteResourcesDialog(bot);
	private Dialog _dialog = new Dialog(bot);
	private PackageExplorerView _packageExplorerView = new PackageExplorerView(bot);
	private ProjectExplorerView _projectExplorerView = new ProjectExplorerView(bot);
	private ServersView _serversView = new ServersView(bot);

}