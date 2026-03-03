/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

import java.util.function.IntFunction;

public class InputArrayBracketNoWhitespaceMemberAndOperators {

    void testMember() {
        final String[] stringArray = {"a"};
        String t1 = stringArray[0].toString();
        String t2 = stringArray[0]
            .toString();
        String t3 = stringArray[0] .toString(); // violation ''\]' is followed by whitespace.'
    }

    void testPostfixOperators() {
        int[] arr = {1, 2};
        int x = arr[0], u = arr[1];
        int y = arr[0] , v = arr[1]; // violation ''\]' is followed by whitespace.'
        arr[2] ++; // violation ''\]' is followed by whitespace.'
        arr[2]++;
        arr[2] --; // violation ''\]' is followed by whitespace.'
        arr[2]--;
        arr[0]+=1; // violation ''\]' is not followed by whitespace.'
        int a = arr[0] + arr[1];
        int b = arr[0]+ arr[1]; // violation ''\]' is not followed by whitespace.'
        int c = arr[0] - arr[1];
        int d = arr[0]- arr[1]; // violation ''\]' is not followed by whitespace.'
    }

    void testColonWhitespace() {
        IntFunction<int[]> f1 = int[]::new;
        IntFunction<int[] > f2 = int[]::new; // violation ''\]' is followed by whitespace.'
        IntFunction<int[]> f3 = int[] ::new; // violation ''\]' is followed by whitespace.'
    }

    void testAngleBracket() {
        int[] arr = new int[5];
        boolean result1 = arr[0]>=0; // violation ''\]' is not followed by whitespace.'
        boolean result2 = arr[0] >= 0;
        boolean result3 = arr[0]<=0; // violation ''\]' is not followed by whitespace.'
        boolean result4 = arr[0] <= 0;
        int result5 = arr[0]>> 2; // violation ''\]' is not followed by whitespace.'
        int result6 = arr[0] >> 2;
        int result7 = arr[0]<< 2; // violation ''\]' is not followed by whitespace.'
        int result8 = arr[0] << 2;
        boolean result9 = arr[0]< 2; // violation ''\]' is not followed by whitespace'
    }
}
