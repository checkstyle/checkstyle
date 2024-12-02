/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport">
      <property name="regexp" value="true"/>
      <property name="illegalPkgs" value="java\.util"/>
    </module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection;
import java.util.List; // violation, import class of illegal package by regexp
import java.util.Enumeration; // violation, import class of illegal package by regexp
import java.util.Arrays; // violation, import class of illegal package by regexp
import java.util.Date; // violation, import class of illegal package by regexp
import sun.applet.*;

public class Example4 {}
// xdoc section -- end
