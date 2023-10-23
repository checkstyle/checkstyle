/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantImport"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

// xdoc section -- start
import static com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.Example1.*; // OK, static import
import static java.lang.Integer.MAX_VALUE; // OK, static import

// violation below, 'Redundant import from the same package'
import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.Example1;
import java.lang.String; // violation, "Redundant import from the java.lang package"
import java.util.Scanner;
import java.util.Scanner; // violation 'Duplicate import to line 18 - java.util.Scanner'

public class Example1{ }
// xdoc section -- end

