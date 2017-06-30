package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

class InputSeparatorWrapArrayDeclarator {

    protected int[] arrayDeclarationWithGoodWrapping = new int[
            ] {1, 2}; // ok

    protected int[] arrayDeclarationWithBadWrapping = new int
            [] {1, 2}; // warn

}

