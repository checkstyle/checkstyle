package org.checkstyle.suppressionxpathfilter.naming.googlemethodname;

import org.junit.jupiter.api.Test;

public interface InputXpathGoogleMethodNameInterface {

    @Test
    void test_1(); // warn 'underscore only allowed between letters or between digits.'
}
