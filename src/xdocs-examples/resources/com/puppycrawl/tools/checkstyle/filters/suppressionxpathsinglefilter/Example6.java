/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example6files.MyMethod"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="FileOne.java"/>
      <property name="checks" value="MyMethod"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

/*
// xdoc section -- start
public class FileOne {
  public void MyMethod() {} // OK
}

public class FileTwo {
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
*/
public class Example6 {}