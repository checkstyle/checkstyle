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
import java.sql.Connection;     // violation
import java.util.List;          // violation
import java.util.Enumeration;
import java.util.Arrays;        // violation
import java.util.Date;

public class Example5 {}
// xdoc section -- end
