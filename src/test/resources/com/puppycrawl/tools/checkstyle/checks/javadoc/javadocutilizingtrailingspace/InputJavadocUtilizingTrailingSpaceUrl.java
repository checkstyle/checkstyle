/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for URL handling in Javadocs.
 */
public class InputJavadocUtilizingTrailingSpaceUrl {

    /**
     * http://example.com/this/is/a/very/long/url/that/exceeds/limit
     */
    public void httpUrlAtStartAllowed() { }

    /**
     * https://example.com/this/is/a/very/long/url/that/exceeds/limit
     */
    public void httpsUrlAtStartAllowed() { }

    /**
     * ftp://ftp.example.com/very/long/path/to/file/that/exceeds/limit
     */
    public void ftpUrlAtStartAllowed() { }

    // violation 2 below 'Line is longer than'
    /**
     * See the docs at http://example.com/this/is/a/very/long/url/exceeds to learn more.
     */
    public void httpUrlInMiddleViolation() { }

    // violation 2 below 'Line is longer than'
    /**
     * Documentation: https://example.com/this/is/a/very/long/url/that/exceeds/the/limit/set
     */
    public void httpsUrlInMiddleViolation() { }

    /**
     * Check the resources at
     * http://example.com/this/is/a/very/long/url/that/exceeds/limit
     */
    public void urlOnNewLineAllowed() { }

    /**
     * Visit http://example.com for details.
     */
    public void shortUrlInMiddle() { }

    /**
     * More info: https://example.com/docs
     */
    public void shortHttpsUrl() { }

    /**
     * @see http://example.com/api/documentation/for/this/method
     */
    public void seeWithUrl() { }

    // violation 3 below 'Line is longer than'
    /**
     * Official site: http://example.com
     * API docs: https://api.example.com/v1/documentation 
     */
    public void multipleUrls() { }
}
