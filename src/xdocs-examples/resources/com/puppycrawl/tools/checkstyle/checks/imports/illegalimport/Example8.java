/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="regexp" value="true"/>
      <property name="illegalClasses"
        value="^java\.util\.(List|Arrays), ^java\.sql\.Connection"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.util.Enumeration;
import java.util.Date;

public class Example8 {}
// xdoc section -- end
