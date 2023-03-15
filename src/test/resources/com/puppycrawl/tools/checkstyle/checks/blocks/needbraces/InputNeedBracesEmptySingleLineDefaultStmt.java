/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesEmptySingleLineDefaultStmt {
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

@interface ExampleEmptySingleLineDefaultStmt {
    String priority() default "value"; // ok
}

interface IntefaceWithDefaultMethodEmptySingleLineDefaultStmt {
    default void doIt(){}
}
