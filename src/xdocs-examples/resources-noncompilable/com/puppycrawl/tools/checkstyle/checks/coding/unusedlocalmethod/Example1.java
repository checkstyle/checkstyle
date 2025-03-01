/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalMethod">
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

import java.io.*;
import java.util.function.Predicate;

// xdoc section -- start
class Example1 {
  {
    int k = 12; // violation, assign and update but never use 'k'
    k++;
  }

  int a;

  Example1(int a) { // ok, as 'a' is a constructor parameter not a local variable
    this.a = 12;
  }

  void method(int b) {
    int[] arr = {1, 2, 3};  // violation, unused named local variable 'arr'
    int[] anotherArr = {1}; // ok, 'anotherArr' is accessed
    anotherArr[0] = 4;
  }

  String convertValue(String newValue) {
    String s = newValue.toLowerCase(); // violation, unused named local variable 's'
    String _ = newValue.toLowerCase(); // ok, '_' is unnamed variable
    return newValue.toLowerCase();
  }

  void read() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String s; // violation, unused named local variable 's'
    while ((s = reader.readLine()) != null) {
    }
    try (BufferedReader reader1 = // ok, 'reader1' is a resource
             new BufferedReader(new FileReader("abc.txt"))) {
    }
    try {
    } catch (Exception e) {
    }  // ok, 'e' is an exception parameter
  }

  void loops() {
    int j = 12;
    for (int i = 0; j < 11; i++)  // violation, unused named local variable 'i'
      for (int p = 0; j < 11; p++) p /= 2;    // ok, 'p' is used
    for (Integer _ : new int[0]) {
    } // ok, '_' is unnamed variable
  }

  void lambdas() {
    Predicate<String> obj = (String str) -> true; // ok, 'str' is a lambda parameter
    obj.test("Test");
  }
}
// xdoc section -- end
