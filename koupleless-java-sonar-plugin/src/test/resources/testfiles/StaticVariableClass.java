package com.alipay.sofa.koupleless.sonar.plugin.java.check.variable;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CodeNoobKing
 * @date 2024/4/11
 **/
public class StaticVariableClass {

    private static final Integer NOWARNING_IMMUTABLE_TYPE = 0;

    private static Gson NOWARNING_GSON_TYPE = new Gson();

    private static AtomicInteger NOWARNING_ID = new AtomicInteger(0);

    private static List<String> NO_WARNING_UNMODIFIABLE_LIST_INITIALIZE = List.of("a", "b", "c");

    private static Integer SHOULD_BE_WARNING = 0; // Noncompliant {{This static variable 'SHOULD_BE_WARNING' has potential risk on multi app pattern and cannot be automatically ruled out, please check it manually.}}
}
