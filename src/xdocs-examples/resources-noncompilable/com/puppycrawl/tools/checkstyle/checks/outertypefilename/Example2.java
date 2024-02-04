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
class Foo2 { // violation, 'The name of the outer type and the file do not match.'

}
// xdoc section -- end
