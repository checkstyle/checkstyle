/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="validateThrows" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// xdoc section -- start
public class Example7 {

  /**
   * Actual exception thrown is child class of class that is declared in throws.
   * It is limitation of checkstyle (as checkstyle does not know type hierarchy).
   * Javadoc is valid not declaring FileNotFoundException
   * BUT checkstyle can not distinguish relationship between exceptions.
   * @param file some file
   * @throws IOException if some problem
   */
  public void doSomething8(File file) throws IOException {
    if (file == null) {
      // violation below, 'Expected @throws tag for 'FileNotFoundException''
      throw new FileNotFoundException();
    }
  }

  /**
   * Exact throw type referencing in javadoc even first is parent of second type.
   * It is a limitation of checkstyle (as checkstyle does not know type hierarchy).
   * This javadoc is valid for checkstyle and for javadoc tool.
   * @param file some file
   * @throws IOException if some problem
   * @throws FileNotFoundException if file is not found
   */
  public void doSomething9(File file) throws IOException {
    if (file == null) {
      throw new FileNotFoundException();
    }
  }

  /**
   * Ignore try block, but keep catch and finally blocks.
   *
   * @param s String to parse
   * @return A positive integer
   */
  public int parsePositiveInt(String s) {
    try {
      int value = Integer.parseInt(s);
      if (value <= 0) {
        throw new NumberFormatException(value + " is negative/zero"); //try
      }
      return value;
    } catch (NumberFormatException ex) {
      // violation below, 'Expected @throws tag for 'IllegalArgumentException''
      throw new IllegalArgumentException("Invalid number", ex);
    } finally {
      // violation below, 'Expected @throws tag for 'IllegalStateException''
      throw new IllegalStateException("Should never reach here");
    }
  }

  /**
   * Try block without catch is not ignored.
   *
   * @return a String from standard input, if there is one
   */
  public String readLine() {
    try (Scanner sc = new Scanner(System.in)) {
      if (!sc.hasNext()) {
        // violation below, 'Expected @throws tag for 'IllegalStateException''
        throw new IllegalStateException("Empty input");
      }
      return sc.next();
    }
  }

  /**
   * Lambda expressions are ignored.
   *
   * @param s a String to be printed at some point in the future
   * @return a Runnable to be executed when the string is to be printed
   */
  public Runnable printLater(String s) {
    return () -> {
      if (s == null) {
        throw new NullPointerException();
      }
      System.out.println(s);
    };
  }
}
// xdoc section -- end
