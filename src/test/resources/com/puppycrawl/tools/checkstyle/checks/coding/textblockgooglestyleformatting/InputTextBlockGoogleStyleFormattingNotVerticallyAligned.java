/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting {

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

        // violation 3 lines below 'Text-block quotes are not vertically aligned'
        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script
                """;

        // violation 3 lines below 'Text-block quotes are not vertically aligned'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith(
                    """
                this is simple
            """);

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        getData(
"""
            Hello,
            This is a multi-line message.
            """);

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        return
            """
            this is sample text
""";
    }

    public String textFun2() {
        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        final String simpleScript2 =
                """
            this is sample text
            """;

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

        // violation 3 lines below 'Text-block quotes are not vertically aligned'
        getData(
            1,
                """
            this is a multi-line message
            """);

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

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        getData(
                """
            first string
            """ + // violation below 'Text-block quotes are not vertically aligned'
            """
            some String
                """,
"""
            second string
"""
        );

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        return
            """
           THE MULTI-LINE MESSAGE
           """;
    }

    public String textFun3() {
        String s =
            """
            Hello there
            """ + getName();

        String test1 = s
            +
            """
            very good
            """.charAt(0) + getName();

        return s
            +
            """
            very good
            """.charAt(0) + getName();
    }

    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, String data2) {}

    public static void getData(String data, int length) {}

    public static void getData(int length, String data) {}

    public static void getData(int n1, int n2, int n3, int n4 , String data) {}
}
