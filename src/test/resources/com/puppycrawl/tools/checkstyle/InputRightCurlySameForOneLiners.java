////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for RightCurly with option SAME to omit oneliners
 * @see https://github.com/checkstyle/checkstyle/issues/1416
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class InputRightCurlySameForOneLiners {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; }
    }
}
