/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="allowStaticMemberImports" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;
import java.io.*; // violation, 'Using the '.*' form of import should be avoided.'
import static java.lang.Math.*;
import java.util.*; // violation, 'Using the '.*' form of import should be avoided.'
import java.net.*; // violation, 'Using the '.*' form of import should be avoided.'
// xdoc section -- end

class Example4 {}
