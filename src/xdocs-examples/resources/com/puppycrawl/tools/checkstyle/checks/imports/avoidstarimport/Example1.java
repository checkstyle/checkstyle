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
import java.io.*;                 // violation
import static java.lang.Math.*;   // violation
import java.util.*;               // violation
import java.net.*;                // violation
// xdoc section -- end

class Example1 {}
