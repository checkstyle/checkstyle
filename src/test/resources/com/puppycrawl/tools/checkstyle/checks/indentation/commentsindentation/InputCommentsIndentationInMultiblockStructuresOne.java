/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInMultiblockStructuresOne {
    void foo() {
        if (true) {
            assert true;
        // comment for else
        } else {}

        if (true) {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 18.'
        }
        else {}

        if (true) {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.*same .* as line 24, 26.'
        } else {}

        if (true) {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 29, 31.'
        } else {}

        try {
            assert true;
        // comment for catch
        } catch (Exception ex) {}

        try {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 39.'
        }
        catch (Exception ex) {}

        try {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 45, 47.'
        } catch (Exception ex) {}

        try {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.* same.* as line 50, 52.'
        } catch (Exception ex) {}

        try {
            assert true;
        // comment for finally
        } finally {}

        try {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 60.'
        }
        finally {}

        try {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 66, 68.'
        } finally {}

        try {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.* same.* as line 71, 73.'
        } finally {}

        try {} catch (Exception ex) {
            assert true;
        // comment for finally
        } finally {}

        try {} catch (Exception ex) {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 81.'
        }
        finally {}

        try {} catch (Exception ex) {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 87, 89.'
        } finally {}

        try {} catch (Exception ex) {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.*same.* as line 92, 94.'
        } finally {}

        try {} catch (ClassCastException ex) {
            assert true;
        // comment for catch
        } catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 102.'
        }
        catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
// violation '.* incorrect .* level 0, expected is 12, 8, .* same .* as line 108, 110.'
        } catch (Exception ex) {}

        try {} catch (ClassCastException ex) {
            assert true;
                // violation '.* incorrect .* level 16, expected is 12, 8,.*same.*as line 113, 115.'
        } catch (Exception ex) {}

    }
}
