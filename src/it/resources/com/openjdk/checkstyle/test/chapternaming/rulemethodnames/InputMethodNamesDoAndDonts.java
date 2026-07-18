package com.openjdk.checkstyle.test.chapternaming.rulemethodnames;

// violation first line 'Header mismatch*'

public class InputMethodNamesDoAndDonts {
    public void expand() {
    }

    public boolean isExpanding() {
        return true;
    }

    public String getState() {
        return null;
    }

    // donts section
    // checkstyle can only apply format it will not check for verbs
    public boolean expanding() {
        return true;
    }

    public String GetState() { // violation 'Name 'GetState' must match pattern'
        return "";
    }

    public int get_index() { // violation 'Name 'get_index' must match pattern'
        return 0;
    }

}
