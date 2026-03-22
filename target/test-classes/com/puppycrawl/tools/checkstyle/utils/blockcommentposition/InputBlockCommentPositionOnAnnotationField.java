package com.puppycrawl.tools.checkstyle.utils.blockcommentposition;

public @interface InputBlockCommentPositionOnAnnotationField {
    /**
     * I'm a javadoc
     */
    String value() default "";

    /**
     * I'm a javadoc
     */
    abstract String value1() default "";

    /**
     * I'm a javadoc
     */
    @Deprecated
    String value2() default "";

    /**
     * I'm a javadoc
     */
    @Deprecated
    abstract String value3() default "";
}
