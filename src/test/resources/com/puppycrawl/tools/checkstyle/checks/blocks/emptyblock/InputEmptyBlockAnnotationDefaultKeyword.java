package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

/* Config:
 * option = "statement"
 * tokens = "LITERAL_DEFAULT"
 */
public @interface InputEmptyBlockAnnotationDefaultKeyword {
    String name() default "";   // ok
}
