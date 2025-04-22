/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="checkC" value="true"/>
      <property name="checkCPP" value="false"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;
// xdoc section -- start
class Example8
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CHECKSTYLE:OFF
  int VAR2; // violation, Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'
  //CHECKSTYLE:ON

  public static final int var3 = 1;
  // violation above, Name 'must match pattern'

  /*CHECKSTYLE:OFF*/
  public static final int var4 = 1; // filtered violation 'must match pattern'
  /*CHECKSTYLE:ON*/

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //CHECKSTYLE:OFF

    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed
    catch(Error err) {} // violation, Catching 'Error' is not allowed

    //CHECKSTYLE:ON
  }
}
// xdoc section -- end
