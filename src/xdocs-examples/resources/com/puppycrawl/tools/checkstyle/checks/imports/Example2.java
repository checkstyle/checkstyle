/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="illegalPkgs" value="java.io, java.sql"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*; // violation, 'Illegal import'
import java.lang.ArithmeticException;
import java.sql.Connection; // violation, 'Illegal import'
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Date;
import sun.applet.*;

public class Example2 {}
// xdoc section -- end
