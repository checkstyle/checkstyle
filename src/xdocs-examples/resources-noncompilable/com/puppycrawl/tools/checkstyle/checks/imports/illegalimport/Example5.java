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

// non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection; // violation, 'Illegal import'
import java.util.List; // violation, 'Illegal import'
import java.util.Enumeration;
import java.util.Arrays; // violation, 'Illegal import'
import java.util.Date;
import sun.applet.*; // violation, 'Illegal import'

public class Example5 {}
// xdoc section -- end
