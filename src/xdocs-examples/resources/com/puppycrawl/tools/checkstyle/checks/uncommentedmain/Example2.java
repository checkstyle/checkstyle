/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UncommentedMain">
      <property name="excludedClasses" value="\.Main$"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

// xdoc section - start
class Example2 {
  // violation below 'Uncommented main method found'
  public static void main(String... args) {
  }

  class Launch {
    // public static void main(String[] args) {}
  }

  class Start {
    // violation below 'Uncommented main method found'
    public void main() {
    }
  }

  record MyRecord1() {
    // violation below 'Uncommented main method found'
    public void main() {
    }
  }

  record MyRecord2() {
    // public void main() {}
  }
}

class Main {
  // ok, excluded by 'excludedClasses'
  public static void main(String[] args) {}
}
// xdoc section - end
