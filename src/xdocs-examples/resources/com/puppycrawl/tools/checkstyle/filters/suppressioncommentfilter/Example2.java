/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="stop constant check"/>
      <property name="onCommentFormat" value="resume constant check"/>
      <property name="checkFormat" value="ConstantNameCheck"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

// xdoc section -- start
class Example2
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //stop constant check
  int VAR2; // violation, Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'
  //resume constant check

  public static final int var3 = 1;
  // violation above, Name 'must match pattern'

  //stop constant check
  public static final int var4 = 1; // filtered violation 'must match pattern'
  //resume constant check

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //stop constant check

    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed
    catch(Error err) {} // violation, Catching 'Error' is not allowed

    //resume constant check
  }
}
// xdoc section -- end
