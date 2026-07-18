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


interface Empty6a {
}

interface Empty6b {

}

class InnerParent6 {



  class Inner6 {
    int y;
  }

}

class LocalParent6 {

  void method() {
    // violation 2 lines below 'Blank line required after the opening brace of type'
    //  'definition.'
    class Local6 {
      int z;
    } // violation 'Blank line required before the closing brace of type definition.'
  }

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MyEnum6 {
  VALUE;
} // violation 'Blank line required before the closing brace of type definition.'
// xdoc section -- end
