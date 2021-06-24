/*
EmptyCatchBlock
exceptionVariableName = expected|ignore|myException
commentFormat = This is expected


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;
import java.io.IOException;

public class InputEmptyCatchBlockDefault2
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
        } catch (Exception e) {} // violation

    }

    private void foo2() {
        try {
            throw new IOException();
        } catch (IOException | NullPointerException | ArithmeticException ignore) {
        }
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
        } catch (IOException | NullPointerException | ArithmeticException e) { // violation
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
        catch (Exception e) { // violation
            //This is expected
            /* This is expected */
            /**This is expected */
        }
        finally
        {
        }
    }

    public void testTryCatch3()
    {
        try {
            int y=0;
            int u=8;
            int e=u-y;
        }
        catch (IllegalArgumentException e) {
            System.identityHashCode(e); //some comment
            return;
        }
        catch (IllegalStateException ex) {
                System.identityHashCode(ex);
                return;
        }
    }

    public void testTryCatch4()
    {
        int y=0;
        int u=8;
        try {
            int e=u-y;
        }
        catch (IllegalArgumentException e) {
            System.identityHashCode(e);
            return;
        }
    }
    public void setFormats() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null)
                k = "ss";
            else {
                return;
            }
        }
    }
    public void setFormats1() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";
            } else {
                return;
            }
        }
    }
    public void setFormats2() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";
                return;
            }
        }
    }
    public void setFormats3() {
        try {
            int k = 4;
        } catch (Exception e) {
            Object k = null;
            if (k != null) {
                k = "ss";

            }
        }
    }

    private void some() {
        try {
            throw new IOException();
        } catch (IOException e) { /* // violation
             * ololo
             * blalba
             */
        }
    }
    private void some1() {
        try {
            throw new IOException();
        } catch (IOException e) { /* // violation
             * lalala
             * This is expected
             */
        }
    }
    private void some2() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /*
             * This is expected
             * lalala
             */
        }
    }
    private void some3() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation
            // some comment
            //This is expected
        }
    }
    private void some4() {
        try {
            throw new IOException();
        } catch (IOException e) {
            //This is expected
            // some comment
        }
    }
    private void some5() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation
            /* some comment */
            //This is expected
        }
    }

    private void emptyMultilineComment() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation
            /*
*/
        }
    }
}
