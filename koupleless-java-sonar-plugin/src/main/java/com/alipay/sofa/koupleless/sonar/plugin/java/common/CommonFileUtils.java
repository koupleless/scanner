/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.koupleless.sonar.plugin.java.common;

import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.location.Position;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * @author CodeNoobKing
 * @date 2024/4/11
 **/
public class CommonFileUtils {

    public static String extractSourceCode(JavaFileScannerContext context, Tree tree) {
        List<String> lines = context.getFileLines();
        Position start = tree.firstToken().range().start();
        Position end = tree.lastToken().range().end();
        StringBuilder content = new StringBuilder();

        // Start and end are on the same line.
        if (start.line() == end.line()) {
            String line = lines.get(start.line() - 1);
            content.append(line.substring(start.column() - 1, end.column()));
            return content.toString();
        }

        // Add part of the start line
        String startLine = lines.get(start.line() - 1);
        content.append(startLine.substring(start.column() - 1));

        // Add full lines between start and end
        for (int i = start.line() + 1; i < end.line(); i++) {
            content.append(System.lineSeparator()).append(lines.get(i - 1));
        }

        // Add part of the end line
        String endLine = lines.get(end.line() - 1);
        content.append(System.lineSeparator()).append(endLine.substring(0, end.column()));
        return content.toString();
    }
}
