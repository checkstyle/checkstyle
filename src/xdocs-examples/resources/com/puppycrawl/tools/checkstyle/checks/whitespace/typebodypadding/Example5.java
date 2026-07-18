/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="skipInner" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below 'Blank line required after the opening brace of type definition.'
class Example5 {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

class Example5b {

  int x;

}


interface Empty5a {
}

interface Empty5b {

}

class InnerParent5 {

  // violation 2 lines below 'Blank line required after the opening brace of type'
  //  'definition.'
  class Inner5 {
    int y;
  } // violation 'Blank line required before the closing brace of type definition.'

}

class LocalParent5 {

  void method() {


    class Local5 {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum5 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
