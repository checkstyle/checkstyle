package com.puppycrawl.tools.checkstyle.checks.naming;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class InputCatchParameter {

    void foo1() {
        try {

        }
        catch (Exception ex) {

        }
    }

    void foo2() {
        try {

        }
        catch (NullPointerException | IllegalArgumentException ex) {
            // just to check how the ParentName's option 'skipCahcthParameter' deals with catching
            // multiple exception types and
        }
    }
}
