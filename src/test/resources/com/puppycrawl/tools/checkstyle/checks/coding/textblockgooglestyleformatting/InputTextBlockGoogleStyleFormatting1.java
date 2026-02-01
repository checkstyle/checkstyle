/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting1 {

    public String testFun1() {
        // violation 6 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 5 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 6 lines below 'Text-block quotes are not vertically aligned'
        getData(
            """
            first string
            """ + """
            some String
            """, """
            second string
            """
           // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
           // violation 4 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
           // violation 3 lines above 'Text-block quotes are not vertically aligned'
        );

        return
            """
            THE MULTI-LINE MESSAGE""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace'
        //   'Text-block quotes are not vertically aligned'
    }

    public String textFun2() {

        String s =
            """
            Hello there
            """ + getName();

        getData(
            """
              hello there1
              """, 0); // violation 'Text-block quotes are not vertically aligned'

        // violation 3 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        String test1 = s
            + """
            very good
            """.charAt(0) + getName(); // violation 'Text-block quotes are not vertically aligned'

        return s
            +
            """
            very good
            """.charAt(0) + getName();
    }

    public String getName() {
        return "name";
    }

    public static void getData(String data, int length) {}
    public static void getData(String data, String length) {}
}
