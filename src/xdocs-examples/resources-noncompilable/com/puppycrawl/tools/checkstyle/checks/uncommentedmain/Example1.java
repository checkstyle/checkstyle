/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UncommentedMain"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

// xdoc section -- start
class Example1 {
  // violation below, 'Uncommented main method found'
  public static void main(String... args){}
}

class Main {
  // violation below, 'Uncommented main method found'
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
