/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UncommentedMain"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

// xdoc section -- start
public class Example1 {
   public static void main(String... args){}   // violation, 'Uncommented main method found'
}

public class Main {
   public static void main(String[] args){}   // violation, 'Uncommented main method found'
}

public class Launch {
   //public static void main(String[] args){}
}

public class Start {
   public void main(){}
}

record MyRecord1 {
    MyRecord1() {}
    public static void main(String[] args){}                // violation, 'Uncommented main method found'
}

record MyRecord2 {
    MyRecord2() {}
    //public void main(){}
}
// xdoc section -- end
