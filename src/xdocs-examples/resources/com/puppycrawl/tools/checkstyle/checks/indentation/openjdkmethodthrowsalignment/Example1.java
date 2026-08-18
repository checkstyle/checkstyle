/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodThrowsAlignment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.indentation.openjdkmethodthrowsalignment;

// xdoc section - start
public class Example1 {
  // violation 2 lines below 'The throws clause should be on a new line'
  public void sameLine(int first,
                       int second) throws Exception {
  }

  public void declarationRelative(int first,
                                  int second)
          throws Exception {
  }

  public void alignedWithPrevious(int first,
      int second)
      // violation below 'The throws clause should be indented eight spaces'
      throws Exception {
  }
}
// xdoc section - end
