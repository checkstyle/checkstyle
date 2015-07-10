package com.puppycrawl.tools.checkstyle.javadoc;

public class InputMissingJavadocTags {
    /**
     * Missing return.
     *
     * @param number to return
     * @throws ThreadDeath sometimes
     */
    int missingReturn(int number) throws ThreadDeath {
        return number;
    }

    /**
     * Missing param.
     *
     * @return number
     * @throws ThreadDeath sometimes
     */
    int missingParam(int number) throws ThreadDeath {
        return number;
    }

    /**
     * Missing throws.
     *
     * @param number to return
     * @return number
     */
    int missingThrows(int number) throws ThreadDeath {
        return number;
    }
}
