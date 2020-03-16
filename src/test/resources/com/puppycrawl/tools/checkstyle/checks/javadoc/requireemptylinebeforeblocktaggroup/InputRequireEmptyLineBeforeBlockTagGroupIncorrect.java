package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktaggroup;

import java.io.IOException;

/**
 * Config: default
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
}
