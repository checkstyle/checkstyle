/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport">
      <property name="maxAllowed" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;
import java.io.*;
import static java.lang.Math.*; // violation, 'form of import should be avoided.'
import java.util.*; // violation, 'form of import should be avoided.'
import java.net.*; // violation, 'form of import should be avoided.'
// xdoc section -- end

class Example7 {}
