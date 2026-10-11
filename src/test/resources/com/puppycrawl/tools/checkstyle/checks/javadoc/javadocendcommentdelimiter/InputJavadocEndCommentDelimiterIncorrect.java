/*
JavadocEndCommentDelimiter


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

public class InputJavadocEndCommentDelimiterIncorrect {

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Javadoc.
     **/
    void extraAsteriskOnOwnLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Javadoc.
     ***/
    void twoExtraAsterisksOnOwnLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Javadoc.
     ****/
    void severalExtraAsterisksOnOwnLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Javadoc. **/
    void extraAsteriskOnSingleLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Javadoc. ***/
    void twoExtraAsterisksOnSingleLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Javadoc. ****/
    void severalExtraAsterisksOnSingleLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Javadoc. *
     **/
    void contentLineAsteriskAndBadDelimiter() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Javadoc. ****
     **/
    void manyContentAsterisksAndBadDelimiter() {}
}
