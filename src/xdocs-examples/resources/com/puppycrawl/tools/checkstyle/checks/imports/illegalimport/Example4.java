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


package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection;
import java.util.List; // violation, 'Illegal import'
import java.util.Enumeration; // violation, 'Illegal import'
import java.util.Arrays; // violation, 'Illegal import'
import java.util.Date; // violation, 'Illegal import'
import sun.misc.*;

public class Example4 {}
// xdoc section -- end
