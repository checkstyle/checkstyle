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
  public void valid(int a) throws Exception {
  }

  public void sameLine(int first,
    int second) throws Exception {
    // violation above """The 'throws' clause should be on a new line when
    // the method declaration is wrapped."""
  }

  public void incorrectIndentation(int first,
                                  int second)
      throws Exception {
    // violation above """The 'throws' clause should be indented 8 spaces relative
    // to the method declaration or the previous line and should not align with the
    // previous line."""
  }

  public void declarationRelative(int first,
                                  int second)
          throws Exception {
  }

  public void previousLineRelative(int first,
                          int second)
                                  throws Exception {
  }

  public void alignedWithPreviousLine(int first,
        int second)
        throws Exception {
    // violation above """The 'throws' clause should be indented 8 spaces relative
    // to the method declaration or the previous line and should not align with the
    // previous line."""
  }
}
// xdoc section - end
