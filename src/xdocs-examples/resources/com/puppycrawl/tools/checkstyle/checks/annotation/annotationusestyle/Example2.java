/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationUseStyle">
      <property name="elementStyle" value="expanded"/>
      <property name="closingParens" value="never"/>
      <property name="trailingArrayComma" value="never"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

// xdoc section -- start
@SuppressWarnings("unchecked") // violation 'Annotation style must be 'EXPANDED''
@Deprecated // ok as closingParens property set to never
// violation below 'Annotation style must be 'EXPANDED''
@SomeArrays({"unchecked","unused"})
public class Example2
{

}

@SuppressWarnings(value={"unchecked"}) // ok as elementStyle set to expanded
@Deprecated() // violation 'Annotation cannot have closing parenthesis'
// violation below 'Annotation array values cannot contain trailing comma'
@SomeArrays(value={"unchecked","unused",})
class TestStyle2 {

}
// xdoc section -- end
