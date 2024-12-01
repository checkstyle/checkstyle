/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="illegalPkgs" value="java.io, java.sql"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;           // violation
import java.lang.ArithmeticException;
import java.sql.Connection; // violation
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;

public class Example3 {}
// xdoc section -- end
