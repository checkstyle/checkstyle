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

        String correctA = flag
                ?
                """
                yes
                """
                :
                """
                no
                """;

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

        String correctB = flag ?
                """
                yes
                """
                :
                """
                no
                """;

        String c = flag ?
                """
                yes
                """
                : """
                  no
                  """;
        // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

        String coorectC = flag ?
                """
                yes
                """
                :
                """
                no
                """;
    }
}
