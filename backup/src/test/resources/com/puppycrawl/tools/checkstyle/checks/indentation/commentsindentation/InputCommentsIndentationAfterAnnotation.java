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

@AfterAnnotationCommentsAnnotation1
    // violation '.* incorrect .* level 4, expected is 0, .* same .* as line 22.'
class InputCommentsIndentationAfterAnnotation1 {

    @AfterAnnotationCommentsAnnotation1
        // violation '.* incorrect .* level 8, expected is 4, .* same .* as line 26.'
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

@AfterAnnotationCommentsAnnotation1
    // violation '.* incorrect .* level 4, expected is 0, .* same .* as line 44.'
@AfterAnnotationCommentsAnnotation2
class InputCommentsIndentationAfterAnnotation4 {

    @AfterAnnotationCommentsAnnotation1
        // violation '.* incorrect .* level 8, expected is 4, .* same .* as line 49.'
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

class InputCommentsIndentationAfterAnnotation5 {

    @AfterAnnotationCommentsAnnotation1
  // violation '.* incorrect .* level 2, expected is 4, .* same .* as line 58.'
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

@interface AfterAnnotationCommentsAnnotation1 {}
@interface AfterAnnotationCommentsAnnotation2 {}
