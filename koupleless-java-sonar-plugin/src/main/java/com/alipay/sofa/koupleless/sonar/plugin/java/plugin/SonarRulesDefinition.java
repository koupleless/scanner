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
package com.alipay.sofa.koupleless.sonar.plugin.java.plugin;

import com.alipay.sofa.koupleless.sonar.plugin.java.check.klass.MajorClassForNameInvocationCheckRule;
import com.alipay.sofa.koupleless.sonar.plugin.java.check.klass.MajorClassGetClassLoaderInvocationCheckRule;
import com.alipay.sofa.koupleless.sonar.plugin.java.check.variable.MajorStaticVariableCheckRule;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author CodeNoobKing
 * @date 2024/4/12
 **/
public class SonarRulesDefinition implements RulesDefinition {

    public static final String  REPOSITORY_KEY     = "koupleless-java";

    public static final String  REPOSITORY_NAME    = "Koupleless Sonar Analyzer";

    // don't change that because the path is hard coded in CheckVerifier
    private static final String RESOURCE_BASE_PATH = "org/sonar/l10n/java/rules/java";

    private final SonarRuntime  runtime;

    public SonarRulesDefinition(SonarRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "java")
            .setName(REPOSITORY_NAME);

        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, runtime);

        ruleMetadataLoader.addRulesByAnnotatedClass(repository,
            List.of(MajorStaticVariableCheckRule.class, MajorClassForNameInvocationCheckRule.class,
                MajorClassGetClassLoaderInvocationCheckRule.class));

        repository.done();
    }
}
