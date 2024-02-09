/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OuterTypeFilename"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

// xdoc section -- start
enum Foo4 {} // violation, 'The name of the outer type and the file do not match.'
class Example4ButNotSameName {}
// xdoc section -- end
