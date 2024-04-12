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

import org.junit.Assert;
import org.junit.Test;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.location.Position;
import org.sonar.plugins.java.api.tree.SyntaxToken;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.location.Range;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author CodeNoobKing
 * @date 2024/4/12
 **/
public class CommonFileUtilsTest {

    @Test
    public void testExtractSourceCode_OneLine() {
        JavaFileScannerContext context = null;
        Tree tree = null;
        {
            List<String> lines = List.of(
                "package com.alipay.sofa.koupleless.sonar.plugin.java.common;", "",
                "foo bar hello world");
            context = mock(JavaFileScannerContext.class);
            doReturn(lines).when(context).getFileLines();

            tree = mock(Tree.class);
            SyntaxToken firstSyntaxToken = mock(SyntaxToken.class);
            doReturn(Range.at(Position.at(3, 5), Position.at(0, 0))).when(firstSyntaxToken).range();
            doReturn(firstSyntaxToken).when(tree).firstToken();

            SyntaxToken lastSyntaxToken = mock(SyntaxToken.class);
            doReturn(Range.at(Position.at(0, 0), Position.at(3, 13))).when(lastSyntaxToken).range();
            doReturn(lastSyntaxToken).when(tree).lastToken();
        }

        String sourceCode = CommonFileUtils.extractSourceCode(context, tree);
        Assert.assertEquals("bar hello", sourceCode);
    }

    @Test
    public void testExtractSourceCode_MultiLine() {
        JavaFileScannerContext context = null;
        Tree tree = null;
        {
            List<String> lines = List.of(
                "package com.alipay.sofa.koupleless.sonar.plugin.java.common;", "",
                "foo bar hello world", "foo bar hello world", "foo bar hello world", "john doe");
            context = mock(JavaFileScannerContext.class);
            doReturn(lines).when(context).getFileLines();

            tree = mock(Tree.class);
            SyntaxToken firstSyntaxToken = mock(SyntaxToken.class);
            doReturn(Range.at(Position.at(3, 5), Position.at(0, 0))).when(firstSyntaxToken).range();
            doReturn(firstSyntaxToken).when(tree).firstToken();

            SyntaxToken lastSyntaxToken = mock(SyntaxToken.class);
            doReturn(Range.at(Position.at(0, 0), Position.at(5, 13))).when(lastSyntaxToken).range();
            doReturn(lastSyntaxToken).when(tree).lastToken();
        }

        String sourceCode = CommonFileUtils.extractSourceCode(context, tree);
        Assert.assertEquals("bar hello world" + System.lineSeparator() + "foo bar hello world"
                            + System.lineSeparator() + "foo bar hello",
            sourceCode);
    }
}
