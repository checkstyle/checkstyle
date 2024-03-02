/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapTryWithResources implements AutoCloseable
{
    public static void main(String[] args) throws Exception {
        InputOperatorWrapTryWithResources v = new InputOperatorWrapTryWithResources();

        try (v.finalWrapper.finalField) {
        }

        try (new InputOperatorWrapTryWithResources() { }.finalWrapper.finalField) {
        }

        try ((args.length > 0 ? v
                : new InputOperatorWrapTryWithResources()).finalWrapper.finalField) {
        }

        //More than one resource
        InputOperatorWrapTryWithResources i1 = new InputOperatorWrapTryWithResources();
        try (i1; InputOperatorWrapTryWithResources i2 = new InputOperatorWrapTryWithResources()) {
        }

        InputOperatorWrapTryWithResources m1 = new InputOperatorWrapTryWithResources();
        try (m1; InputOperatorWrapTryWithResources m2 = m1;
             InputOperatorWrapTryWithResources m3 = m2;) {
        }
    }

    final static FinalWrapper finalWrapper = new FinalWrapper();
    public void method() throws Exception {
        try(this.finalWrapper.finalField) {
        }
    }
    static class FinalWrapper {
        public final AutoCloseable finalField = new AutoCloseable() {
            @Override
            public void close() throws Exception {
            }
        };
    }
    @Override
    public void close() throws Exception {
    }
}
