package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class RightCurlyInputSame {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; }
    }
}
