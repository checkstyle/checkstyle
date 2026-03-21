/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for mixed content with various Javadoc elements combined.
 */
public class InputJavadocUtilizingTrailingSpaceMixed {

    // violation 2 lines below 'Line under-utilized (74/80). Words from below could be moved up'
    /**
     * This method processes data from {@link java.util.List} collections.
     * For more details see http://example.com/docs for full documentation.
     *
     * <p>Usage example:
     * <pre>
     * Mixed mixed = new Mixed();
     * mixed.processData(list);
     * </pre>
     *
     * @param data the input {@link java.util.List} to process
     * @return the processed result as an {@code int} value
     * @throws IllegalArgumentException if data is {@code null}
     */
    public int processData(java.util.List<?> data) {
        if (data == null) {
            throw new IllegalArgumentException("null");
        }
        return data.size();
    }

    /**
     * This method processes data from {@link java.util.List} collections. For
     * more details see http://example.com/docs for full documentation.
     *
     * <p>Usage example:
     * <pre>
     * Mixed mixed = new Mixed();
     * mixed.processData(list);
     * </pre>
     *
     * @param data the input {@link java.util.List} to process
     * @return the processed result as an {@code int} value
     * @throws IllegalArgumentException if data is {@code null}
     */
    public int correctedProcessData(java.util.List<?> data) {
        if (data == null) {
            throw new IllegalArgumentException("null");
        }
        return data.size();
    }

    // violation 2 lines below 'Line under-utilized (45/80). Words from below could be moved up'
    /**
     * Retrieves data from the specified URL.
     * See http://example.com/api
     * for the API documentation.
     *
     * @param url the target URL
     * @return response data
     */
    public String fetchFromUrl(String url) {
        return "";
    }

    /**
     * Retrieves data from the specified URL. See http://example.com/api for the
     * API documentation.
     *
     * @param url the target URL
     * @return response data
     */
    public String correctedFetchFromUrl(String url) {
        return "";
    }

    /**
     * <p>This method demonstrates:
     * <ul>
     * <li>Using {@link Object#toString()} for conversion.</li>
     * <li>Handling {@code null} values gracefully.</li>
     * <li>Returning meaningful error messages to callers.</li>
     * </ul>
     *
     * @param obj the object to convert using the toString method
     * @return the string representation or {@code "null"} if input was null
     */
    public String convertToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    // violation 2 lines below 'Line under-utilized (70/80). Words from below could be moved up'
    /**
     * This line has exactly seventy characters including thee leading
     * nineChars more text here
     */
    public void boundaryExactlyAtLimit() { }

    /**
     * This line has exactly seventy characters including thee leading nineChars
     * more text here
     */
    public void correctedBoundaryExactlyAtLimit() { }

}
