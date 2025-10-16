/*
TextBlockGoogleStyleFormatting

*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingNotVerticallyAligned {
    public static String textFun() {
        final String simpleScript =
"""
        s
""";

        final String simpleScript1 =
            """
            this is simple test;
            """;
        final String simpleScript2 = simpleScript +
                simpleScript1 +
                """
                this is simple script
                """;
        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script
                """; // violation 'Text-block quotes are not vertically aligned'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith(
                    """
                this is simple
            """); // violation 'Text-block quotes are not vertically aligned'

        getData(
"""
            Hello,
            This is a multi-line message.
            """); // violation 'Text-block quotes are not vertically aligned'

        return
            """
            this is sample text
"""; // violation 'Text-block quotes are not vertically aligned'
    }

    public String textFun2() {
        final String simpleScript2 =
                """
            this is sample text
            """; // violation 'Text-block quotes are not vertically aligned'

        getData(
            """
            Hello,
            This is a multi-line message.
            """);

        getData(
            1,
            """
         this is a multi-line message
            """);

        getData(
            1,
                """
            this is a multi-line message
            """); // violation 'Text-block quotes are not vertically aligned'

        getData(1,
            3, 5, 8,
            """
            some String
            """);

        getData(1,
            3, 5, 8,
                """
                some String
                """
        );

        getData(
            """
            first string
            """
            +
            """
            some String
            """,
            """
            second string
            """);

        getData(
                """
            first string
            """ + // violation 'Text-block quotes are not vertically aligned'
            """
            some String
                """, // violation 'Text-block quotes are not vertically aligned'
"""
            second string
"""
        );

        return
            """
           THE MULTI-LINE MESSAGE
           """; // violation 'Text-block quotes are not vertically aligned'
    }

    public static void getData(String data) {}
    public static void getData(String data, String data2) {}
    public static void getData(String data, int length) {}
    public static void getData(int length, String data) {}
    public static void getData(int n1, int n2, int n3, int n4 , String data) {}
}
