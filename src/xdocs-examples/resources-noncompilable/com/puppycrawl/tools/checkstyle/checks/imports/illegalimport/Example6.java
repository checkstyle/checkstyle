/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="illegalModules" value="java.base, java.logging"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import module java.base; // violation, 'Illegal import'
import module java.xml;
import module java.sql;
import module java.logging; // violation, 'Illegal import'
import module java.se;

public class Example6 {}
// xdoc section -- end
