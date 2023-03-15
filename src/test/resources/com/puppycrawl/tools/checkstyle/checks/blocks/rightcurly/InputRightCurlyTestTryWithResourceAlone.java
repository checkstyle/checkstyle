/*
RightCurly
option = ALONE
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

class InputRightCurlyTestTryWithResourceAlone {
    void test() throws IOException {
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) {
            ;
        } // ok
        catch (IOException e) {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1))
        {
            ;
        } catch (IOException e) // violation ''}' at column 9 should be alone on a line'
        {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                // violation below ''}' at column 67 should be alone on a line'
                BufferedReader br2 = new BufferedReader(br1)) { ; }
        catch (IOException e) { ; } // violation ''}' at column 35 should be alone on a line'
        try (BufferedReader br1 = new BufferedReader(null);

                BufferedReader br2 = new BufferedReader(br1)) {} catch (IOException e) { ; }
        try (BufferedReader br1 = new BufferedReader(null); // 2 violations above
                BufferedReader br2 = new BufferedReader(br1)) {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                // violation below ''}' at column 67 should be alone on a line'
                BufferedReader br2 = new BufferedReader(br1)) { ; }
        try (BufferedReader br1 = new BufferedReader(null)) {
            ; } // violation ''}' at column 15 should be alone on a line'
        try (BufferedReader br1 = new BufferedReader(null)) {
            } int i; // violation ''}' at column 13 should be alone on a line'
    }
}
