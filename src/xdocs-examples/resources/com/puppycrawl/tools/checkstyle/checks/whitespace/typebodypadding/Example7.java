/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property
        name="tokens"
        value="INTERFACE_DEF, RECORD_DEF, ENUM_DEF, ANNOTATION_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start

class Example7 {
  int x;
}

class Example7b {

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
  static int x;
} // violation 'Blank line required before the closing brace of type definition.'

// violation below 'Blank line required after the opening brace of type definition.'
@interface MyAnnotation {
  int x();
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
