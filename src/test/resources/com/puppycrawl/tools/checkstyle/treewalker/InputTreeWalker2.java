package com.puppycrawl.tools.checkstyle.treewalker;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;

import java.io.File;

public class InputTreeWalker2 {
    public static class ViolationTreeWalkerCheck
            extends AbstractFileSetCheck {

        private static final String MSG_KEY = "Top-level class InputTreeWalkerInner"
                + " has to reside in its own source file.";

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(9, 2, MSG_KEY);
        }
    }

    public static class ViolationTreeWalkerJavadocCheck
            extends AbstractFileSetCheck {

        private static final String MSG_KEY = "Redundant <p> tag.";

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(7, MSG_KEY);
        }
    }

    public static class ViolationTreeWalkerMultiCheckOrder
            extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(10, 9, "''if'' is not followed by whitespace.");
        }
    }

    public static class ViolationTreeWalkerMultiCheckOrder2
            extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(9, 32, "Name ''V2'' must match pattern ''^[a-z]([a-z0-9][a-zA-Z0-9]*)?$''.");
            log(13, 26, "Name ''b'' must match pattern ''^[a-z][a-z0-9][a-zA-Z0-9]*$''.");
        }
    }

    public static class ViolationSuppressionCommentFilterCheck
            extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(8, 25, "Name ''I'' must match pattern ''^[a-z][a-zA-Z0-9]*$''.");
            log(13, 25, "Name ''P'' must match pattern ''^[a-z][a-zA-Z0-9]*$''.");
        }
    }

    public static class ViolationTreeWalkerJavaFile
            extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
            log(9, 32, "Name ''k'' must match pattern ''^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$''.");
        }
    }
}
