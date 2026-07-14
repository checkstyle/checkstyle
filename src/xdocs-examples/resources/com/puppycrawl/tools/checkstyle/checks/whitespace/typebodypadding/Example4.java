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


interface Empty4a {
}

interface Empty4b {

}

class InnerParent4 {



  class Inner4 {
    int y;
  }

}

class LocalParent4 {

  void method() {


    class Local4 {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum4 {
  VALUE;
}
// xdoc section -- end
