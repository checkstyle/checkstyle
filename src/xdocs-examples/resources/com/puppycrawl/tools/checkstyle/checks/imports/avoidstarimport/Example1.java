/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStarImport"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstarimport;

// xdoc section -- start
import java.util.Scanner;
import java.io.*; // violation, 'Using the '.*' form of import should be avoided.'
import static java.lang.Math.*; // violation, 'form of import should be avoided.'
import java.util.*; // violation, 'Using the '.*' form of import should be avoided.'
import java.net.*; // violation, 'Using the '.*' form of import should be avoided.'
// xdoc section -- end

class Example1 {}
