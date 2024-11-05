/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OneStatementPerLine"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

// xdoc section -- start
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.PipedReader;
import java.io.BufferedReader; import java.io.EOFException;
// violation above 'Only one statement per line allowed.'

public class Example1 {

  public void method1() {
    int var1; int var2; // violation, 'Only one statement per line allowed.'
    var1 = 1; var2 = 2; // violation, 'Only one statement per line allowed.'
  }

  public void method2() {
    int var2;
    // violation below 'Only one statement per line allowed.'
    Object obj1 = new Object(); Object obj2 = new Object();
    int var1 = 1
    ; var2 = 2; // violation, 'Only one statement per line allowed.'
    int o = 1, p = 2
    , r = 5; int t; // violation, 'Only one statement per line allowed.'
  }

  public void method3() throws IOException {
    final OutputStream s1 = new PipedOutputStream();
    final OutputStream s2 = new PipedOutputStream();
    try (s1; s2; OutputStream s3 = new PipedOutputStream()) {
    }

    try (Reader r = new PipedReader(); s2; Reader s3 = new PipedReader()) {
    }
  }

}
// xdoc section -- end
