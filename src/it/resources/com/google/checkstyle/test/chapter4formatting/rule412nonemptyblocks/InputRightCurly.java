package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class InputRightCurly {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; }
        // violation above ''{' at column 19 should have line break after.'
    }
}
