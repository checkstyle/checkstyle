/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageAnnotation"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java7

// xdoc section -- start
@Deprecated // violation below
package com.puppycrawl.tools.checkstyle.checks.annotation.packageannotation;

class Example1 {}
// xdoc section -- end
