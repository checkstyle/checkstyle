package com.puppycrawl.tools.checkstyle.checks.coding;

/**
 * This file contains test inputs for InputOneStatementPerLine
 * which cause compilation problem in Eclipse 4.2.2 but still
 * must be tested.
 */

/**
 * Two import statements and one 'empty' statement
 * which are not on the same line are legal.
 */
import java.awt.event.ActionEvent;
import java.lang.annotation.Annotation;
;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;

public class InputOneStatementPerLine {
    /**
     * According to java language specifications,
     * statements end with ';'. That is why ';;'
     * may be considered as two empty statements on the same line
     * and rises violation.
     */
    ;; //warn
    static {
        new JCheckBox().addActionListener((final ActionEvent e) -> {good();});
        List<Integer> ints = new LinkedList<Integer>();
        ints.stream().map( t -> { return t * 2;} ).filter( t -> { return false;});
        ints.stream().map( t -> { int m = t * 2; return m; } ); //warn
        ints.stream().map( t -> { int m = t * 2; return m; } ); int i = 3; //warn
        ints.stream().map( t -> t * 2); int k = 4; //warn
        ints.stream().map( t -> t * 2);
        List<Integer> ints2 = new LinkedList<Integer>();
        ints.stream().map( t -> { return ints2.stream().map(w -> { return w * 2; });});
        ints.stream().map( t -> { return ints2.stream().map(w -> { int m = w * 2; return m; });}); //warn
        ints.stream().map( t -> {
            return ints2.stream().map(
                    w -> {
                        int m = w * 2;
                        return m;
                    });
        });
        ints.stream().map( t -> {
            int k = 0;
            for (int i = 0;i < 10;i++) {
                k = i + k;
            }
            return k;
        });
    }

    private static void good() {
    }
}
