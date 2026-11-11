/*
RegexpMultiline
format = System\\.(out)|(err)\\.print(ln)?\\(
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
matchAcrossLines = (default)false
reportGroup = (default)0
fileExtensions = (default)""

*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Test case for detecting simple semantic violations.
 * @author Lars Kühne
 **/
class InputRegexpMultilineSemantic
{
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true);
    }

    /* Boolean instantiation in a non-static initializer */
    {
        Boolean x = new Boolean(true);
        Boolean[] y = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    }

    /** fully qualified Boolean instantiation in a method. **/
    Boolean getBoolean()
    {
        return new Boolean(true);
    }

    void otherInstantiations()
    {
        // instantiation of classes in the same package
        Object o1 = new InputBraces();
        Object o2 = new InputModifier();
        // classes in another package with .* import
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        File f = new File("/tmp");
        // classes in another package with explicit import
        Dimension dim = new Dimension();
        Color col = new Color(0, 0, 0);
    }

    void exHandlerTest()
    {
        try {
            ; // do stuff and don't handle exceptions in some cases
        }
        catch (IllegalStateException emptyCatchIsAlwaysAnError) {
        }
        catch (NullPointerException ex) {
            // can never happen, but only commenting this is currently a problem
            // Possible future enhancement: allowEmptyCatch="commented"
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            ;
            // can never happen, semicolon makes checkstyle happy
            // this is a workaround for above problem
        }
        catch (NegativeArraySizeException ex) {
            {
            }
            // can never happen, empty compound statement is another workaround
        }
        catch (UnsupportedOperationException handledException) {
            System.out.println(handledException.getMessage());
        }
        // violation 2 lines above 'Line matches the illegal pattern'
        catch (SecurityException ex) { /* hello */ }
        catch (StringIndexOutOfBoundsException ex) {}
        catch (IllegalArgumentException ex) { }

        try {
        }
        finally {
        }
        try {
        // something
        }
        finally {
            // something
        }
        try {
            ; // something
        }
        finally {
            ; // statement
        }
    }

    private class InputBraces {
    }

    private class InputModifier {
    }
}
