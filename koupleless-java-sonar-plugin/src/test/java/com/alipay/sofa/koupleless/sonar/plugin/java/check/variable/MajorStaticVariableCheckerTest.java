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
package com.alipay.sofa.koupleless.sonar.plugin.java.check.variable;

import com.alipay.sofa.koupleless.sonar.plugin.java.utils.TestUtils;
import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.internal.InternalCheckVerifier;
import org.sonar.java.reporting.AnalyzerMessage;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author CodeNoobKing
 * @date 2024/4/11
 **/
public class MajorStaticVariableCheckerTest {

    @Test
    public void test() {
        MajorStaticVariableCheckRule check = new MajorStaticVariableCheckRule();
        InternalCheckVerifier checkVerifier = (InternalCheckVerifier) CheckVerifier.newVerifier();
        checkVerifier = checkVerifier.withCustomIssueVerifier(new Consumer<Set<AnalyzerMessage>>() {
            @Override
            public void accept(Set<AnalyzerMessage> analyzerMessages) {
                System.out.println("analyzerMessages = " + analyzerMessages);
            }
        }).onFile(TestUtils.getTestJavaFile("StaticVariableClass.java")).withCheck(check);

        checkVerifier.verifyIssues();
    }
}
