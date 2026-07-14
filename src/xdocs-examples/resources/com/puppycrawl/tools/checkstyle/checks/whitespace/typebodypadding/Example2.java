/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="allowEmpty" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below 'Blank line required after the opening brace of type definition.'
class Example2 {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

class Example2b {

  int x;

}

// violation below 'Blank line required after the opening brace of type definition.'
interface Empty2a {
} // violation 'Blank line required before the closing brace of type definition.'

interface Empty2b {

}

class InnerParent2 {



  class Inner2 {
    int y;
  }

}

class LocalParent2 {

  void method() {


    class Local2 {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum2 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
