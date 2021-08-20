/*
RightCurly
option = (default)SAME
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

class InputRightCurlyTestTryWithResourceSame {
    void test() throws IOException {
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) {
            ;
        } // violation
        catch (IOException e) {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1))
        {
            ;
        } catch (IOException e) // ok
        {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) { ; } // violation
        catch (IOException e) { ; } // ok
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) {} catch (IOException e) { ; } // ok
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) { ; } // ok
        try (BufferedReader br1 = new BufferedReader(null)) {
            ; } // violation
        try (BufferedReader br1 = new BufferedReader(null)) {
            } int i; // violation
    }
}
