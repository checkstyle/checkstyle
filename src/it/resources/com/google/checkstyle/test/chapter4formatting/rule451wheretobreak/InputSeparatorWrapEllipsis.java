package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

class InputSeparatorWrapEllipsis {

    public void testMethodWithGoodWrapping(String... // ok
            parameters) {

    }

    public void testMethodWithBadWrapping(String
            ...parameters) { // warn

    }

}

