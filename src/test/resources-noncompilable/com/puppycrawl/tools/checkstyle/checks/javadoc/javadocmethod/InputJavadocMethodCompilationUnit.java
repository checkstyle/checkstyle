/*
JavadocMethod
validateThrows = true
tokens = (default)METHOD_DEF,CTOR_DEF,ANNOTATION_FIELD_DEF,COMPACT_CTOR_DEF
allowInlineReturn = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingReturnTag = (default)false
allowMissingParamTags = (default)false
allowedAnnotations = (default)Override


*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

@Deprecated @ProblemCauser
public record InputJavadocMethodCompilationUnit() {

    public void doThing() {
        // No error unless an annotation is present
        @ProblemCauser
        record Test(String hello, int world) {}

        @ProblemCauser
        class TestClass {}

        @ProblemCauser @Deprecated
        record Test2(String hello, int world) {}

        @ProblemCauser @Deprecated
        class TestClass2 {}
    }

    @Deprecated @ProblemCauser
    public static record AnnotatedRecordWithVisibilityModifier1(String string){}

    @Deprecated @ProblemCauser
    protected record AnnotatedRecordWithVisibilityModifier2(Integer integer){}

    @Deprecated @ProblemCauser
    private record AnnotatedRecordWithVisibilityModifier3(String x, int y){}

    @Deprecated @ProblemCauser
    public static class AnnotatedClassWithVisibilityModifier1 {}

    @Deprecated @ProblemCauser
    protected class AnnotatedClassWithVisibilityModifier2 {}

    @Deprecated @ProblemCauser
    private class AnnotatedClassWithVisibilityModifier3 {}

}

@interface ProblemCauser {
}
