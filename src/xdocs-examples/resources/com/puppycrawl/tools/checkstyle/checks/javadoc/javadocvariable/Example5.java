/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section -- start
public class Example5 {
  public int variablePublic; // violation, 'Missing a Javadoc comment'
  protected int variableProtected; // violation, 'Missing a Javadoc comment'
  int variablePackage; // violation, 'Missing a Javadoc comment'
  private int variablePrivate; // violation, 'Missing a Javadoc comment'

  public void testMethodInnerClass() {

    // This check ignores local classes.
    class InnerClass {
      public int innerClassVariablePublic;
      protected int innerClassVariableProtected;
      int innerClassVariablePackage;
      private int innerClassVariablePrivate;
    }

    // This check ignores anonymous inner classes.
    Runnable runnable = new Runnable() {
      public int innerClassVariablePublic;
      protected int innerClassVariableProtected;
      int innerClassVariablePackage;
      private int innerClassVariablePrivate;
      public void run()
        {
          System.identityHashCode("running");
        }
    };
  }
}
// xdoc section -- end
