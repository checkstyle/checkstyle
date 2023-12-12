/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckNullCaseLabel {
    public void switchWithNullLabelTest(SwitchInput i) {
        Integer counter=0;
        switch (i) {
            case FIRST: counter++;
            case SECOND: counter--;
            case null: counter=counter+2;
        }
    }
    enum SwitchInput {
        FIRST,
        SECOND
    }

    public void multipleCaseWithNullCaseConstants(){

        Integer value = 0;
        int counter = 0;
        switch (value) {
            case 0, 1 -> counter++;
            case 2 -> counter++;
            case null -> counter++;
            default -> counter++;
        }
    }

    public void nullCaseConstantsPrecedingDefaultCaseConstants(){

        String fruit = null;

        int counter = 0;

        switch (fruit) {
            case "apple", "orange" -> counter++;
            case null -> counter++;
            default -> counter++;
        }
    }

    public void switchExpressionWithPatterns(){

        Object value = 1;
        int counter = 0;
        Integer result = switch (value) {
            case String s -> counter++;
            case Integer i -> counter++;
            case null -> counter++;
            default -> counter++;
        };

    }


}

