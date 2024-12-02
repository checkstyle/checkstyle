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

//non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection; // violation, import of illegal class by regexp
import java.util.List; // violation, import of illegal class by regexp
import java.util.Enumeration;
import java.util.Arrays; // violation, import of illegal class by regexp
import java.util.Date;
import sun.applet.*; // violation, illegal import of all sun packages by default

public class Example5 {}
// xdoc section -- end
