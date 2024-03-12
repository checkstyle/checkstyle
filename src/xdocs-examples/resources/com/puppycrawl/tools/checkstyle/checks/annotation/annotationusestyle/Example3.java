/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationUseStyle">
      <property name="elementStyle" value="compact"/>
      <property name="closingParens" value="always"/>
      <property name="trailingArrayComma" value="ignore"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

// xdoc section -- start
@SuppressWarnings("unchecked") // ok as element style set to compact
@Deprecated // violation 'Annotation must have closing parenthesis'
@SomeArrays({"unchecked","unused"}) // ok as it's in compact style
public class Example3
{

}

// violation below 'Annotation style must be 'COMPACT''
@SuppressWarnings(value={"unchecked"})
@Deprecated() // ok as closingParens set to always
// violation below 'Annotation style must be 'COMPACT''
@SomeArrays(value={"unchecked","unused",})
class TestStyle3 {

}
// xdoc section -- end
