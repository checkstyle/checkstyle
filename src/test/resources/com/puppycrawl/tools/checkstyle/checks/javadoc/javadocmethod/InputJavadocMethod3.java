/*
JavadocMethod
validateThrows = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;


interface Input {

    void setHeader(String header);
}

public class InputJavadocMethod3 implements Input {

    /**
     * Setter to define the required header specified inline.
     * Individual header lines must be separated by the string {@code "\n"}
     * (even on platforms with a different line separator).
     * For header lines containing {@code "\n\n"} checkstyle will forcefully
     * expect an empty line to exist. See examples below.
     * Regular expressions must not span multiple lines.
     *
     * @param header the header value to validate and set (in that order)
     */
    @Override
    public void setHeader(String header) {
        if (!StringUtils.isBlank(header)) {
            if (!CommonUtil.isPatternValid(header)) {
                throw new IllegalArgumentException("Unable to parse format: " + header);
            }
        }
    }
}

class CommonUtil {

    public static boolean isBlank(String header) {
        return false;
    }

    public static boolean isPatternValid(String header) {
        return false;
    }
}
