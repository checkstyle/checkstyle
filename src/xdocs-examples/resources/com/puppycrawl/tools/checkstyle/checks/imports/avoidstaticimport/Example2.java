/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStaticImport">
      <property name="excludes" value="java.lang.System.out,java.lang.Math.*"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstaticimport;

// xdoc section -- start
import static java.lang.Math.*;            // OK
import static java.lang.System.out;        // OK
import static java.lang.Integer.parseInt;  // violation
import java.io.*;                          // OK
import java.util.*;                        // OK
// xdoc section -- end

class Example2{}
