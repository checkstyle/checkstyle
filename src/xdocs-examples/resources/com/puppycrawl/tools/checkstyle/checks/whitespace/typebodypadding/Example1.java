/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below 'Blank line required after the opening brace of type definition.'
class Example1 {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

class Example1b {

  int x;

}


interface Empty1a {
}

interface Empty1b {

}

class InnerParent1 {



  class Inner1 {
    int y;
  }

}

class LocalParent1 {

  void method() {


    class Local1 {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum1 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
