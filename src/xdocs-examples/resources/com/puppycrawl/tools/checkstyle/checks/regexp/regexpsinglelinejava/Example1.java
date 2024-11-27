/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RegexpSinglelineJava"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpsinglelinejava;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

// xdoc section -- start
class Example1 {

  private void testMethod1() {
    int example3 = 0;
    System.out.println("");
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
    final Logger logger = Logger.getLogger(Example1.class.getName());

    logger.info("first");
    logger.info("second");
    logger.info("third");
    System.out.println("fourth");
    logger.info("fifth");
  }
}
// xdoc section -- end
