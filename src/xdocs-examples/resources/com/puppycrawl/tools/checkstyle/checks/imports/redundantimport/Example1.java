/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantImport"/>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

// OK, static import
import static com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.Example2.*;
import static java.lang.Integer.MAX_VALUE; // OK, static import

// violation, imported from the same package as the current package
import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.Example2.*;
import java.lang.String; // violation, the class imported is from the 'java.lang' package
import java.util.Scanner; // OK
import java.util.Scanner; // violation, it is a duplicate of another import
// xdoc section -- end

public class Example1{ };
