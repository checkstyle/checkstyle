/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InnerAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// xdoc section -- start
public class Example1 {
  void foo() throws IOException {
    int a, b;
    a = b = 5; // violation, 'Inner assignments should be avoided'
    a = b += 5; // violation, 'Inner assignments should be avoided'

    a = 5;
    b = 5;
    a = 5; b = 5;

    double myDouble;
    double[] doubleArray = new double[] {myDouble = 4.5, 15.5};
    // violation above, 'Inner assignments should be avoided'
    String nameOne;
    List<String> myList = new ArrayList<String>();
    myList.add(nameOne = "tom"); // violation, 'Inner assignments should be avoided'

    for (int k = 0; k < 10; k = k + 2) {
        // some code
    }

    boolean someVal;
    if (someVal = true) { // violation, 'Inner assignments should be avoided'
        // some code
    }

    while (someVal = false) {} // violation, 'Inner assignments should be avoided'

    InputStream is = new FileInputStream("textFile.txt");
    while ((b = is.read()) != -1) { // OK, this is a common idiom
        // some code
    }
  }

  boolean testMethod() {
    boolean val;
    return val = true; // violation, 'Inner assignments should be avoided'
  }
}
// xdoc section -- end
