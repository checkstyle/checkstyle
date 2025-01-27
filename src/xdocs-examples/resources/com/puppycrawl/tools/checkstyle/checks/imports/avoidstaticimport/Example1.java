/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStaticImport"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstaticimport;

// xdoc section -- start
import static java.lang.Math.pow;          // violation, 'Using a static member import should be avoided.'
import static java.lang.System.*;          // violation, 'Using a static member import should be avoided.'
import java.io.File;                       // OK
import java.util.*;                        // OK
// xdoc section -- end

class Example1{}
