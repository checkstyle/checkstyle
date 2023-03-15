//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

@Deprecated @ProblemCauser
public record InputJava14LocalRecordAnnotation() {

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
