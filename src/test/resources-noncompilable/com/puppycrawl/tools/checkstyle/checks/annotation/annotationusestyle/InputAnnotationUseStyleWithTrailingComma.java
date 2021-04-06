//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;

/* Config:
 * closingParens = ignore
 * elementStyle = ignore
 * trailingArrayComma = ALWAYS
 */
public class InputAnnotationUseStyleWithTrailingComma
{
    @Test(value={(false) ? "" : "foo",}, more={(true) ? "" : "bar",}) // ok
    /**

    */
    enum P {
        L,
        Y;
    }

}

@interface Test {
    String[] value();
    String[] more() default {};
}
