package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class InputRightCurly {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } // violation ''}' at column 9 should be on the same line as the next part'
        finally {
            after = true;
        }
    }
}
