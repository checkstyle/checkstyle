/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationUseStyle"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

@interface SomeArrays {
  String[] value() default {};
}

// xdoc section -- start
@SuppressWarnings("unchecked") // ok as it's in implied style
@Deprecated // ok as it matches closingParens default property
@SomeArrays({"unchecked","unused"}) // ok as it's in implied style
public class Example1
{

}

// violation below 'Annotation style must be 'COMPACT_NO_ARRAY''
@SuppressWarnings(value={"unchecked"})
@Deprecated() // violation 'Annotation cannot have closing parenthesis'
// violation below 'Annotation array values cannot contain trailing comma'
@SomeArrays(value={"unchecked","unused",})
class TestStyle1
{

}
// xdoc section -- end
