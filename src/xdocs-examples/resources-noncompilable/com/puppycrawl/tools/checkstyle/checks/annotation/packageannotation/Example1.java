/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PackageAnnotation"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java7

// xdoc section -- start
@Deprecated
package com.puppycrawl.tools.checkstyle.checks.annotation.packageannotation;
// violation above, 'Package annotations must be in the package-info.java info.'

class Example1 {}
// xdoc section -- end
