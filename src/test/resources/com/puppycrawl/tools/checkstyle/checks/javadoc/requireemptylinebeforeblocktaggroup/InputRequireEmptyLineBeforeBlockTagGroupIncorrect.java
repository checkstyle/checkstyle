/*
RequireEmptyLineBeforeBlockTagGroup
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktaggroup;

import java.io.IOException;

// violation 3 lines below 'Javadoc tag '@since' should be preceded'
/**
 * Some Javadoc.
 * @since 8.36
 */
class InputRequireEmptyLineBeforeBlockTagGroupIncorrect {

    // violation 3 lines below 'Javadoc tag '@param' should be preceded'
    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter.
     */
    private boolean paramTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    // violation 3 lines below 'Javadoc tag '@param' should be preceded'
    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter.
     * @return this one does not need an empty line, but the tag before this one does.
     */
    private boolean paramMultiTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    // violation 1 lines below 'Javadoc tag '@return' should be preceded'
    /**@return clientPort return clientPort if there is another ZK backup can run
     *         when killing the current active; return -1, if there is no backups.
     * @throws IOException
     * @throws InterruptedException
     */
    public int killCurrentActiveZooKeeperServer() throws IOException,
            InterruptedException {
        return 0;
    }
    
    // violation 3 lines below 'Javadoc tag '@see' should be preceded'
    /**
      * Resolve the entity.
      * @see org.xml.sax.EntityResolver#resolveEntity(String, String).
      * @param publicId The public identifier, or <code>null</code>
      *                 if none is available.
      * @param systemId The system identifier provided in the XML
      *                 document. Will not be <code>null</code>.
      * @return an inputsource for this identifier
      * @throws IOException if there is a problem.
      */
    public int resolveEntity(String publicId, String systemId)
            throws IOException {
        return 0;
    }

    // violation 4 lines below 'Javadoc tag '@see' should be preceded'
    /**
    * This {@link Collections#shuffle} is a valid link
    * and the check counts this as a usage.
    * @see Arrays#sort
    * @throws IllegalAccessError::new
    */
    public static void n() {}
    
    // violation 7 lines below 'Javadoc tag '@param' should be preceded'
    /**
     * Parse the expected and actual content strings as XML and assert that the
     * two are "similar" -- i.e. they contain the same elements and attributes
     * regardless of order.
     * <p>Use of this method assumes the
     * <a href="http://xmlunit.sourceforge.net/">XMLUnit<a/> library is available.
     * @param expected the expected XML content
     * @param actual the actual XML content
     * @see org.springframework.test.web.servlet#xpath(String, Object...)
     * @see org.springframework.test.web.servlet#xpath(String, Map, Object...)
     */
    public void assertXmlEquals() {
        
    }
}

// violation 4 lines below 'Javadoc tag '@serial' should be preceded'
/**
* This class is used by the query-building mechanism to represent binary
* operations.
* @serial include
*
* @since 1.5
*/
class BinaryOperation {

}
