package com.puppycrawl.tools.checkstyle.checks.indentation;

public class InputCommentsIndentationInMultiblockStructures {
    void foo() {
        if (true) {
            assert true;
        // comment for else
        } else {}

        if (true) {
            assert true;
        // violation
        }
        else {}

        if (true) {
            assert true;
                // violation
        } else {}

        if (true) {
            assert true;
// violation
        } else {}

        try {
            assert true;
        // comment for catch
        } catch (Exception ex) {}

        try {
            assert true;
        // violation
        }
        catch (Exception ex) {}

        try {
            assert true;
// violation
        } catch (Exception ex) {}

        try {
            assert true;
                // violation
        } catch (Exception ex) {}

        try {
            assert true;
        // comment for finally
        } finally {}

        try {
            assert true;
        // violation
        }
        finally {}

        try {
            assert true;
// violation
        } finally {}

        try {
            assert true;
                // violation
        } finally {}

        try {} catch (Exception ex) {
            assert true;
        // comment for finally
        } finally {}

        try {} catch (Exception ex) {
            assert true;
        // violation
        }
        finally {}

        try {} catch (Exception ex) {
            assert true;
// violation
        } finally {}

        try {} catch (Exception ex) {
            assert true;
                // violation
        } finally {}

        try {} catch (ClassCastException ex) {
            assert true;
        // comment for catch
        } catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
        // violation
        }
        catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
// violation
        } catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
                // violation
        } catch (Exception ex) {}

        do {
            assert true;
        // comment for while
        } while (false);

        do {
            assert true;
        // violation
        }
        while (false);

        do {
            assert true;
                // violation
        } while (false);

        do {
            assert true;
// violation
        } while (false);
    }
}
