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

class Foo5 { // violation, 'The name of the outer type and the file do not match.'

}
// xdoc section -- end
