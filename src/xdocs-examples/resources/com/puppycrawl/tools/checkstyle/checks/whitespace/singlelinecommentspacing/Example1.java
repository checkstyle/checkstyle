/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SingleLineCommentSpacing"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlelinecommentspacing;

// xdoc section -- start
class Example1 {
  void method() {
    int ok = 1; // ok

    int both = 1;//bad // 2 violations
    int afterOnly = 2; //bad // violation 'should be followed by whitespace before comment text.'
    int beforeOnly = 3;// bad // violation 'should be preceded by whitespace when trailing code.'
    int bareComment = 4;//

    //comment-only line // violation 'should be followed by whitespace before comment text.'
    ///
    //////
    // comment-only line with proper spacing
  }
}
// xdoc section -- end
