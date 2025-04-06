/*
HiddenField
ignoreFormat = (default)
ignoreConstructorParameter = (default)false
setterCanReturnItsClass = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,PATTERN_VARIABLE_DEF,LAMBDA,RECORD_COMPONENT_DEF
ignoreAbstractMethods = (default)false
ignoreSetter = (default)false

*/

//non-compiled with javac: Compilable with Java21
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
