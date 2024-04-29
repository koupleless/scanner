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
@Rule(key = "MajorClassGetClassLoaderInvocationCheck", name = "koupleless-class-get-class-loader-invocation-major", priority = Priority.MAJOR)
public class MajorClassGetClassLoaderInvocationCheckRule extends IssuableSubscriptionVisitor {

    private static String CLASS_GET_CLASS_LOADER_SIGNATURE = "java.lang.Class#getClassLoader()Ljava/lang/ClassLoader;";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
        if (CLASS_GET_CLASS_LOADER_SIGNATURE.equals(methodInvocationTree.methodSymbol().signature())) {
            reportIssue(
                    tree,
                    "'Class getClassLoader return use current model's classLoader, which may cause incorrect behaviour, plz check it manually."
            );
        }
        super.visitNode(tree);
    }
}
