/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationUseStyle">
      <property name="elementStyle" value="ignore"/>
      <property name="closingParens" value="ignore"/>
      <property name="trailingArrayComma" value="always"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

// xdoc section -- start
@Deprecated // ok as 'closingParens' is set to 'ignore
// violation below 'Annotation array values must contain trailing comma'
@SomeArraysUnique({"unchecked","unused"})
public class ExampleAnnotationStyle4
{

}

// violation below 'Annotation array values must contain trailing comma'
@SuppressWarnings(value={"unchecked"}) //
@Deprecated() // ok as 'closingParens' is set to 'ignore
// ok below as it has a trailing array comma
@SomeArraysUnique(value={"unchecked","unused",})
class TestStyle4 {

}
// xdoc section -- end
