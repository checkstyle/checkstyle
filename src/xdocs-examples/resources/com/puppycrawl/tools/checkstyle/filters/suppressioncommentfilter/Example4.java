/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="checkFormat" value="MemberName"/>
    </module>
    <module name="MemberName"/>
    <module name="ConstantName"/>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;
// xdoc section -- start
class Example4 {

  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CHECKSTYLE:OFF
  int VAR2; // filtered violation 'must match pattern'
  //CHECKSTYLE:ON

  //stop constant check
  public static final int var3 = 1; // violation, 'must match pattern'
  //resume constant check

  //ILLEGAL OFF: Exception
  void method1() {
    try {
    }
    catch (Exception ex) { }  // violation, Catching 'Exception' is not allowed
    catch (Error err) { }     // violation, Catching 'Error' is not allowed
  }
  //ILLEGAL ON: Exception

  //CSOFF MemberID
  int VAR4;   // violation, Name 'VAR4' must match pattern '^[a-z][a-zA-Z0-9]*$'
  //CSON MemberID

  /*CHECKSTYLE:OFF*/
  public static final int varC = 1; // violation, 'must match pattern'
  /*CHECKSTYLE:ON*/
}
// xdoc section -- end
