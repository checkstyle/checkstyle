package com.openjdk.checkstyle.test.chapternaming.rulevariables;

// violation first line 'Header is missing*'

public class InputVariablesDoAndDonts {
    int currentIndex;
    boolean dataAvailable;

    int current_index; // violation 'Name 'current_index' must match pattern'
    boolean DataAvailable; // violation 'Name 'DataAvailable' must match pattern'
}
