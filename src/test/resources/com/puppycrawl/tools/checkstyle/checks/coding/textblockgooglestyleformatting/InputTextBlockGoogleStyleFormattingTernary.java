/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingTernary {

    boolean flag = true;

    void method() {

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        String a = flag
                ? """
                  yes
                  """
                : """
                  no
                  """;
        // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

        // 2 violations 3 lines below:
        // 'Opening quotes (""") of text-block must be on the new line'
        // 'Each line of text in the text block must be indented'
        String b = flag ? """
                  yes
                  """
                : """
                  no
                  """;
        // violation 4 lines above 'Text-block quotes are not vertically aligned'
        // violation 4 lines above 'Opening quotes (""") of text-block must be on the new line'

        String c = flag ?
                """
                yes
                """
                : """
                  no
                  """;
        // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

        String d = flag ?
                """
                yes
                """
                :
                """
                no
                """;
    }
}
