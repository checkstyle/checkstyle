/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="ILLEGAL OFF\: (\w+)"/>
      <property name="onCommentFormat" value="ILLEGAL ON\: (\w+)"/>
      <property name="checkFormat" value="IllegalCatch"/>
      <property name="messageFormat" value="^Catching '$1' is not allowed.$"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

// xdoc section -- start
class Example3
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //ILLEGAL OFF: Exception
  int VAR2; // violation, Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'
  //ILLEGAL ON: Exception

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //ILLEGAL OFF: Exception
  public static final int var4 = 1;
  // violation above,  Name 'must match pattern'
  //ILLEGAL ON: Exception

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //ILLEGAL OFF: Exception

    try {}
    catch(Exception ex) {}
    // filtered violation above 'Catching 'Exception' is not allowed'
    catch(Error err) {} // violation, Catching 'Error' is not allowed

    //ILLEGAL ON: Exception
  }
}
// xdoc section -- end
