/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="csoff (\w+)"/>
      <property name="onCommentFormat" value="cson (\w+)"/>
      <property name="checkFormat" value="$1"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;
// xdoc section -- start
class Example7
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //csoff MemberName
  int VAR2; // suppressed violation
  //cson MemberName

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //csoff ConstantName
  //csoff IllegalCatch

  public static final int var4 = 1; // suppressed violation

  public void method1()
  {
    try {}
    catch(Exception ex) {} // suppressed violation

    try {}
    catch(Exception ex) {} // suppressed violation
    catch(Error err) {} // suppressed violation
  }

  //cson ConstantName
  //cson IllegalCatch

}
// xdoc section -- end
