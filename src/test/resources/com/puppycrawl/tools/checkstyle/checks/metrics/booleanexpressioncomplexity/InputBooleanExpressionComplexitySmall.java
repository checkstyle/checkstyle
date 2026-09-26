/*
BooleanExpressionComplexity
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformExpressionsAsOne = false


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexitySmall {
    public void method() {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                java.lang.reflect.Method stopDispatching = this.getClass()
                        .getDeclaredMethod("method", null);
                stopDispatching.setAccessible(true);
                stopDispatching.invoke(this, null);
            }
            catch (java.lang.reflect.InvocationTargetException | NoSuchMethodException
                    | IllegalAccessException ex) {
            }
        });
    }

   boolean underBor(boolean a) {
        return a | (p() && q() && r() && s() && t());
    } // violation above 'Boolean expression complexity is 5 (max allowed is 1).'

    private boolean t() {
        return false;
    }

    private boolean s() {
        return true;
    }

    private boolean r() {
        return false;
    }

    private boolean q() {
        return false;
    }

    private boolean p() {
        return true;
    }

    boolean check(Object o, Object p, Object q, Object r, Object s) {
        // violation below 'Boolean expression complexity is 4 (max allowed is 1).'
        return o instanceof String && p instanceof Integer
                && q instanceof Long && r instanceof Double
                && s instanceof Float;
    }


}
