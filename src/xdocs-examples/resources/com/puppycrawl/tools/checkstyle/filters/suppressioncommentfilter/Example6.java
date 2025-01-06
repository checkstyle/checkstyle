/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="CSOFF (\w+)"/>
      <property name="onCommentFormat" value="CSON (\w+)"/>
      <property name="idFormat" value="$1"/>
    </module>
    <module name="MemberName">
    <property name="id" value="MemberID"/>
    </module>
    <module name="ConstantName">
      <property name="id" value="ConstantID"/>
    </module>
    <module name="IllegalCatch">
      <property name="id" value="IllegalID"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;
// xdoc section -- start
class Example6
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CSOFF MemberID
  int VAR2; // suppressed violation
  //CSON: MemberID

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //CSOFF ConstantID
  public static final int var4 = 1; // suppressed violation
  //CSON ConstantID

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //CSOFF IllegalID

    try {}
    catch(Exception ex) {} // suppressed violation
    catch(Error err) {} // suppressed violation

    //CSON IllegalID
  }
}
// xdoc section -- end
