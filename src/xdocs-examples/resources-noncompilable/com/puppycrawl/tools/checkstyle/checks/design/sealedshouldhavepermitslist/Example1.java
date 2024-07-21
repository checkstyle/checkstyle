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

  // imagine hundreds of lines of code...

  sealed class A {} // violation
  final class B extends A {}
  final class C extends A {}
  final class D { } // this can extend A, so as any other class in the same file
}

// all subclasses are declared at the enclosing class level, for easy reading
class CorrectedExample1 {
  sealed class A permits B, C {} // ok, explicitly declared permitted subclasses
  final class B extends A {}
  final class C extends A {}
  final class D { } // this can not extend A
}
// xdoc section -- end
