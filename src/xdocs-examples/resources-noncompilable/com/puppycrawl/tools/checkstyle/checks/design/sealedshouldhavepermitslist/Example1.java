/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SealedShouldHavePermitsList"/>
  </module>
</module>
*/


//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

// xdoc section -- start
public class Example1 {
  sealed class A {}   // violation
  final class B extends A {}
  final class C extends A {}
  final class D { } // this can extend A, so as any other class in the compilation unit
}

class CorrectedExample1 {
  sealed class A permits B, C  {} // ok, explicitly declared the permitted subclasses
  final class B extends A {}
  final class C extends A {}
  final class D { } // this can not extend A
}
// xdoc section -- end
