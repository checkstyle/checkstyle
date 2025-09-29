/*
RequireEmptyLineBeforeBlockTagGroup
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktaggroup;

import java.io.IOException;

/**
 * Some Javadoc.
 * @since 8.36 // violation
 */
class InputRequireEmptyLineBeforeBlockTagGroupIncorrect {

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter. // violation
     */
    private boolean paramTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    /**
     * This documents the private method.
     * @param thisParamTagNeedsNewline this documents the parameter. // violation
     * @return this one does not need an empty line, but the tag before this one does.
     */
    private boolean paramMultiTagNeedsNewline(boolean thisParamTagNeedsNewline) {
        return false;
    }

    /**@return clientPort return clientPort if there is another ZK backup can run // violation
     *         when killing the current active; return -1, if there is no backups.
     * @throws IOException
     * @throws InterruptedException
     */
    public int killCurrentActiveZooKeeperServer() throws IOException,
            InterruptedException {
        return 0;
    }
    
    /**
      * Resolve the entity.
      * @see org.xml.sax.EntityResolver#resolveEntity(String, String). // violation
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

    /**
    * This {@link Collections#shuffle} is a valid link
    * and the check counts this as a usage.
    * @see Arrays#sort   // violation
    * @throws IllegalAccessError::new
    */
    public static void n() {}
    
    /**
  	 * Parse the expected and actual content strings as XML and assert that the
  	 * two are "similar" -- i.e. they contain the same elements and attributes
  	 * regardless of order.
  	 * <p>Use of this method assumes the
  	 * <a href="http://xmlunit.sourceforge.net/">XMLUnit<a/> library is available.
  	 * @param expected the expected XML content   // violation
  	 * @param actual the actual XML content
  	 * @see org.springframework.test.web.servlet.result.MockMvcResultMatchers#xpath(String, Object...)
  	 * @see org.springframework.test.web.servlet.result.MockMvcResultMatchers#xpath(String, Map, Object...)
  	 */
    public void assertXmlEquals() {
        
    }
}

/**
* This class is used by the query-building mechanism to represent binary
* operations.
* @serial include      // violation
*
* @since 1.5
*/
class BinaryOperation {

}
