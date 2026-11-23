///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ArchUnitVerifyMethodTest {

    /**
     * Names of the annotations that mark a method as a test method.
     */
    private static final Set<String> TEST_ANNOTATIONS = Set.of(
        "org.junit.jupiter.api.Test",
        "org.junit.jupiter.api.RepeatedTest",
        "org.junit.jupiter.params.ParameterizedTest"
    );

    /**
     * Source roots that are compiled into the test classes, in lookup order.
     */
    private static final List<Path> TEST_SOURCE_ROOTS = List.of(
        Path.of("src/test/java"),
        Path.of("src/xdocs-examples/java"),
        Path.of("src/it/java")
    );

    /**
     * Resource holding the list of test methods not following the rule.
     */
    private static final String SUPPRESSED_METHODS_RESOURCE =
            "archunitverifymethod/InputArchUnitVerifyMethodSuppressedTestMethods.txt";

    /**
     * List of test methods not following {@link #testTestMethodsShouldUseVerifyMethod()} rule.
     * until #22947.
     */
    // until https://github.com/checkstyle/checkstyle/issues/22947
    private static final Set<String> SUPPRESSED_TEST_METHODS_WITHOUT_VERIFY =
            loadSuppressedTestMethodsWithoutVerify();

    /**
     * Loads the suppression list of test methods that do not use a verify method and have
     * no javadoc comment yet, from the resource file in the archunitverifymethod folder
     * next to this test class. The list holds one method full name per line and is
     * a backlog to resolve under the issue that introduced this test.
     *
     * @return full names of the suppressed test methods
     */
    private static Set<String> loadSuppressedTestMethodsWithoutVerify() {
        final Set<String> suppressed = new HashSet<>();
        final String content;
        try (InputStream stream = ArchUnitVerifyMethodTest.class
                .getResourceAsStream(SUPPRESSED_METHODS_RESOURCE)) {
            if (stream == null) {
                throw new IllegalStateException(
                        "No " + SUPPRESSED_METHODS_RESOURCE + " resource found");
            }
            content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Failed to load " + SUPPRESSED_METHODS_RESOURCE, exception);
        }
        for (String line : content.lines().toList()) {
            final String methodName = line.trim();
            if (!methodName.isEmpty() && !methodName.startsWith("#")) {
                suppressed.add(methodName);
            }
        }
        return suppressed;
    }

    /**
     * The goal is to ensure all test methods use a verify method that validates the whole
     * input file, so tests are written against inputs and not as pure unit tests of internal
     * logic. A test method that can not comply should have a javadoc comment that explains
     * why a verify method is not possible to use. This test method itself is such a case,
     * it validates the structure of the test code base and has no input file to verify,
     * that is the reason for this javadoc comment.
     */
    @Test
    public void testTestMethodsShouldUseVerifyMethod() {
        final JavaClasses testClasses = new ClassFileImporter()
                .withImportOption(location -> location.contains("test-classes"))
                .importPackages("com.puppycrawl.tools.checkstyle", "org.checkstyle");

        final ArchConditionWithSuppression<JavaMethod> useVerifyMethodOrBeSuppressed =
            new ArchConditionWithSuppression<>(useVerifyMethodOrHaveJavadoc(testClasses),
                    SUPPRESSED_TEST_METHODS_WITHOUT_VERIFY);

        final ArchRule testMethodsShouldUseVerifyMethod = methods()
            .that(new DescribedPredicate<>("is a test method") {
                @Override
                public boolean test(JavaMethod input) {
                    return isTestMethod(input);
                }
            })
            .should(useVerifyMethodOrBeSuppressed);

        testMethodsShouldUseVerifyMethod.check(testClasses);

        assertWithMessage("Outdated suppressions (can be removed)")
                .that(useVerifyMethodOrBeSuppressed.getSuppressions())
                .containsExactlyElementsIn(useVerifyMethodOrBeSuppressed.getUsedSuppressions());
    }

    /**
     * Creates an ArchCondition checking that a test method uses a verify method or has
     * a javadoc comment.
     *
     * @param testClasses imported test classes
     * @return ArchCondition checking verify method usage of test methods
     */
    private static ArchCondition<JavaMethod> useVerifyMethodOrHaveJavadoc(
            JavaClasses testClasses) {
        return new UseVerifyMethodArchCondition(
                getFullNamesOfMethodsThatUseVerifyMethod(testClasses),
                new TestMethodJavadocIndex(testClasses));
    }

    /**
     * Computes the full names of all methods from which a verify method of a test support
     * class is reachable through method calls, so a test method is compliant when it calls
     * a verify method directly or through a helper method.
     *
     * @param testClasses imported test classes
     * @return full names of methods that can reach a verify method
     */
    private static Set<String> getFullNamesOfMethodsThatUseVerifyMethod(JavaClasses testClasses) {
        final Map<String, Set<String>> callersByTarget = new HashMap<>();
        final Set<String> verifyMethods = new HashSet<>();
        for (JavaClass testClass : testClasses) {
            for (JavaMethod method : testClass.getMethods()) {
                if (isVerifyMethod(method)) {
                    verifyMethods.add(method.getFullName());
                }
                for (JavaMethodCall call : method.getMethodCallsFromSelf()) {
                    call.getTarget().resolveMember().ifPresent(target -> {
                        callersByTarget
                            .computeIfAbsent(target.getFullName(), name -> new HashSet<>())
                            .add(method.getFullName());
                    });
                }
            }
        }
        final Set<String> methodsThatUseVerify = new HashSet<>(verifyMethods);
        final Deque<String> queue = new ArrayDeque<>(verifyMethods);
        while (!queue.isEmpty()) {
            final String target = queue.poll();
            for (String caller : callersByTarget.getOrDefault(target, Set.of())) {
                if (methodsThatUseVerify.add(caller)) {
                    queue.add(caller);
                }
            }
        }
        return methodsThatUseVerify;
    }

    /**
     * Checks whether a method is a verify method, that is a method with a name starting
     * with {@code verify} declared in a test support class or in a subclass of one.
     *
     * @param method method to check
     * @return true if the method is a verify method of a test support class
     */
    private static boolean isVerifyMethod(JavaMethod method) {
        boolean verifyMethod = false;
        if (method.getName().startsWith("verify")) {
            JavaClass owner = method.getOwner();
            while (owner != null && !verifyMethod) {
                verifyMethod = owner.getSimpleName().endsWith("TestSupport");
                owner = owner.getSuperclass().map(JavaType::toErasure).orElse(null);
            }
        }
        return verifyMethod;
    }

    /**
     * Checks whether a method is a test method, that is annotated with one of the
     * JUnit test annotations.
     *
     * @param method method to check
     * @return true if the method is a test method
     */
    private static boolean isTestMethod(JavaMethod method) {
        return TEST_ANNOTATIONS.stream().anyMatch(method::isAnnotatedWith);
    }

    /**
     * ArchCondition checking that a test method uses a verify method or has a javadoc
     * comment that explains why a verify method is not possible to use.
     */
    private static final class UseVerifyMethodArchCondition extends ArchCondition<JavaMethod> {

        private final Set<String> fullNamesOfMethodsThatUseVerify;
        private final TestMethodJavadocIndex javadocIndex;

        private UseVerifyMethodArchCondition(Set<String> fullNamesOfMethodsThatUseVerify,
                TestMethodJavadocIndex javadocIndex) {
            super("use a verify method or have a javadoc comment");
            this.fullNamesOfMethodsThatUseVerify = fullNamesOfMethodsThatUseVerify;
            this.javadocIndex = javadocIndex;
        }

        @Override
        public void check(JavaMethod item, ConditionEvents events) {
            if (javadocIndex.hasSourceFile(item)
                    && !fullNamesOfMethodsThatUseVerify.contains(item.getFullName())
                    && !javadocIndex.hasJavadoc(item)) {
                final String message = String.format(Locale.ROOT,
                    "<%s> does not use a verify method and has no javadoc comment",
                    item.getFullName());
                events.add(SimpleConditionEvent.violated(item, message));
            }
        }

    }

    /**
     * Index of test methods that have a javadoc comment, built by parsing the source
     * file of every class that declares test methods. ArchUnit works on bytecode and
     * bytecode has no javadoc comments, so the sources are parsed with checkstyle's own parser.
     */
    private static final class TestMethodJavadocIndex {

        private final Set<String> methodKeysWithJavadoc = new HashSet<>();
        private final Set<String> fallbackKeysWithJavadoc = new HashSet<>();
        private final Set<String> classNamesWithSourceFile = new HashSet<>();

        private TestMethodJavadocIndex(JavaClasses testClasses) {
            final Set<Path> parsedFiles = new HashSet<>();
            for (JavaClass testClass : testClasses) {
                final boolean declaresTestMethod = testClass.getMethods().stream()
                        .anyMatch(ArchUnitVerifyMethodTest::isTestMethod);
                if (declaresTestMethod) {
                    final Path sourceFile = findSourceFile(testClass.getName());
                    if (sourceFile != null) {
                        classNamesWithSourceFile.add(testClass.getName());
                        if (parsedFiles.add(sourceFile)) {
                            collectMethodKeysWithJavadoc(sourceFile);
                        }
                    }
                }
            }
        }

        /**
         * Checks whether the class of a test method has a source file under the test
         * source roots. Compiled test input files have no source file there, they are
         * fixtures and not test classes, so their methods are out of scope of the rule.
         *
         * @param method test method to check
         * @return true if the class of the method has a source file in the test sources
         */
        private boolean hasSourceFile(JavaMethod method) {
            return classNamesWithSourceFile.contains(method.getOwner().getName());
        }

        /**
         * Checks whether a test method has a javadoc comment.
         *
         * @param method test method to check
         * @return true if the method has a javadoc comment in its source file
         */
        private boolean hasJavadoc(JavaMethod method) {
            return methodKeysWithJavadoc.contains(toMethodKey(method))
                    || fallbackKeysWithJavadoc.contains(toFallbackKey(method));
        }

        /**
         * Parses a source file and collects the keys of its methods that have a javadoc
         * comment. A fallback key without parameter types is collected only when the
         * method is the single method of its class with that name and parameter count,
         * so it can not exempt an overload that has no javadoc comment.
         *
         * @param sourceFile source file to parse
         */
        private void collectMethodKeysWithJavadoc(Path sourceFile) {
            final DetailAST rootAst;
            try {
                rootAst = JavaParser.parseFile(sourceFile.toFile(),
                        JavaParser.Options.WITH_COMMENTS);
            }
            catch (CheckstyleException | IOException exception) {
                throw new IllegalStateException("Failed to parse " + sourceFile, exception);
            }
            final Map<String, Integer> methodCountsByFallbackKey = new HashMap<>();
            final List<String[]> keys = new ArrayList<>();
            collectMethodKeys(rootAst, getPackageName(rootAst), new ArrayDeque<>(),
                    methodCountsByFallbackKey, keys);
            for (String[] key : keys) {
                if (methodCountsByFallbackKey.get(key[1]) == 1) {
                    fallbackKeysWithJavadoc.add(key[1]);
                }
                methodKeysWithJavadoc.add(key[0]);
            }
        }

        /**
         * Walks an AST and collects the exact and fallback keys of all methods, counting
         * methods per fallback key for the whole file.
         *
         * @param ast AST node to walk
         * @param packageName package of the parsed file
         * @param typeNames names of the enclosing types, outermost first
         * @param methodCountsByFallbackKey counter of methods per fallback key
         * @param keys collected exact and fallback key pairs of methods with javadoc
         */
        private static void collectMethodKeys(DetailAST ast, String packageName,
                Deque<String> typeNames, Map<String, Integer> methodCountsByFallbackKey,
                List<String[]> keys) {
            final boolean typeDef = isTypeDef(ast);
            if (typeDef) {
                typeNames.addLast(ast.findFirstToken(TokenTypes.IDENT).getText());
            }
            if (ast.getType() == TokenTypes.METHOD_DEF) {
                final String fallbackKey = toFallbackKey(packageName, typeNames, ast);
                methodCountsByFallbackKey.merge(fallbackKey, 1, Integer::sum);
                if (hasJavadocComment(ast)) {
                    keys.add(new String[] {toMethodKey(packageName, typeNames, ast), fallbackKey});
                }
            }
            DetailAST child = ast.getFirstChild();
            while (child != null) {
                collectMethodKeys(child, packageName, typeNames, methodCountsByFallbackKey,
                        keys);
                child = child.getNextSibling();
            }
            if (typeDef) {
                typeNames.removeLast();
            }
        }

        /**
         * Finds the source file of a class by its full name, nested classes resolve to
         * the file of their top level class.
         *
         * @param className full name of the class, with {@code $} for nested classes
         * @return path of the source file, or null if it is under no known source root
         */
        private static Path findSourceFile(String className) {
            final String topLevelClassName = className.split("\\$", 2)[0];
            final String relativePath = topLevelClassName.replace('.', '/') + ".java";
            Path sourceFile = null;
            for (Path root : TEST_SOURCE_ROOTS) {
                final Path candidate = root.resolve(relativePath);
                if (Files.exists(candidate)) {
                    sourceFile = candidate;
                    break;
                }
            }
            return sourceFile;
        }

        /**
         * Returns the package name of a parsed file.
         *
         * @param rootAst root AST of the parsed file
         * @return package name, or an empty string for the default package
         */
        private static String getPackageName(DetailAST rootAst) {
            String packageName = "";
            final DetailAST packageDef = rootAst.findFirstToken(TokenTypes.PACKAGE_DEF);
            if (packageDef != null) {
                DetailAST child = packageDef.getFirstChild();
                while (child != null) {
                    if (child.getType() == TokenTypes.DOT
                            || child.getType() == TokenTypes.IDENT) {
                        packageName = renderDottedName(child);
                        break;
                    }
                    child = child.getNextSibling();
                }
            }
            return packageName;
        }

        /**
         * Renders a dotted name node of an AST, like a package name.
         *
         * @param ast IDENT or DOT node
         * @return dotted name
         */
        private static String renderDottedName(DetailAST ast) {
            final String name;
            if (ast.getType() == TokenTypes.DOT) {
                name = renderDottedName(ast.getFirstChild()) + "."
                        + ast.getLastChild().getText();
            }
            else {
                name = ast.getText();
            }
            return name;
        }

        /**
         * Checks whether an AST node is a type definition.
         *
         * @param ast AST node to check
         * @return true if the node is a class, interface, enum, record or annotation definition
         */
        private static boolean isTypeDef(DetailAST ast) {
            final int type = ast.getType();
            return type == TokenTypes.CLASS_DEF
                    || type == TokenTypes.INTERFACE_DEF
                    || type == TokenTypes.ENUM_DEF
                    || type == TokenTypes.RECORD_DEF
                    || type == TokenTypes.ANNOTATION_DEF;
        }

        /**
         * Checks whether a method definition has a javadoc comment.
         *
         * @param methodDef method definition AST
         * @return true if the method has a javadoc comment
         */
        private static boolean hasJavadocComment(DetailAST methodDef) {
            final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
            final DetailAST blockComment = findBlockComment(modifiers);
            boolean hasJavadoc = false;
            if (blockComment != null) {
                final DetailAST content =
                        blockComment.findFirstToken(TokenTypes.COMMENT_CONTENT);
                hasJavadoc = content != null && content.getText().startsWith("*");
            }
            return hasJavadoc;
        }

        /**
         * Finds the first block comment in the subtree of an AST node. A javadoc comment
         * of a method is a child of its modifiers, or of an annotation in the modifiers
         * when the method is annotated.
         *
         * @param ast AST node to search
         * @return first block comment begin node in the subtree, or null if there is none
         */
        private static DetailAST findBlockComment(DetailAST ast) {
            DetailAST blockComment = null;
            DetailAST child = ast.getFirstChild();
            while (child != null && blockComment == null) {
                if (child.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                    blockComment = child;
                }
                else {
                    blockComment = findBlockComment(child);
                }
                child = child.getNextSibling();
            }
            return blockComment;
        }

        /**
         * Builds the exact key of a method from its bytecode model.
         *
         * @param method method to build the key for
         * @return exact key of the method
         */
        private static String toMethodKey(JavaMethod method) {
            final String parameterTypes = method.getRawParameterTypes().stream()
                    .map(JavaClass::getSimpleName)
                    .collect(Collectors.joining(", "));
            return method.getOwner().getName() + "." + method.getName()
                    + "(" + parameterTypes + ")";
        }

        /**
         * Builds the exact key of a method from its AST, in the same format as
         * {@link #toMethodKey(JavaMethod)}.
         *
         * @param packageName package of the parsed file
         * @param typeNames names of the enclosing types, outermost first
         * @param methodDef method definition AST
         * @return exact key of the method
         */
        private static String toMethodKey(String packageName, Deque<String> typeNames,
                DetailAST methodDef) {
            final List<String> parameterTypes = new ArrayList<>();
            DetailAST parameter = methodDef.findFirstToken(TokenTypes.PARAMETERS)
                    .getFirstChild();
            while (parameter != null) {
                if (parameter.getType() == TokenTypes.PARAMETER_DEF) {
                    parameterTypes.add(getParameterTypeName(parameter));
                }
                parameter = parameter.getNextSibling();
            }
            return toClassName(packageName, typeNames) + "."
                    + methodDef.findFirstToken(TokenTypes.IDENT).getText()
                    + "(" + String.join(", ", parameterTypes) + ")";
        }

        /**
         * Builds the fallback key of a method from its bytecode model.
         *
         * @param method method to build the key for
         * @return fallback key of the method
         */
        private static String toFallbackKey(JavaMethod method) {
            return method.getOwner().getName() + "." + method.getName()
                    + "/" + method.getRawParameterTypes().size();
        }

        /**
         * Builds the fallback key of a method from its AST, in the same format as
         * {@link #toFallbackKey(JavaMethod)}.
         *
         * @param packageName package of the parsed file
         * @param typeNames names of the enclosing types, outermost first
         * @param methodDef method definition AST
         * @return fallback key of the method
         */
        private static String toFallbackKey(String packageName, Deque<String> typeNames,
                DetailAST methodDef) {
            int parameterCount = 0;
            DetailAST parameter = methodDef.findFirstToken(TokenTypes.PARAMETERS)
                    .getFirstChild();
            while (parameter != null) {
                if (parameter.getType() == TokenTypes.PARAMETER_DEF) {
                    parameterCount++;
                }
                parameter = parameter.getNextSibling();
            }
            return toClassName(packageName, typeNames) + "."
                    + methodDef.findFirstToken(TokenTypes.IDENT).getText()
                    + "/" + parameterCount;
        }

        /**
         * Builds the full class name for a key from a package name and the names of the
         * enclosing types.
         *
         * @param packageName package of the parsed file
         * @param typeNames names of the enclosing types, outermost first
         * @return full class name, with {@code $} between nested type names
         */
        private static String toClassName(String packageName, Deque<String> typeNames) {
            final String className;
            if (packageName.isEmpty()) {
                className = String.join("$", typeNames);
            }
            else {
                className = packageName + "." + String.join("$", typeNames);
            }
            return className;
        }

        /**
         * Renders the type name of a parameter as a simple type name with array brackets,
         * matching the rendering of {@link #toMethodKey(JavaMethod)}.
         *
         * @param parameterDef parameter definition AST
         * @return simple type name of the parameter
         */
        private static String getParameterTypeName(DetailAST parameterDef) {
            final StringBuilder typeName = new StringBuilder(renderTypeName(
                    parameterDef.findFirstToken(TokenTypes.TYPE).getFirstChild()));
            DetailAST child = parameterDef.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.ELLIPSIS
                        || child.getType() == TokenTypes.ARRAY_DECLARATOR) {
                    typeName.append("[]");
                }
                child = child.getNextSibling();
            }
            return typeName.toString();
        }

        /**
         * Renders a type name node as a simple type name.
         *
         * @param ast type name node, an IDENT, DOT, ARRAY_DECLARATOR or literal type token
         * @return simple type name
         */
        private static String renderTypeName(DetailAST ast) {
            final String typeName;
            if (ast.getType() == TokenTypes.ARRAY_DECLARATOR) {
                typeName = renderTypeName(ast.getFirstChild()) + "[]";
            }
            else if (ast.getType() == TokenTypes.DOT) {
                typeName = ast.getLastChild().getText();
            }
            else {
                typeName = ast.getText();
            }
            return typeName;
        }

    }

}
