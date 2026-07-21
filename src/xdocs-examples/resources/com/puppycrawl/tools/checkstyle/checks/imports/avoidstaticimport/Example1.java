/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidStaticImport"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstaticimport;

// xdoc section -- start
import static java.lang.Math.*;           // violation, 'Using a static member import should be avoided.'
import static java.lang.System.out;       // violation, 'Using a static member import should be avoided.'
import static java.lang.Integer.parseInt; // violation, 'Using a static member import should be avoided.'

import java.io.File;
import java.util.List;
// xdoc section -- end

class Example1 {}
