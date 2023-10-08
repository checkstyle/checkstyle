/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="excludes" value="java.io,java.net,java.lang.Math"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;         // OK
import java.io.*;                 // OK
import static java.lang.Math.*;   // OK
import java.util.*;               // violation
import java.net.*;                // OK
// xdoc section -- end

class Example2 {}
