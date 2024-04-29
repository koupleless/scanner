package com.alipay.sofa.koupleless.sonar.plugin.java.check.klass;

import com.alipay.sofa.koupleless.sonar.plugin.java.utils.TestUtils;
import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.internal.InternalCheckVerifier;
import org.sonar.java.reporting.AnalyzerMessage;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author CodeNoobKing
 * @date 2024/4/29
 **/
public class MajorClassGetClassLoaderInvocationCheckRuleTest {

    @Test
    public void testRule() {
        MajorClassGetClassLoaderInvocationCheckRule check = new MajorClassGetClassLoaderInvocationCheckRule();
        InternalCheckVerifier checkVerifier = (InternalCheckVerifier) CheckVerifier.newVerifier();
        checkVerifier = checkVerifier.withCustomIssueVerifier(new Consumer<Set<AnalyzerMessage>>() {
            @Override
            public void accept(Set<AnalyzerMessage> analyzerMessages) {
                System.out.println("analyzerMessages = " + analyzerMessages);
            }
        }).onFile(TestUtils.getTestJavaFile("MajorClassGetClassLoaderInvocationClass.java")).withCheck(check);

        checkVerifier.verifyIssues();
    }
}
