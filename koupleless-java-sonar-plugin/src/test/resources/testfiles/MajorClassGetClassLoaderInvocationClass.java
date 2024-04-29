package com.alipay.sofa.koupleless.sonar.plugin.java.check.klass;

/**
 * @author CodeNoobKing
 * @date 2024/4/29
 **/
public class MajorClassForNameInvocationClass {

    static {
        try {
            MajorClassForNameInvocationClass.class.getClassLoader(); //Noncompliant {{Class getClassLoader return use current model's classLoader, which may cause incorrect behaviour, plz check it manually.}}
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    {
        try {
            MajorClassForNameInvocationClass.class.getClassLoader();// Noncompliant {{Class getClassLoader return use current model's classLoader, which may cause incorrect behaviour, plz check it manually.}}
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void invoke() throws Throwable {
        MajorClassForNameInvocationClass.class.getClassLoader();// Noncompliant {{Class for name use caller's class loader, which may cause incorrect behaviour, plz check it manually.}}
    }

    public void notInvoke() {
        System.out.println("not invoke");
    }
}