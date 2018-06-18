package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class InputRightCurlyBracePolicyAloneForAnonymousClass {

    private static final Set<String> TREE_SET = Collections.unmodifiableSortedSet(Arrays
            .stream(new String[] {"br", "li", "dt", "dd", "hr", "img", "p", "td", "tr", "th", })
                    .collect(Collectors.toCollection(TreeSet::new))); // for coverage
    public static final String STRING = new String("String");
    public static final Object OBJECT1 = new Object();
    public static final Object OBJECT2 = new Object() {
        @Override
        public String toString() {
            return "";
        }
    }; int field1;   // violation

    int field2;


    static void method1(Object o) {
        new Object() { @Override public String toString() { return ""; } int binary = 10; }; //v
    }

    static void method2(Thread t, int x) {
        method1(new Object() {
            @Override
            protected Object clone() {
                return new Object() { @Override protected void finalize() { "".toString(); } }; //v
            }
        }); // violation
    }

    void method3() {
        method2(new Thread() {
            @Override
            public void run() {
                start();
                super.run();
            }
        }, field1); // violation



        method2(new Thread() {
                    @Override
                    public void run() {
                        field1++;
                        start();
                        super.run();
                    }
                },      // no violation
                0);
    }

    static Thread method4(Runnable r, String s) {
        r.run();
        return new Thread(r,s) {
            @Override
            public String toString() {
                return "name:"+s;
            }
        }; // no violation
    }

    static Thread method4(Runnable r) {
        r.run();
        String s = new Object().toString();
        return new Thread(r, new Object() {
            @Override
            public String toString() {
                return s;
            }
        }.toString());  // violation
    }

    void method5(int t) {
        method2(method4(new Runnable() {
                    @Override
                    public void run() {
                        field1+=field2;
                        field2++;
                        if (t>field1) {
                            run();
                        }
                    }
                }),        // violation
                field1);

        method2(method4(new Runnable() {
            @Override
            public void run() {
                field1+=field2;
                field2++;
                if (t>field1) {
                    run();
                }
            }
        }), field1); // violation
    }
}
