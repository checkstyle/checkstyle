/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalIdentifierName">
      <property name="format" value="^[A-Z][a-zA-Z0-9]*$"/>
      <property name="tokens" value="CLASS_DEF"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="[\\/]src[\\/]test[\\/]java[\\/]"/>
      <property name="checks" value="IllegalIdentifierName"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value=".*Tests\.java"/>
      <property name="checks" value="IllegalIdentifierName"/>
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
.../src/myApplication.java // violation, Name 'myApplication' must match pattern.
.../src/myApplicationTests.java // filtered violation 'must match pattern'
.../src/test/java/insidePackage.java // filtered violation 'must match pattern'
// xdoc section -- end
*/
