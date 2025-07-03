/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UncommentedMain"/>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

// xdoc section -- start
class Example1 {
  // violation below, 'Uncommented main method found'
  public static void main(String... args){}
}

class MainOne {
  // violation below, 'Uncommented main method found'
  public static void main(String[] args){}
}

class LaunchOne {
  //public static void main(String[] args){}
}

class StartOne {
  public void main(){}
}

record MyRecordOne() {
  public void main(){}
}

record MyRecordTwo() {
  //public void main(){}
}
// xdoc section -- end
