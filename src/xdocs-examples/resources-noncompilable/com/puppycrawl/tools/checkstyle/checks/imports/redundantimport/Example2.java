/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantImport"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

// xdoc section -- start
import module java.base;
import module java.logging;
import module java.base; // violation 'Duplicate import to line 14 - java.base'

public class Example2{ }
// xdoc section -- end

