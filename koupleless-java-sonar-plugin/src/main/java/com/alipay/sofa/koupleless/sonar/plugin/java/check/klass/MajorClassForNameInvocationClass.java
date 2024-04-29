package com.alipay.sofa.koupleless.sonar.plugin.java.check.klass;

/**
 * @author CodeNoobKing
 * @date 2024/4/29
 **/
public class MajorClassForNameInvocationClass {

    static {
        try {
            Class.forName("Foo");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    {
        try {
            Class.forName("Foo");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void invoke() throws Throwable {
        Class.forName("Foo");
    }

    public void notInvoke() {
        System.out.println("not invoke");
    }
}
