/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="useContainerOrderingForStatic" value="false"/>
      <property name="ordered" value="true"/>
      <property name="option" value="top"/>
      <property name="caseSensitive" value="false"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Byte.MAX_VALUE;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.valueOf;
import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
// violation above 'Wrong order for...'
// xdoc section -- end

public class Example9 { }
