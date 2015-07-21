package com.puppycrawl.tools.checkstyle.annotation;


@interface SomeArrays32 {
    @Another32(value={"foo", "bar"}) //expanded
    DOGS[] pooches();
}


@interface Another32 {
    String[] value();
}
