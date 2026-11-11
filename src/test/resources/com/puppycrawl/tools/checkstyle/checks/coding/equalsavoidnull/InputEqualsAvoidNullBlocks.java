/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

import java.util.Map;

public class InputEqualsAvoidNullBlocks {
    static {
        final String s = "";
        s.equals(""); // violation 'left .* of .* equals'
        {
            final String x = "";
            class A {
                void foo() {
                    s.equals(""); // violation 'left .* of .* equals'
                    x.equals(""); // violation 'left .* of .* equals'
                }
            }
        }
    }
    void foo(String param) {
        try {
            param.equals(""); // violation 'left .* of .* equals'
            do {
                String s = "";
                s.equals(""); // violation 'left .* of .* equals'
            } while (param.equals("")); // violation 'left .* of .* equals'
        } catch (Exception e) {
            while (param.equals("")) { // violation 'left .* of .* equals'
                for (String s = ""; s.equals(""); ){ // violation 'left .* of .* equals'
                    if (s.equals("")) { // violation 'left .* of .* equals'
                        synchronized (this) {
                            switch (s) {
                                case "1": String str = "";
                                str.equals(""); // violation 'left .* of .* equals'
                                case "2": s.equals(""); str = ""; // violation 'left .*of.*equals'
                                str.equals(""); // violation 'left .* of .* equals'
                                case "3": param.equals(""); // violation 'left .* of .* equals'
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
    enum EmbeddedEnum {
        A(129),
        B(283),
        C(1212) {
            String constDefVar;
            public void doSomething() {
                constDefVar.equals(""); // violation 'left .* of .* equals'
            }
        };

        Map.Entry<String,Long> enumInstance;

        EmbeddedEnum(int i) {
            enumInstance.equals("");
        }

        public static void doSomethingStatic() {
            enumStatic.equals(""); // violation 'left .* of .* equals'
            enumStatic.equals(null);
        }
        static String enumStatic;
    }

    private String foo() {return "";}
    private Object foo(int i) {return i;}

    Object o2 = new Object();
    Object o3 = new Object();
    private void bar() {
        foo().equals(""); // methods are not checked
        foo(0).equals("");
        this.foo().equals("");
        Object o1 = new Object(); o1.equals("");
        o2.equals(""); String o2 = "";
        o3.equals("");
String o3 = "";
    }
}
