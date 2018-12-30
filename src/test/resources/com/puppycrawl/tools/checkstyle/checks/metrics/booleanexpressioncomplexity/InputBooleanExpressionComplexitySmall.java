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
}
