/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
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

    // ok, a line that has a URL is not reported as too long
    /**
     * See the docs at http://example.com/this/is/a/very/long/url/exceeds to learn more.
     */
    public void httpUrlInMiddleAllowed() { }

    /**
     * See the docs at http://example.com/this/is/a/very/long/url/exceeds to
     * learn more.
     */
    public void wrappedHttpUrlInMiddle() { }

    // ok, a line that has a URL is not reported as too long
    /**
     * Documentation: https://example.com/this/is/a/very/long/url/that/exceeds/the/limit/set
     */
    public void httpsUrlInMiddleAllowed() { }

    /**
     * Documentation:
     * https://example.com/this/is/a/very/long/url/that/exceeds/the/limit/set
     */
    public void wrappedHttpsUrlInMiddle() { }

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
     * See http://example.com/api/documentation/for/this/method
     */
    public void seeWithUrl() { }

    // violation 2 lines below 'Line under-utilized (40/80). Words from below could be moved up'
    /**
     * Official site: http://example.com
     * API docs: https://api.example.com/v1/documentation
     */
    public void multipleUrls() { }

    /**
     * Official site: http://example.com API docs:
     * https://api.example.com/v1/documentation
     */
    public void correctedMultipleUrls() { }

    /**
     * https://en.wikipedia.org/wiki/Overview_of_RESTful_API_Description_Languages#Hypertext-driven_API.
     */
    public void longUrlWithPeriodAllowed() { }

    // ok, a line that has a URL is not reported as too long
    /**
     * https://example.com/this/is/a/very/long/url/that/exceeds/limit for more details.
     */
    public void longUrlAtStartWithTextAllowed() { }

    /**
     * https://example.com/this/is/a/very/long/url/that/exceeds/limit for more
     * details.
     */
    public void wrappedLongUrlAtStartWithText() { }

    // ok, a line that has a URL is not reported as too long
    /**
     * https://example.com/this/is/a/very/long/url/that/exceeds/limit {@link Object}
     */
    public void longUrlAtStartWithTagAllowed() { }
}
