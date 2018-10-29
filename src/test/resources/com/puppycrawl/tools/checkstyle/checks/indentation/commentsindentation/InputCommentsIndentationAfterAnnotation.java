package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

@AfterAnnotationCommentsAnnotation1
// Comment after annotation
public class InputCommentsIndentationAfterAnnotation {

    @AfterAnnotationCommentsAnnotation1
    // Comment after annotation
    public int input;

}

@AfterAnnotationCommentsAnnotation1
    // violation
class InputCommentsIndentationAfterAnnotation1 {

    @AfterAnnotationCommentsAnnotation1
        // violation
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
    // violation
@AfterAnnotationCommentsAnnotation2
class InputCommentsIndentationAfterAnnotation4 {

    @AfterAnnotationCommentsAnnotation1
        // violation
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

class InputCommentsIndentationAfterAnnotation5 {

    @AfterAnnotationCommentsAnnotation1
  // violation
    @AfterAnnotationCommentsAnnotation2
    public int input;

}

@interface AfterAnnotationCommentsAnnotation1 {}
@interface AfterAnnotationCommentsAnnotation2 {}
