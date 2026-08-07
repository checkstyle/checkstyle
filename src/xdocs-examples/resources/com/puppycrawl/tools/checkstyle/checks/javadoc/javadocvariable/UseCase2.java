/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocVariable">
      <property name="accessModifiers" value="public,protected,package,private"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

// xdoc section - start
public class UseCase2 {
  // violation below 'Missing a Javadoc comment for 'variablePublic'.'
  public int variablePublic;
  // violation below 'Missing a Javadoc comment for 'variableProtected'.'
  protected int variableProtected;
  // violation below 'Missing a Javadoc comment for 'variablePackage'.'
  int variablePackage;
  // violation below 'Missing a Javadoc comment for 'variablePrivate'.'
  private int variablePrivate;

  public enum PublicEnum {
    CONSTANT // violation 'Missing a Javadoc comment for 'CONSTANT'.'
  }

  private enum PrivateEnum {
    CONSTANT // violation 'Missing a Javadoc comment for 'CONSTANT'.'
  }

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
// xdoc section - end
