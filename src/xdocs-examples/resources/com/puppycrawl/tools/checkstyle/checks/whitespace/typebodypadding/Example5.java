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


interface Empty {
}

interface Emptyb {

}

class InnerParent {

  // violation 2 lines below 'Blank line required after the opening brace of type'
  //  'definition.'
  class Inner {
    int y;
  } // violation 'Blank line required before the closing brace of type definition.'

}

class LocalParent {

  void method() {


    class Local {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
interface Another {
  int x = 1;
} // violation 'Blank line required before the closing brace of type definition.'

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'

// violation below 'Blank line required after the opening brace of type definition.'
record MyRecord() {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

// violation below 'Blank line required after the opening brace of type definition.'
@interface MyAnnotation {
  int x();
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
