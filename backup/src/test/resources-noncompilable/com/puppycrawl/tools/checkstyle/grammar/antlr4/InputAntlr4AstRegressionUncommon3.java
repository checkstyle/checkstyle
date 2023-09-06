//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionUncommon3 implements AutoCloseable {
    @Override
    public void close() throws Exception {

    }

    interface SAM {
        int m();
    }

    interface SuperI {
        public default int foo() { return 1234; }
    }

    interface I extends SuperI {
    }

    interface T extends I {
        public default SAM boo() { return I.super::foo; }
    }

    interface DSPRI { String m(String a); }

    interface DSPRA {
        default String xsA__(String s) {
            return "A__xsA:" + s;
        }

        default String xsAB_(String s) {
            return "AB_xsA:" + s;
        }

    }

    interface DSPRB extends DSPRA {

        default String xsAB_(String s) {
            return "AB_xsB:" + s;
        }

        default String xs_B_(String s) {
            return "_B_xsB:" + s;
        }

    }

    public class MethodReferenceTestSuperDefault implements DSPRB {

        public void testMethodReferenceSuper() {
            DSPRI q;

            q = DSPRB.super::xsA__;

            q = DSPRB.super::xsAB_;

            q = DSPRB.super::xs_B_;
        }

    }

    void test() throws Exception {
        try (InputAntlr4AstRegressionUncommon3.this) {
            System.out.println("Unable to process");
        }
    }

}
