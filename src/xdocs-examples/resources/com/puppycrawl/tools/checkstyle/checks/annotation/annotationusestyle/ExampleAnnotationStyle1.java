/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationUseStyle"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

// xdoc section -- start

@interface SomeArraysUnique {
  String[] value() default {};
}

@SuppressWarnings("unchecked") // ok as it's in implied style
@Deprecated // // ok as it matches closingParens default property
@SomeArraysUnique({"unchecked","unused"}) // ok as it's in implied style
public class ExampleAnnotationStyle1
{

}

@Deprecated() // violation 'Annotation cannot have closing parenthesis'
// violation below 'Annotation array values cannot contain trailing comma'
@SomeArraysUnique(value={"unchecked","unused",})
class TestStyle1
{

}
// xdoc section -- end
