/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck">
      <property name="format"
        value="^[a-z]+(\.[a-z][a-z0-9]*)*$"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="PackageName"/>
      <property name="query" value="(//PACKAGE_DEF[.//IDENT[@text='File']])//IDENT[not(preceding::IDENT)]"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

/*
// xdoc section -- start
package File; // OK

public class FileOne {}
// xdoc section -- end
*/

public class Example4 {}
