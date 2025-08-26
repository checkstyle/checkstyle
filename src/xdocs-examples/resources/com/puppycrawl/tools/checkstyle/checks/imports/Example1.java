/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalImport"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

// xdoc section -- start
import java.io.*;
import java.lang.ArithmeticException;
import java.sql.Connection;
import java.util.List;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Date;
import sun.applet.*; // violation, 'Illegal import'

public class Example1 {}
// xdoc section -- end
