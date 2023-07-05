package com.puppycrawl.tools.checkstyle.api.filetext;

import java.util.regex.Pattern;

public class InputFileText {
    public static boolean isValidPasswd(String passwd) {
        String reg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~#;:?" +
                "/@&!\"'%*=¬.,-])(?=[^\\s]+$).{8,24}$";
        return Pattern.matches(reg, passwd);
    }
}

/**
 * Test case for the "design for inheritance" check.
 * @author Lars Kühne
 **/
class Extra {
}