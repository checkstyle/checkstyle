/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = (default)Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodNewCasesAfterAstMigration {

    /**
     * Some javadoc.
     *
     * @return some value.
     */ final String hasJavadocWithClosingTagOnMethodLine() {
        return "value";
    }

    @Test // violation 'Missing a Javadoc comment.'
    public void annotatedSingleLineMethod() { }

    @Benchmark // violation 'Missing a Javadoc comment.'
    public Object annotatedBenchmarkMethod() { return new Object(); }

    @AnnotationWithValue(Level.Invocation) // violation 'Missing a Javadoc comment.'
    public void annotatedMethodWithAnnotationValue() { }

    private static class Nested {
        private static int map(int v) { return v + 1; } // violation 'Missing a Javadoc comment.'
    }

    private enum Includes {
        VALUE("value");

        private final String value;

        Includes(String value) { this.value = value; } // violation 'Missing a Javadoc comment.'
    }
}

@interface Test {
}

@interface Benchmark {
}

@interface AnnotationWithValue {
    /** Some javadoc. */
    Level value();
}

enum Level {
    Invocation
}

interface DiffReportInterfaceRegression {
    @SuppressWarnings("unchecked") // violation 'Missing a Javadoc comment.'
    static Object oneLineStaticMethod(Object value) { return value; }

    default Object get() { return new Object(); } // violation 'Missing a Javadoc comment.'
}
