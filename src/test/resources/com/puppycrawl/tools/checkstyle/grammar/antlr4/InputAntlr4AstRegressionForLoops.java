package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.Serializable;

public class InputAntlr4AstRegressionForLoops {
   int k;
    void method1()
    {
        //Violations:
        for (int i = 0; i < 1; i++) {
            i++;
        }
        for (int i = 0; i < 1; i++) {
            i = i + 1;
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; i++) {
                --i;
            }
        }
        for (int i = 0, j = 0; i < 1; i++) {
            j++;
        }

        // Ok:
        for (int i = 0; i < 1; i++) {
        }
        for (int i = 0; i < 1; i++) {
            int x = i;
        }
        for (int i = 0; i < 1; i++) {
            Serializable s = new Serializable() {
                int i = 3;
                void a() {
                    System.identityHashCode(i++);
                }
            };
        }
        for (int k = 0; k < 1; k++) {
            this.k++;
        }

        String[] sa = {"a", "b"};
        for(String s:sa) {}
        for(String s:sa) {
            s = "new string";
        }
        for(int i=0;i < 10;) {
            i++;
        }
        for (int i = 0, l = 0,m=0; l < 10; i++,m=m+2) {
            l++;
            m++;
        }
        for (int i = 0; i < 10; ) {
            i = 11;
        }
        int w=0;
        for (int i=0;i<10; java.sql.Date.valueOf(""),this.i++,w++) {
            i++;
            w++;
        }
        for (int i=0,k=0; i<10 && k < 10; ++i,++k) {
            i = i + 3;
            k = k + 4;
        }
        for (int i = 0,j = 0 ; i <10; i++) {
            j++;
        }

        for (String v : sa) {
            new NestedClass() {
                public void method() {}
            };
            v = "bad";
        }
        for (int i = 0; i < 10; i += 1) {
            for (i = 7; i < 10; i += 1) {}
        }
        for (String name: new String[] {}) {
        }

        for (i = 0; i < 10; i++) {
            String name;
            name = "";
        }
        for (int EXPR = 0; EXPR < 1; EXPR++) {
            for (int j = 0; j < 1; i++) {
                i++;
            }
        }
    }
    private int i;
}

@SuppressWarnings(value = "this previously caused NullPointerException")
class VariableDeclaredBeforeTheFirstBlockBegins {
    void foo(String[] requests) {
        Class mUse4 = java.util.Enumeration[].class;
        for (String eventDataType : requests) {
            @SuppressWarnings(value = "this previously caused NullPointerException")
            String eventData;
        }
    }
}
abstract class NestedClass {
    public abstract void method();
}
