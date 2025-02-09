/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="[\\/]src[\\/]test[\\/]java[\\/]"/>
      <property name="checks" value="Javadoc*"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value=".*Tests\.java"/>
      <property name="checks" value="Javadoc*"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="generated-sources"/>
      <property name="checks" value="[a-zA-Z0-9]*"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example14 {}

/*
// xdoc section -- start
.../MyApplication.java // violation, @return tag should be present
.../MyApplicationTests.java // filtered violation '@return tag should be present'
.../src/test/java/javaInsideTestJavaPackage.java // filtered violation '@return tag'
// xdoc section -- end
*/
