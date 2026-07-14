/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="atEndOfBody" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below 'Blank line required after the opening brace of type definition.'
class Example4 {
  int x;
}

class Example4b {

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
}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum {
  VALUE;
}

// violation below 'Blank line required after the opening brace of type definition.'
record MyRecord() {
  int x;
}

// violation below 'Blank line required after the opening brace of type definition.'
@interface MyAnnotation {
  int x();
}
// xdoc section -- end
