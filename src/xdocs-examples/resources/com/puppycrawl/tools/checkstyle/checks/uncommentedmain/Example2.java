/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UncommentedMain">
      <property name="excludedClasses" value="\.Main$"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

// xdoc section -- start
public class Example2 {
   public static void main(String... args){}   // violation, 'Uncommented main method found'
}

public class Main {
   public static void main(String[] args){}
}

public class Launch {
   //public static void main(String[] args){}
}

public class Start {
   public void main(){}
}

public class MyRecord1 {
    public void main(){}
}
// xdoc section -- end
