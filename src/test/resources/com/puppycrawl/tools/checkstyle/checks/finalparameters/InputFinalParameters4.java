/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test case for detecting missing final parameters.
 * @author Lars Kühne
 **/
class InputFinalParameters4
{
    /** some catch blocks */
    void method1()
    {
        try {
            String.CASE_INSENSITIVE_ORDER.equals("");
        }
        catch (NullPointerException npe) { // violation, 'npe' should be fina;
            npe.getMessage();
        }
        catch (@MyAnnotation33 final ClassCastException e) {
            e.getMessage();
        }
        catch (RuntimeException e) { // violation, 'e' should be final
            e.getMessage();
        }
        catch (@MyAnnotation33 NoClassDefFoundError e) { // violation, 'e' should be final
            e.getMessage();
        }
    }

}
