package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;


@interface SomeArrays32 {
    @Another32(value={"foo", "bar"}) //expanded
    DOGS[] pooches();
}


@interface Another32 {
    String[] value();
}
