/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting6 {

    public void testMethod() {
        String s = "abc";
        String p = "asd";

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
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
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 3 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ("a" + ("""
                b""" + """
                c""") != s) {}
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 3 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 9 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 8 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 8 lines below 'Closing quotes (""") of text-block should not be preceded by'

        // violation 6 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 5 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 5 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if (s == """
                a""" + """
                b""" + """
                c""") {}
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 3 lines above:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += """
                asd""") != p) {
        }

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += "asd") == s + (p + """
                asd""")) {
        }

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if ((s += "asd") != s + """
                p""" + p) {
        }

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // 2 violations 4 lines below:
        //  'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //  'Text-block quotes are not vertically aligned'
        if (s != s + """
                p""" + p) {
        }

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
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
