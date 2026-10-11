package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

// violation first line 'Header mismatch'

public class InputBracesNeedBracesValid {
    String obj = new String();
    String value = new String();
    int counter = 1;
    int count = 0;
    int num = 12;
    String o = "O";

    public boolean test() {
        if (obj.equals(num)) {

        }

        if (true) {
            count = 2;
        } else {
            return false;
        }

        for (int i = 0; i < 5; i++) {
            ++count;
        }

        do {
            ++count;
        } while (false);

        for (int j = 0; j < 10; j++) {

        }

        for (int i = 0; i < 10; value.charAt(12)) {

        }

        while (counter < 10) {
            ++count;
        }

        return true;
    }
}
