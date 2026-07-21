package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

/*
 * This file contains test inputs for InputOneStatementPerLine2.
 */

import java.awt.event.ActionEvent;
import java.lang.Integer;
import java.lang.String;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;

/** Some javadoc. */
public class InputOneStatementPerLine2 {
  /*
   * According to java language specifications,
   * statements end with ';'. That is why ';;'
   * may be considered as two empty statements on the same line
   * and rises violation.
   */
  ;; // violation 'Only one statement per line allowed.'
  static {
    new JCheckBox().addActionListener((final ActionEvent e) -> { good(); });
    // violation above ''{' at column 64 should have line break after.'
    List<Integer> ints = new LinkedList<Integer>();
    ints.stream().map(t -> { return t * 2; }).filter(t -> { return false; });
    // 2 violations above:
    //  ''{' at column 28 should have line break after.'
    //  ''{' at column 59 should have line break after.'
    ints.stream().map(t -> { int m = t * 2; return m; });
    // 2 violations above:
    //  ''{' at column 28 should have line break after.'
    //  'Only one statement per line allowed.'
  }

  private static void good() {}
}