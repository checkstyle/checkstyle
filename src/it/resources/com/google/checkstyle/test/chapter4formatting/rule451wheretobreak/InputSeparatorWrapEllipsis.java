package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

class InputSeparatorWrapEllipsis {

    public void testMethodWithGoodWrapping(String...
            parameters) {

    }

    public void testMethodWithBadWrapping(String
            ...parameters) { // warn

    }

}

