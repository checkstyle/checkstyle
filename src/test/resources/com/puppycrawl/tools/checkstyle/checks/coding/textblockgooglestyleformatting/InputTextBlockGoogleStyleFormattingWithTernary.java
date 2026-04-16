/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingWithTernary {
    boolean flag = true;

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    String a = flag
        ? """
          yes
          """
        : """
          no
          """;
    // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    String b = flag ? """
          yes
          """ // violation 'Text-block quotes are not vertically aligned'
        : """
          no
          """;
    // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'

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
