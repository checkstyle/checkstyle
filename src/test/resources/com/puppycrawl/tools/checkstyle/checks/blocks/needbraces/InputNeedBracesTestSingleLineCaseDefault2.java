package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

/*
 * Config:
 * allowSingleLineStatement = true
 * tokens = { LITERAL_CASE, LITERAL_DEFAULT }
 */
public class InputNeedBracesTestSingleLineCaseDefault2 {
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

interface IntefaceWithDefaultMethod {
    default void doIt(){}
}
