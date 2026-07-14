/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="atStartOfBody" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start

class Example3 {
  int x;
} // violation 'Blank line required before the closing brace of type definition.'

class Example3b {

  int x;

}


interface Empty3a {
}

interface Empty3b {

}

class InnerParent3 {



  class Inner3 {
    int y;
  }

}

class LocalParent3 {

  void method() {


    class Local3 {
      int z;
    }
  }

}


enum MyEnum3 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
