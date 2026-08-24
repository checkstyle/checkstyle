/*
InappropriateJavadocBlockTagsOnField
violateExecutionOnNonTightHtml = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonfield;

class InputInappropriateJavadocBlockTagsOnFieldTightHtml {

    // violation 2 lines below 'Unclosed HTML tag found: p'
    /**
     * <p>
     */
    public static final int CONST = 12;

}
