/*
EmptyCatchBlock
exceptionVariableName = expected|ignore|myException
commentFormat = This is expected


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;

public class InputEmptyCatchBlockNonEmptyCatch2 {
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
}
