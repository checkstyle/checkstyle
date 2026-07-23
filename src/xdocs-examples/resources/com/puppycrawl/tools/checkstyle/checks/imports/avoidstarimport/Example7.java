/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="maxAllowedStarImports" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;
import java.io.*;
import static java.lang.Math.*; // violation 'Only '1' star import is allowed per file.'
import java.util.*; // violation 'Only '1' star import is allowed per file.'
import java.net.*; // violation 'Only '1' star import is allowed per file.'
// xdoc section -- end

public class Example7 {}
