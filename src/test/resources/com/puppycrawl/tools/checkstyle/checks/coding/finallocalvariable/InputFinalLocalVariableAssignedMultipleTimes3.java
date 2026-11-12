/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.Locale;

public class InputFinalLocalVariableAssignedMultipleTimes3 {

    // Taken from findbugs
    public String foo11(final Object o, boolean getMinimal) {

        String n = System.lineSeparator();
        if (n != null) {
            return n;
        }

        try {

            String className;
            int kind; // violation "Variable 'kind' should be declared final"
            boolean isParameterToInitMethodofAnonymousInnerClass = false;
            boolean isSyntheticMethod = false;
            if (o instanceof String || o instanceof Integer) {

                String m; // violation "Variable 'm' should be declared final"
                if (o instanceof String) {
                    m = (String) o;
                    isSyntheticMethod = m.equals("");
                    kind = 1;
                    className = this.getClass().getName();
                }
                else if (o instanceof String) {
                    m = "";
                    // Don't
                    isSyntheticMethod = m.equals("");
                    className = this.getClass().getName();
                    kind = 2;
                    if ("<init>".equals(m.toLowerCase(Locale.getDefault()))) {
                        final int i = className.lastIndexOf('$');
                        if (i + 1 < className.length()) {
                            isParameterToInitMethodofAnonymousInnerClass = true;
                        }
                    }
                }
                else {
                    throw new IllegalStateException("impossible");
                }

            } // associated with method
            else if (o instanceof String) {

                className = ((String) o).getClass().getName();
                kind = 3;
            }
            else if (o instanceof String) {
                assert false;
                className = (String) o;
                kind = 4;
            }
            else {
                throw new IllegalArgumentException("Can't" + o.getClass().getName());
            }

            // <init> method parameters for inner classes don't inherit default
            // annotations
            // since some of them are synthetic
            if (isParameterToInitMethodofAnonymousInnerClass) {
                return null;
            }

            // synthetic elements should not inherit default annotations
            if (isSyntheticMethod) {
                return null;
            }
            try {
                final String c = new String(className);

                if (c != null && c.equals("")) {
                    return null;
                }
            }
            catch (Exception e) {
                assert true;
            }

            // look for default annotation
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            final int p = className.lastIndexOf('.');
            className = className.substring(0, p + 1) + "package-info";
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            return n;
        }
        catch (Exception e) {
            String.CASE_INSENSITIVE_ORDER.equals(e);
            ;
            return null;
        }

    }
}
