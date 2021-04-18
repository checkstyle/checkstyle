package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.io.BufferedReader;
import java.io.IOException;

/*
 * Config:
 * option = alone_or_singleline
 */
class InputRightCurlyTryWithResourceAloneSingle {
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
        } catch (IOException e) // violation
        {
            ;
        }
        try (BufferedReader br1 = new BufferedReader(null);
                BufferedReader br2 = new BufferedReader(br1)) { ; } // ok
        catch (IOException e) { ; } // ok
        try (BufferedReader br1 = new BufferedReader(null);
                // violation on next line
                BufferedReader br2 = new BufferedReader(br1)) {} catch (IOException e) { ; }
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
