/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="skipLocal" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below 'Blank line required after the opening brace of type definition.'
class Example6 {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

class Example6b {

  int x;

}


interface Empty {
}

interface Emptyb {

}

class InnerParent {



  class Inner {
    int y;
  }

}

class LocalParent {

  void method() {
    // violation 2 lines below 'Blank line required after the opening brace of type'
    //  'definition.'
    class Local {
      int z;
    } // violation 'Blank line required before the closing brace of type definition.'
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
