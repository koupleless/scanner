package com.alipay.sofa.koupleless.sonar.plugin.java.check.klass;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * @author CodeNoobKing
 * @date 2024/4/29
 **/
@Rule(key = "MajorClassForNameInvocationCheck", name = "koupleless-class-for-name-call-major", priority = Priority.MAJOR)
public class MajorClassForNameInvocationCheckRule extends IssuableSubscriptionVisitor {

    private static String CLASS_FOR_NAME_SIGNATURE = "java.lang.Class#forName(Ljava/lang/String;)Ljava/lang/Class;";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
        if (CLASS_FOR_NAME_SIGNATURE.equals(methodInvocationTree.methodSymbol().signature())) {
            reportIssue(
                    tree,
                    "Class for name use caller's class loader, which may cause incorrect behaviour, plz check it manually."
            );
        }
        super.visitNode(tree);
    }
}
