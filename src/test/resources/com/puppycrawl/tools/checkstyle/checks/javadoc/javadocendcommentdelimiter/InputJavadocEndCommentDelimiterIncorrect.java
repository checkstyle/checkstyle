/*
JavadocEndCommentDelimiter


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

public class InputJavadocEndCommentDelimiterIncorrect {

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Are you sure?
     **/
    void extraAsteriskOnOwnLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Are you sure?
     ***/
    void twoExtraAsterisksOnOwnLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Are you sure?
     ****/
    void severalExtraAsterisksOnOwnLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Are you sure? **/
    void extraAsteriskOnSingleLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Are you sure? ***/
    void twoExtraAsterisksOnSingleLine() {}

    // violation below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /** Are you sure? ****/
    void severalExtraAsterisksOnSingleLine() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Are you sure? *
     **/
    void contentLineAsteriskAndBadDelimiter() {}

    // violation 3 lines below 'Javadoc closing delimiter should contain exactly one asterisk.'
    /**
     * Are you sure? ****
     **/
    void manyContentAsterisksAndBadDelimiter() {}
}
