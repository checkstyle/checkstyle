package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * query: //METHOD_DEF[count(./SLIST/*) > 2]
 */
public class InputMatchXpathExecutableStatementCount {
    public void test() {
        while (true) { // ok
            Runnable runnable = new Runnable() {
                public void run() {
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    public void test2() { // violation
        test();
        "HELLO WORLD".toString();
        int a = 123;
    }
}
