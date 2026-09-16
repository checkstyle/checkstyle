/*
UnusedTryResourceShouldBeUnnamed

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

import java.io.StringReader;

public class InputUnusedTryResourceShouldBeUnnamedHandlers {
    private final StringReader reader = new StringReader("field");

    void fieldInCatch() {
        // violation below 'Unused try resource 'reader' should be unnamed'
        try (StringReader reader = new StringReader("resource")) {
            System.out.println("body");
        } catch (RuntimeException ex) {
            reader.close();
        }
    }

    void fieldInFinally() {
        // violation below 'Unused try resource 'reader' should be unnamed'
        try (StringReader reader = new StringReader("resource")) {
            System.out.println("body");
        } finally {
            reader.close();
        }
    }

    void resourceInNestedCatch() {
        try (StringReader outer = new StringReader("outer")) {
            // violation below 'Unused try resource 'reader' should be unnamed'
            try (StringReader reader = new StringReader("inner")) {
                System.out.println("body");
            } catch (RuntimeException ex) {
                outer.close();
                reader.close();
            }
        }
    }

    void resourceInNestedFinally() {
        try (StringReader outer = new StringReader("outer")) {
            // violation below 'Unused try resource 'reader' should be unnamed'
            try (StringReader reader = new StringReader("inner")) {
                System.out.println("body");
            } finally {
                outer.close();
                reader.close();
            }
        }
    }

    void resourceDeclaredInHandler() {
        // violation below 'Unused try resource 'reader' should be unnamed'
        try (StringReader reader = new StringReader("resource")) {
            System.out.println("body");
        } finally {
            try (StringReader reader = new StringReader("handler")) {
                reader.close();
            }
        }
    }

    void resourceUsedInBody() {
        try (StringReader reader = new StringReader("resource")) {
            reader.close();
        } finally {
            reader.close();
        }
    }
}
