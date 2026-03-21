/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting6 {

    public void testMethod() {
        String s = "abc";
        String p = "asd";

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        if (s == """
                a""" + "bc") {
        } // violation above 'Closing quotes (""") of text-block should not be preceded by non-whi'

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //   'Closing quotes (""") of text-block should not be preceded by'
        //   'Text-block quotes are not vertically aligned'
        if ("""
                a""" + """
                bc""" == s) {}
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 2 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ("a" + ("""
                b""" + """
                c""") != s) {}
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 2 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 7 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 7 lines below 'Closing quotes (""") of text-block should not be preceded by'

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 5 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if (s == """
                a""" + """
                b""" + """
                c""") {}
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 2 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += """
                asd""") != p) {
        }

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += "asd") == s + (p + """
                asd""")) {
        }

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += "asd") != s + """
                p""" + p) {
        }

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if (s != s + """
                p""" + p) {
        }

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        String c = ("""
                ab""" + s) != null ?
                (p + """
                        ab""" == null ? p : s) : p;
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // 2 violations 2 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
    }
}
