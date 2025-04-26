/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="CSOFF\: ([\w\|]+)"/>
      <property name="onCommentFormat" value="CSON\: ([\w\|]+)"/>
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
class Example4
{
  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CSOFF: MemberName
  int VAR2; // filtered violation 'must match pattern'
  //CSON: MemberName

  public static final int var3 = 1;
  // violation above, 'must match pattern'

  //CSOFF: ConstantName
  public static final int var4 = 1; // filtered violation 'must match pattern'
  //CSON: ConstantName

  public void method1()
  {
    try {}
    catch(Exception ex) {} // violation, Catching 'Exception' is not allowed

    //CSOFF: IllegalCatch

    try {}
    catch(Exception ex) {}
    // filtered violation above 'Catching 'Exception' is not allowed'
    catch(Error err) {}
    // filtered violation above 'Catching 'Error' is not allowed'

    //CSON: IllegalCatch
  }
}
// xdoc section -- end
