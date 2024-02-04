/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeFilename"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

// xdoc section -- start
interface Foo3 { // violation

}
// xdoc section -- end
