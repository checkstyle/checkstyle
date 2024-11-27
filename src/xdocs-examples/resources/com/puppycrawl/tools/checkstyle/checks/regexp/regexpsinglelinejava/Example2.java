/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RegexpSinglelineJava">
      <!-- . matches any character, so we need to
           escape it and use \. to match dots. -->
      <property name="format" value="System\.out\.println"/>
      <property name="ignoreComments" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsinglelinejava;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

// xdoc section -- start
class Example2 {

  private void testMethod1() {
    int example3 = 0;
    System.out.println(""); // violation, instruction matches illegal pattern
    System.out.
    println("");
  }

  private void testMethod2() throws IOException {
    FileReader in = new FileReader("path/to/input");
    int ch = in.read();
    while(ch != -1) {
      System.out.print((char)ch);
      ch = in.read();
    }

    FileWriter out = new FileWriter("path/to/output");
    out.write("something");
  }

  public void testMethod3(){
    final Logger logger = Logger.getLogger(Example2.class.getName());

    logger.info("first");
    logger.info("second");
    logger.info("third");
    System.out.println("fourth"); // violation, instruction matches illegal pattern
    logger.info("fifth");
  }
}
// xdoc section -- end
