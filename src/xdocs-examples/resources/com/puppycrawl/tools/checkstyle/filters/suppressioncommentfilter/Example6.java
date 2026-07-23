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
class Example6 {

  int VAR1; // violation, Name 'VAR1' must match pattern '^[a-z][a-zA-Z0-9]*$'

  //CHECKSTYLE:OFF
  int VAR2; // violation, Name 'VAR2' must match pattern '^[a-z][a-zA-Z0-9]*$'
  //CHECKSTYLE:ON

  //stop constant check
  public static final int var3 = 1; // violation 'must match pattern'
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
  int VAR4;   // filtered violation 'must match pattern'
  //CSON MemberID

  /*CHECKSTYLE:OFF*/
  public static final int varC = 1;  // violation 'must match pattern'
  /*CHECKSTYLE:ON*/
}
// xdoc section -- end
