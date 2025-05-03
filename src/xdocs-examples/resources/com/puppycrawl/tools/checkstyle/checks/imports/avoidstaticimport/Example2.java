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
import static java.lang.Math.*;
import static java.lang.System.out;
import static java.lang.Integer.parseInt;  // violation, 'Using a static member import should be avoided.'
import java.io.*;
import java.util.*;
// xdoc section -- end

class Example2{}
