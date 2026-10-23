/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

@AfterAnnotationCommentsAnnotation1
// Comment after annotation
public class InputCommentsIndentationAfterAnnotation {

    @AfterAnnotationCommentsAnnotation1
    // Comment after annotation
    public int input;

}

// violation 2 lines below '.* incorrect .* level 4, expected is 0, .* same .* as line 23.'
@AfterAnnotationCommentsAnnotation1
    // Comment after annotation
class InputCommentsIndentationAfterAnnotation1 {

    // violation 2 lines below '.* incorrect .* level 8, expected is 4, .* same .* as line 28.'
    @AfterAnnotationCommentsAnnotation1
        // Comment after annotation
    public int input;

}

@AfterAnnotationCommentsAnnotation1
// Comment after annotation
@AfterAnnotationCommentsAnnotation2
class InputCommentsIndentationAfterAnnotation3 {

    @AfterAnnotationCommentsAnnotation1
    // Comment after annotation
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

// violation 2 lines below '.* incorrect .* level 4, expected is 0, .* same .* as line 47.'
@AfterAnnotationCommentsAnnotation1
    // Comment after annotation
@AfterAnnotationCommentsAnnotation2
class InputCommentsIndentationAfterAnnotation4 {

    // violation 2 lines below '.* incorrect .* level 8, expected is 4, .* same .* as line 53.'
    @AfterAnnotationCommentsAnnotation1
        // Comment after annotation
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

class InputCommentsIndentationAfterAnnotation5 {

    // violation 2 lines below '.* incorrect .* level 2, expected is 4, .* same .* as line 63.'
    @AfterAnnotationCommentsAnnotation1
  // Comment after annotation
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

@interface AfterAnnotationCommentsAnnotation1 {}
@interface AfterAnnotationCommentsAnnotation2 {}
