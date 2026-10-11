/*
EmptyCatchBlock
exceptionVariableName = (default)^$
commentFormat = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;
import java.io.IOException;
public class InputEmptyCatchBlockDefault
{

    private void foo() {
        try {
            throw new RuntimeException();
        } catch (Exception expected) {
            //Expected
        }
    }

    private void foo1() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {} // violation 'Empty catch block'

    }

    private void foo2() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException ignore) {
        } // violation above 'Empty catch block'
    }

    private void foo3() { // comment
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { //This is expected
        }
    }

    private void foo4() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { /* This is expected*/
        }
    }

    private void foo5() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException e) { // singleline comment
        }
    }

    private void foo6() {
        try {
            throw new IOException();
        } catch (IOException expected) { // This is expected
            int k = 0;
        }
    }

    public void testTryCatch()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
            return;
        }
        catch (Exception e) {
            System.identityHashCode(e);
            return;
        }
        finally
        {
            return;
        }
    }

    public void testTryCatch2()
    {
        try {
        }
        catch (Exception e) {
            //This is expected
            /* This is expected */
            /**This is expected */
        }
        finally
        {
        }
    }
}
