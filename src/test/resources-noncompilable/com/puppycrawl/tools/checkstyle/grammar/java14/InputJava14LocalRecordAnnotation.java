//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

class InputJava14LocalRecordAnnotation {

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

}

@interface ProblemCauser {
}
