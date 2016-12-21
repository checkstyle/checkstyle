package com.puppycrawl.tools.checkstyle.checks.blocks;

public class InputNeedBracesEmptyDefault {
    private static void main() {
        switch (5) {
            default:
        }
    }
}
