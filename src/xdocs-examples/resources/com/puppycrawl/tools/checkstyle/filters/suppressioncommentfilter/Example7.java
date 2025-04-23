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
  int VAR2; // filtered violation 'must match pattern'
  //cson MemberName

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //csoff ConstantName
  //csoff IllegalCatch

  public static final int var4 = 1; // filtered violation 'must match pattern'

  public void method1()
  {
    try {}
    catch(Exception ex) {}
    // filtered violation above 'Catching 'Exception' is not allowed'

    try {}
    catch(Exception ex) {}
    // filtered violation above 'Catching 'Exception' is not allowed'
    catch(Error err) {}
    // filtered violation above 'Catching 'Error' is not allowed'
  }

  //cson ConstantName
  //cson IllegalCatch

}
// xdoc section -- end
