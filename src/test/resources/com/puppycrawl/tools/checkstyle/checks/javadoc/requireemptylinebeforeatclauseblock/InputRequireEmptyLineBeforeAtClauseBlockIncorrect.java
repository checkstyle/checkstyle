package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeatclauseblock;

import java.io.IOException;

/**
 * Config: default
 * Some Javadoc.
 * @since 8.35 // violation
 */
class InputRequireEmptyLineBeforeAtClauseBlockIncorrect {

    /**
     * This documents the private method.
     * @param thisParamAtClauseNeedsNewline this documents the parameter. // violation
     */
    private boolean paramAtClauseNeedsNewline(boolean thisParamAtClauseNeedsNewline) {
        return false;
    }

    /**
     * This documents the private method.
     * @param thisParamAtClauseNeedsNewline this documents the parameter. // violation
     * @return this one does not need an empty line, but the at-clause before this one does.
     */
    private boolean paramMultiAtClauseNeedsNewline(boolean thisParamAtClauseNeedsNewline) {
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
