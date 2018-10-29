package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;


@interface InputAnnotationUseStyle {
    @Another32(value={"foo", "bar"}) //expanded
    DOGS[] pooches();
}


@interface Another32 {
    String[] value();
}
