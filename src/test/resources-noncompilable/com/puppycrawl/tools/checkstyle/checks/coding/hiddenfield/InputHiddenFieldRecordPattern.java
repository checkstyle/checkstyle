/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldRecordPattern {
    private String s;
    private int x;
    static int z;

    record Color(String name, int value) { }
    record Point(Color c, int x, int y) { }

    public boolean doStuff(Object f) {
            return f instanceof Color(String s, int x);
            // 2 violations above:
            //    ''s' hides a field'
            //    ''x' hides a field'
    }


    public void test(Object f) {
        if (f instanceof Color(String s, int x)) {
        // 2 violations above:
        //    ''s' hides a field'
        //    ''x' hides a field'
            System.out.println(s);
        }
    }

   public void test2(Object f) {
        if (f instanceof Point(Color(String s, int x), _, int z)) {
        // 3 violations above:
        //    ''s' hides a field'
        //    ''x' hides a field'
        //    ''z' hides a field'
            System.out.println(s);
        }
        switch (f) {
            case Point(Color(String s, int x), _, _) : break;
            // 2 violations above:
            //    ''s' hides a field'
            //    ''x' hides a field'
            default:
                throw new IllegalStateException();
        }
   }
}
