/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageName">
      <property name="format"
        value="^[A-Z]+(\.[A-Z]*)*$"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="PackageName"/>
      <property name="query" value="(//PACKAGE_DEF[.//IDENT
                [@text='suppressionxpathsinglefilter']])
                //IDENT[not(preceding::IDENT)]"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;
// filtered violation above 'must match pattern'

public class Example4 {}
// xdoc section -- end
