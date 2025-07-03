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

// xdoc section -- start
class Example2 {
  // violation below, 'Uncommented main method found'
  public static void main(String... args){}
}

class Main {

  public static void main(String[] args){}
}

class Launch {
  //public static void main(String[] args){}
}

class Start {
  public void main(){}
}

record MyRecord1() {
  public void main(){}
}

record MyRecord2() {
  //public void main(){}
}
// xdoc section -- end
