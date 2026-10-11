package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import org.apache.tools.ant.types.Path;

public class InputAntlr4AstRegressionExpressions {

    public void method() {
        InputAntlr4AstRegressionExpressions.<Path>method("classpath");
    }

    public static <T> T method(String fieldName) {
        return null;
    }

    class B {
        public Object clone() throws CloneNotSupportedException {
            super.clone();
            return null;
        }

        void clone(Object asd, Object asd2) {
        }
    }

    class C extends B {
        void method2() {
            new Runnable() {
                @Override
                public void run() {
                    C.super.clone(null, null);
                }
            };
        }
    }

}
