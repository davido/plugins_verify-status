// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.verifystatus.client;

import com.google.gerrit.client.GerritUiExtensionPoint;
import com.google.gerrit.plugin.client.Plugin;
import com.google.gerrit.plugin.client.PluginEntryPoint;
import com.google.gerrit.plugin.client.rpc.RestApi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class VerifyStatusPlugin extends PluginEntryPoint {
  public static final Resources RESOURCES = GWT.create(Resources.class);

  @Override
  public void onPluginLoad() {
    Plugin.get().screenRegex("jobs/(.*)", new JobsScreen.Factory());
    new RestApi("config")
        .view("server")
        .view(Plugin.get().getPluginName(), "config")
        .get(
            new AsyncCallback<ConfigInfo>() {
              @Override
              public void onSuccess(ConfigInfo info) {
                if (info.showJobsSummaryPanel()) {
                  Plugin.get()
                      .panel(
                          GerritUiExtensionPoint.CHANGE_SCREEN_BELOW_CHANGE_INFO_BLOCK,
                          new JobsSummaryPanel.Factory(),
                          "VerifyStatusJobsSummaryPanel");
                }
                if (info.showJobsPanel()) {
                  Plugin.get()
                      .panel(
                          info.showJobsBelowRelatedInfoBlock()
                              ? GerritUiExtensionPoint.CHANGE_SCREEN_BELOW_RELATED_INFO_BLOCK
                              : GerritUiExtensionPoint.CHANGE_SCREEN_BELOW_CHANGE_INFO_BLOCK,
                          new JobsPanel.Factory(info),
                          "VerifyStatusJobsPanel");
                }
                if (info.showJobsDropDownPanel()) {
                  Plugin.get()
                      .panel(
                          GerritUiExtensionPoint.CHANGE_SCREEN_HEADER_RIGHT_OF_POP_DOWNS,
                          new JobsDropDownPanel.Factory(info),
                          "VerifyStatusJobsDropDownPanel");
                }
              }

              @Override
              public void onFailure(Throwable caught) {
                // never invoked
              }
            });
  }
}
