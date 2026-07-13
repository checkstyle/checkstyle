package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

import java.util.ArrayList;
import java.util.List;

public class InputConstantsDoAndDonts {
    public static final int BUFFER_SIZE = 1024;

    enum ApplicationMode { RUNNING, PAUSED, TERMINATED }

    // violation below 'Name 'CURRENT_WORDS' must match pattern'
    public final List<String> CURRENT_WORDS = new ArrayList<>();

    enum ApplicationModeOne { Running, Paused, Terminated } // 3 violations
    // 'must match pattern'
    // 'must match pattern'
    // 'must match pattern'
}
