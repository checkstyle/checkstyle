package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

/* Config:
 *
 * default
 */
public class InputMatchXpathExecutableStatementCount {
    public void test() {
        while (true) {
            Runnable runnable = new Runnable() {
                public void run() {
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    public void test2() {
        test();
        "HELLO WORLD".toString();
        int a = 123;
    }
}
