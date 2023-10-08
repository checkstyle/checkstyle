/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="allowStaticMemberImports" value="true"/>
      <property name="excludes" value="java.io,java.net"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;
import java.io.*;
import static java.lang.Math.*;
import java.util.*;               // violation
import java.net.*;
// xdoc section -- end

class Example6 {}
