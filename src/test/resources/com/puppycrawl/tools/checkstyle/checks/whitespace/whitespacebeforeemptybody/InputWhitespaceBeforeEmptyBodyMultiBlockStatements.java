/*
WhitespaceBeforeEmptyBody
tokens = LITERAL_IF, LITERAL_ELSE, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

import java.io.FileInputStream;
import java.io.InputStream;

public class InputWhitespaceBeforeEmptyBodyMultiBlockStatements {

    void method() {
        boolean a = true;
        boolean b = false;

        if (a && b){           // violation ''{' is not preceded with whitespace'
        } else if (a) {
            // comment
        }
        else{                  // violation ''{' is not preceded with whitespace'
        }

        if (b) {
            a = !b;
        } else if (a){         // violation ''{' is not preceded with whitespace'
        } else{}               // violation ''{' is not preceded with whitespace'

        try {
            a = b && a;
        }
        catch (Exception e){   // violation ''{' is not preceded with whitespace'
        }
        finally{
            a = true;
        }

        try{                   // violation ''{' is not preceded with whitespace'
        }
        catch (Exception e){   // violation ''{' is not preceded with whitespace'
            // comment
        }
        finally{a = b;}

        // violation 2 lines below''{' is not preceded with whitespace'
        try {}
        catch (NullPointerException e){
            // comment
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("bad code");
        }
        finally{}
        // violation above ''{' is not preceded with whitespace'

        try (InputStream in = new FileInputStream("")) { }
        catch (Exception e) {}
    }
}
