package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

class InputSeparatorWrapForArrayDeclarator {

    protected int[] arrayDeclarationWithGoodWrapping = new int[
            ] {1, 2};

    protected int[] arrayDeclarationWithBadWrapping = new int
            [] {1, 2};

}

