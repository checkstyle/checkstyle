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

// non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection; // violation, 'Illegal import'
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Date; // violation, 'Illegal import'
import sun.applet.*; // violation, 'Illegal import'

public class Example3 {}
// xdoc section -- end
