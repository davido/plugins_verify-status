// Copyright (C) 2012 The Android Open Source Project
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

package com.googlesource.gerrit.plugins.verifystatus.init;

import static com.google.gerrit.pgm.init.api.InitUtil.username;

import com.google.gerrit.pgm.init.api.Section;

class PostgreSQLInitializer implements DatabaseConfigInitializer {

  @Override
  public void initConfig(Section configSection) {
    final String defPort = "(postgresql default)";
    configSection.string("Server hostname", "hostname", "localhost");
    configSection.string("Server port", "port", defPort, true);
    configSection.string("Database name", "database", "cidb");
    configSection.string("Database username", "username", username());
    configSection.password("username", "password");
  }
}
