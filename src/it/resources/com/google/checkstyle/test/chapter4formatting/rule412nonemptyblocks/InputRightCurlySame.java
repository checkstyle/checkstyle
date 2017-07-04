package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class InputRightCurlySame {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; }
    }
}
