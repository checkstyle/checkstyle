////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for detecting simple semantic errors.
 * @author Lars Kühne
 **/
class InputSemantic
{
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true);
    }

    /* Boolean instantiation in a non-static initializer */
    {
        Boolean x = new Boolean(true);
    }

    /** fully qualified Boolean instantiation in a method. **/
    Boolean getBoolean()
    {
        return new java.lang.Boolean(true);
    }

    void exHandlerTest()
    {
        try {
            // do stuff and don't handle exceptions in some cases
        }
        catch (IllegalStateException emptyCatchIsAlwaysAnError) {
        }
        catch (NullPointerException ex) {
            // can never happen, but only commentig this is currently an error
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
        catch (SecurityException ex) { /* hello */ }
        catch (StringIndexOutOfBoundsException ex) {}
        catch (IllegalArgumentException ex) { }
    }
}
