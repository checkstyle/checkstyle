/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="illegalClasses"
        value="java.util.Date, java.sql.Connection"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection; // violation, import of illegal class
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Date; // violation, import of illegal class
import sun.applet.*; // violation, illegal import of all sun packages by default

public class Example3 {}
// xdoc section -- end
