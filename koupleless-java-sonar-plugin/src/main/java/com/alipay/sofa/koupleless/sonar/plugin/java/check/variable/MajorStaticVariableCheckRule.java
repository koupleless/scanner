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

import com.alipay.sofa.koupleless.sonar.plugin.java.common.CommonFileUtils;
import com.alipay.sofa.koupleless.sonar.plugin.java.model.VariableCheckUnit;
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Rule(key = "MajorStaticVariableCheck", name = "koupleless-static-variable-major", priority = Priority.MAJOR)
public class MajorStaticVariableCheckRule extends IssuableSubscriptionVisitor {

    /**
     * When the field has final modifier and the type is immutable, the static variable is safe.
     */
    public static Pattern NO_WARNING_IMMUTABLE_TYPES_COMPILED = null;
    public static String  NO_WARNING_IMMUTABLE_TYPES          = """
            int|
            long|
            short|
            byte|
            char|
            float|
            double|
            boolean|
            java.lang.Integer|
            java.lang.Double|
            java.lang.Float|
            java.lang.Long|
            java.lang.Short|
            java.lang.Byte|
            java.lang.Character|
            java.lang.Boolean|
            java.lang.String|
            java.math.BigDecimal|
            java.math.BigInteger|
            java.util.UUID|
            java.util.Locale|
            java.util.Currency|
            java.util.TimeZone|
            java.util.regex.Pattern|
            java.util.regex.Matcher|
            java.util.regex.PatternSyntaxException|
            java.util.regex.MatchResult|
            java.lang.Class.*|
            ^java.math.*|
            java.lang.reflect.*|
            java.lang.management.*|
            .*Converter$|
            .*Transformer$|
            .*Information$|
            .*Settings$|
            .*Level$|
            .*Configuration$|
            .*Charset$|
            .*Keywords$|
            .*DataType$|
            .*DataType<.*>$|
            .*Builder$|
            .*Builder<.*>|
            .*ID$|
            .*Id$|
            .*Hash32$|
            .*Comparator$|
            .*State$|
            .*Data$|
            .*Factory$|
            .*Formatter$|
            .*Exception$|
            .*Config$|
            .*Schema$|
            .*Null$|
            .*NullImpl$|
            ConfigDef|
            .*Version$|
            .*\\.?JdbcParameter.*|
            .*UUID$|
            .*Uuid$|
            .*\\[\\]$|
            .*FACTORY.*|
            """;

    /**
     * When the field name matches the pattern, it often indicates specific safe usage such as keyword, codec, etc.
     * So we don't need to warn them as a major issue.
     */
    private static Pattern NO_WARNING_FIELD_NAME_PATTERN_COMPILED = null;
    private static String  NO_WARNING_FIELD_NAME_PATTERN          = """
            CODE_TO_VALUE|
            localhost|
            keywords|
            KEYWORDS|
            ROOT|
            root|
            SKIP|
            skip|
            CODEC|
            codec|
            startTime|
            START_TIME|
            endTime|
            END_TIME|
            LOCAL_HOST|
            processId|
            PROCESS_ID|
            NEXT_INDEX|
            THREAD_INDEX|
            mediaTypes|
            MEDIA_TYPES|
            ANONYMOUS|
            INITIAL|
            initial|
            PID|
            pid|
            MD5|
            md5|
            JSON|
            json|
            DECOMPRESSOR|
            OBJECT|
            object|
            IGNORED|
            ignored|
            EMPTY.*|
            empty.*|
            ALL_.*|
            NO_.*|
            NONE|
            none|
            ZERO|
            zero|
            TEN|
            ten|
            ANY|
            any|
            NEVER|
            never|
            WARN|
            warn|
            TRUE|
            true|
            FALSE|
            false|
            OK|
            INFINITE|
            IMMEDIATE|
            SUCCESS|
            FAIL|
            UNKNOWN|
            INTERNAL|
            NOT_FOUND|
            CANCELLED|
            UNAVAILABLE|
            UNIMPLEMENTED|
            TIMEOUT|
            TIME_OUT|
            FAIL_TIMEOUT|
            DEADLINE_EXCEEDE|
            .*debug.*|
            .*DEBUG.*|
            .*DECODER.*|
            .*decoder.*|
            .*ENCODER.*|
            .*encoder.*|
            .*license.*|
            .*unSupport.*|
            .*UNSUPPORT.*|
            .*currentTime.*|
            .*CURRENT_TIME.*|
            .*FORMATTER.*|
            .*TO_ENUM$|
            ^default.*|
            ^DEFAULT_.*|
            ^MAX_.*|
            ^max.*|
            ^MIN_.*|
            ^min.*|
            ^INCREMENTING.*|
            ^incrementing.*|
            ^INCREMENT.*|
            ^increment.*|
            ^FALLBACK_.*|
            ^PRIMITIVE_.*|
            PRIMITIVE_TYPE.*|
            PRIMITIVE_WRAPPER_TYPE.*|
            WRAPPER_PRIMITIVE_TYPE_MAP.*|
            .*EVENT_LOOP.*|
            .*TIMER$|
            ^SUPPORTED_.*$|
            .*_CHARACTERS$|
            .*_COUNTER$|
            COUNTER_.*$|
            ^builtin.*|
            ^BUILTIN_.*|
            .*_MB$|
            .*_mb$|
            .*Utils$|
            .*Util$|
            .*_UTILS$|
            .*_UTIL$|
            .*_CODEC$|
            .*MANAGER$|
            .*Manager$|
            .*Parser.*|
            .*PARSER.*|
            .*_HASH$|
            .*_SIZE$|
            .*_size$|
            .*_PACK$|
            .*_ATTR_$|
            .*_ATTRS_$|
            .*CHARSET$|
            .*Exception$|
            .*_EXCEPTION$|
            .*_KEY$|
            .*_KEYS$|
            .*Key$|
            ^EMPTY.*|
            ^Empty.*|
            .*lock.*|
            .*LOCK.*|
            .*Lock.*|
            .*EXCEPTIONS.*|
            .*INSTANCE.*|
            .*instance.*|
            LOG|
            .*LOG.*|
            .*Log.*|
            .*log.*|
            .*LOGGER.*|
            .*logger.*|
            .*RANDOM.*|
            .*Random.*|
            .*random.*|
            .*CLASS_NAME.*|
            .*HOST_NAME.*|
            .*hostname.*|
            .*HEX.*|
            .*hex.*|
            ^UTF_.*|
            ^s_.*|
            .*Null.*|
            .*NULL.*|
            .*Default.*|
            .*DEFAULT.*|
            .*Consts.*|
            .*Constants.*|
            INDEX|
            index|
            .*_INDEX|
            .*_ID.*|
            .*_id.*|
            SEQUENCE|
            sequence|
            .*_SEQUENCE.*|
            .*_sequence.*|
            .*_UPDATER.*|
            .*_Updater.*|
            .*_updater.*|
            """;

    /**
     * Sometimes the containing class is just a utility class or a test class, such as Builder, Mock, Lexer, etc.
     * So we don't need to warn them as a major issue.
     */
    private static Pattern NO_WARNING_CONTAINING_CLASS_NAME_PATTERN_COMPILED = null;
    private static String  NO_WARNING_CONTAINING_CLASS_NAME_PATTERN          = """
            .*Builder$|
            ^Mock.*|
            .*Lexer$|
            TimeUtils|
            .*Constant$|
            .*Test.*|
            """;

    /**
     * Sometimes the field is initialized by some common safe ways, such as unmodifiable containers or TypeToken.
     * So we don't need to warn them as a major issue.
     */
    public static Pattern NO_WARNING_FIELD_INITIALIZATION_TEXT_PATTERN_COMPILED = null;
    public static String  NO_WARNING_FIELD_INITIALIZATION_TEXT_PATTERN          = """
            new TypeToken.*|
            new ParameterizedTypeReference.*|
            new TypeReference.*|
            new GenericTypeIndicator.*|
            .*MarkerManager\\.getMarker\\(LogFactory\\.MARKER\\).*|
            .*asList\\(.*|
            .*List\\.of\\(.*|
            .*singletonList.*|
            .*unmodifiableSet\\(.*|
            .*unmodifiableMap\\(.*|
            .*ofSet\\(.*|
            .*valueOf\\(.*|
            .*of\\(.*|
            .*bsonTypeOf\\(.*|
            .*jsonTypeOf\\(.*|
            .*empty\\(\\).*|
            .*new ArrayList.*\\{.*add\\(.*\\).*\\}.*|
            new NullTransaction.*|
            """;

    //Pattern.DOTALL

    ;
    /**
     * Sometimes the field type is safe, such as some common types such as LOGGER, GSON, etc.
     * So we don't need to warn them as a major issue.
     */
    private static Pattern NO_WARNING_FIELD_TYPE_PATTERN_COMPILED = null;
    private static String  NO_WARNING_FIELD_TYPE_PATTERN          = """
            SYSTEM|
            GSON|
            Gson|
            Type|
            Logger|
            Joiner|
            Escaper|
            char\\[]|
            byte\\[]|
            int\\[]|
            double\\[]|
            java.lang.String\\[]|
            java.lang.reflect.Type|
            Joiner.MapJoiner|
            MapJoiner|
            java.lang.ThreadGroup|
            ThreadGroup|
            java.lang.ClassLoader|
            ClassLoader|
            java.util.Random|
            Function|
            java.util.Comparator|
            java.util.BitSet|
            Comparator|
            java.util.concurrent.ExecutorService|
            ExecutorService|
            java.util.regex.Pattern|
            java.lang.reflect.InvocationHandler|
            java.lang.Object|
            java.lang.invoke.MethodHandle|
            java.lang.invoke.MethodHandles|
            java.lang.invoke.MethodType|
            java.util.function.Function|
            java.util.function.BiFunction|
            java.util.concurrent.ThreadPoolExecutor|
            java.util.concurrent.locks.Condition|
            java.util.concurrent.locks.ReentrantLock|
            java.time.format.DateTimeFormatter|
            java.util.Locale|
            java.util.concurrent.Executors|
            java.util.concurrent.RejectedExecutionException|
            java.util.concurrent.Semaphore|
            java.net.Inet4Address|
            java.net.Inet6Address|
            java.net.InetAddress|
            java.time.Duration|
            com.fasterxml.jackson.databind.Module|
            Splitter|
            OgnlMemberAccess|
            OgnlClassResolver|
            .*Map.*<.*ClassLoader.*|
            .*Predicate.*|
            .*ThreadLocal<.*|
            .*AttributeKey<.*|
            .*Immutable.*|
            .*immutable.*|
            .*unmodifiable<.*|
            """;

    static {
        NO_WARNING_IMMUTABLE_TYPES_COMPILED = Pattern
                .compile(NO_WARNING_IMMUTABLE_TYPES.replace(System.lineSeparator(), ""));
        NO_WARNING_FIELD_NAME_PATTERN_COMPILED = Pattern
                .compile(NO_WARNING_FIELD_NAME_PATTERN.replace(System.lineSeparator(), ""));
        NO_WARNING_FIELD_TYPE_PATTERN_COMPILED = Pattern
                .compile(NO_WARNING_FIELD_TYPE_PATTERN.replace(System.lineSeparator(), ""));
        NO_WARNING_FIELD_INITIALIZATION_TEXT_PATTERN_COMPILED = Pattern.compile(
                NO_WARNING_FIELD_INITIALIZATION_TEXT_PATTERN.replace(System.lineSeparator(), ""),
                Pattern.DOTALL);
        NO_WARNING_CONTAINING_CLASS_NAME_PATTERN_COMPILED = Pattern
                .compile(NO_WARNING_CONTAINING_CLASS_NAME_PATTERN.replace(System.lineSeparator(), ""));
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        VariableTree variableTree = (VariableTree) tree;
        ExpressionTree initializer = variableTree.initializer();
        String initializerText = "";
        if (initializer != null) {
            initializerText = CommonFileUtils.extractSourceCode(context, initializer);
        }

        String containingClassName = "";
        Tree parent = variableTree.parent();
        if (parent instanceof ClassTree) {
            containingClassName = ((ClassTree) parent).symbol().type().fullyQualifiedName();
        }

        VariableCheckUnit checkUnit = VariableCheckUnit.builder()
                .name((variableTree.simpleName().name()))
                .type(variableTree.type().symbolType().fullyQualifiedName())
                .finalModifier(variableTree.symbol().isFinal())
                .staticModifier(variableTree.symbol().isStatic()).initializerText(initializerText)
                .containingClassName(containingClassName).build();

        check(checkUnit, variableTree);

        super.visitNode(tree);
    }

    public boolean shouldWarnStaticVariable(VariableCheckUnit unit) {
        // 不可变对象，不需要警告。
        if (unit.isFinalModifier() && NO_WARNING_IMMUTABLE_TYPES_COMPILED
                .matcher(StringUtils.trim(unit.getType())).matches()) {
            return false;
        }

        // 根据类型模式，判断是否需要警告。
        if (NO_WARNING_FIELD_TYPE_PATTERN_COMPILED.matcher(unit.getType()).matches()) {
            return false;
        }

        // 根据字段名模式，判断是否需要警告。
        if (NO_WARNING_FIELD_NAME_PATTERN_COMPILED.matcher(unit.getName()).matches()) {
            return false;
        }

        // 根据初始化模式，判断是否需要警告。
        if (NO_WARNING_FIELD_INITIALIZATION_TEXT_PATTERN_COMPILED.matcher(unit.getInitializerText())
                .matches()) {
            return false;
        }

        // 根据类名模式，判断是否需要警告。
        if (NO_WARNING_CONTAINING_CLASS_NAME_PATTERN_COMPILED.matcher(unit.getContainingClassName())
                .matches()) {
            return false;
        }

        return true;
    }

    private void check(VariableCheckUnit unit, VariableTree tree) {
        if (shouldWarnStaticVariable(unit)) {
            reportIssue(tree, String.format(
                    "This static variable '%s' has potential risk on multi app pattern and cannot be automatically ruled out, please check it manually.",
                    unit.getName())
            );
        }
    }
}