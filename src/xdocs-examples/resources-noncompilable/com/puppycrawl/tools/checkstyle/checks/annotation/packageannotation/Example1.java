/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageAnnotation"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java7

// xdoc section -- start
@Deprecated
// violation below, 'Package annotations must be in the package-info.java info.'
package com.puppycrawl.tools.checkstyle.checks.annotation.packageannotation;

class Example1 {}
// xdoc section -- end
