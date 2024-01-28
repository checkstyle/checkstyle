/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineSingleLineThree {
    /**
     * Dummy variable to work on.
     */
    private int one = 0;

    /**
     * Dummy variable to work on.
     */
    private int two = 0;
    /**
     * This method contains break, while-loop
     * and for-loop statements.
     */
    private void foo3() {
        do {
            one++;
            if (two > 4) {
                break; //legal
            }
            one++;
            two++;
        } while (two < 7); //legal

        /**
         *  One statement inside for block is legal.
         */
        for (int i = 0; i < 10; i++) one = 5;

        /**
         *  One statement inside for block where
         *  increment expression is empty is legal.
         */
        for (int i = 0; i < 10;) one = 5;

        /**
         *  One statement inside for block where
         *  increment and conditional expressions are empty
         *  (forever loop) is legal
         */
        for (int i = 0;;) one = 5;
    }

    public void foo4() {
        /**
         * a "forever" loop.
         */
        for(;;){} //legal
    }

    public void foo5() {
        /**
         *  One statement inside for block is legal
         */
        for (;;) { one = 5; }
    }

    public void foo6() {
        bar(() -> {
            return;}, () -> {return;});
    }

    void bar(Runnable r1, Runnable r2) { }
}
