package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeatclauseblock;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since 8.35 // ok
 */
class InputRequireEmptyLineBeforeAtClauseBlockCorrect {

    /**
     * This javadoc does not have an at clause. There should be no violations.
     */
    public static final byte NO_AT_CLAUSE = 0;

    /**
     * This javadoc does has one at-clause, with an empty line. There should be no violations.
     *
     * @since 8.0 // ok
     */
    public static final byte ONE_AT_CLAUSE = 0;

    /**
     * This javadoc has multiple at-clauses, with an empty line before the. There should be no
     * violations.
     *
     * @param input this is the first at-clause. // ok
     * @return this is the second at-clause.
     */
    public static boolean test(boolean input) {
        return false;
    }

    /**
     * @return this only has an at-clause. // ok
     */
    public static boolean test() {
        return false;
    }
}
