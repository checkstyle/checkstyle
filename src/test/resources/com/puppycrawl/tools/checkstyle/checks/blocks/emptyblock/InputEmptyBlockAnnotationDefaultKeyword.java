/*
EmptyBlock
option = (default)statement
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public @interface InputEmptyBlockAnnotationDefaultKeyword {
    String name() default "";   // ok
}
