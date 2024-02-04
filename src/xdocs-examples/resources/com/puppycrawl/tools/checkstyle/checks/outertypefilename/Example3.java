/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeFilename"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

// xdoc section -- start
// public class Example1 {
// // This class is commented out
// }

interface Foo3 { // violation

}
// xdoc section -- end
