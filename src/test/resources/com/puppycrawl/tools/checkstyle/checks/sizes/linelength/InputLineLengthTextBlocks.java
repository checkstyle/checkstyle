/*
LineLength
fileExtensions = (default)null
ignorePattern = (default)^(package|import) .*
max = 80
tabWidth = (default)0

*/

// violation 'Line is longer than 80 characters (found 95).'
class InputLineLengthTextBlocks {
    // Text blocks should not trigger violations for lines inside them
    String html = """
        <html>
            <body>
                <h1>This is a very long line inside a text block that exceeds eighty characters</h1>
            </body>
        </html>
        """;

    // Text block with long content on opening line should still not trigger
    String json = """
        {"name":"John","age":30,"email":"john.doe@example.com","address":"123 Main Street, Springfield"}
        """;

    // Normal long line outside text block should still trigger
    // violation 'Line is longer than 80 characters (found 91).'
    private String normalLongLineOutsideTextBlock = "This is a normal string assignment that is definitely longer than eighty chars";

    // Text block with very long content - not flagged
    String longContent = """
        This is a very long line inside a text block that exceeds the eighty character line limit
        """;
}
