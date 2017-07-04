package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesEmptyDefault {
    int value;
    private void main() {
        switch (value) {
            default:
        }
    }
    private void main1() {
        switch (value) {
            case 1:
        }
    }
}

@interface Example {
    String priority() default "value";
}
