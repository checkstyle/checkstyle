/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="tokens"
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


interface Empty7a {
}

interface Empty7b {

}

class InnerParent7 {



  class Inner7 {
    int y;
  }

}

class LocalParent7 {

  void method() {


    class Local7 {
      int z;
    }
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum7 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
