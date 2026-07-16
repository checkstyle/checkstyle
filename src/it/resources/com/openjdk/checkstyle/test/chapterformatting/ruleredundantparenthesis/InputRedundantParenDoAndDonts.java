package com.openjdk.checkstyle.test.chapterformatting.ruleredundantparenthesis;

// violation first line 'Header is missing*'

public final class InputRedundantParenDoAndDonts {

    public String styleGuideDo(boolean flag1, boolean flag2) {
        String cmp = (flag1 != flag2) ? "not equal" : "equal";
        return flag1 ? "yes" : "no";
    }

    public String styleGuideDonts(boolean flag) {
        return (flag ? "yes" : "no"); // violation 'Unnecessary parentheses in return statement.'
    }

}
