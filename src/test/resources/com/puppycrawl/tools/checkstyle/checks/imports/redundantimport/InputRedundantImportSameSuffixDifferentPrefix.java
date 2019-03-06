package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

// we need to test 2 imports with same suffix, but different prefix
import java.awt.image.*;
import sun.awt.image.BadDepthException;

public class InputRedundantImportSameSuffixDifferentPrefix {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
