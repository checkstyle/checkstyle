/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="CHECKSTYLE_OFF: ALMOST_ALL"/>
      <property name="onCommentFormat" value="CHECKSTYLE_ON: ALMOST_ALL"/>
      <property name="checkFormat" value="^((?!(ConstantName)).)*$"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

// xdoc section -- start
class Example5
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CHECKSTYLE_OFF: ALMOST_ALL
  int VAR2; // filtered violation 'must match pattern'
  //CHECKSTYLE_ON: ALMOST_ALL

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //CHECKSTYLE_OFF: ALMOST_ALL
  public static final int var4 = 1;
  // violation above, 'must match pattern'
  //CHECKSTYLE_ON: ALMOST_ALL

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //CHECKSTYLE_OFF: ALMOST_ALL

    try {}
    catch(Exception ex) {}
    // filtered violation above 'Catching 'Exception' is not allowed'
    catch(Error err) {}
    // filtered violation above 'Catching 'Error' is not allowed'

    //CHECKSTYLE_ON: ALMOST_ALL
  }
}
// xdoc section -- end
