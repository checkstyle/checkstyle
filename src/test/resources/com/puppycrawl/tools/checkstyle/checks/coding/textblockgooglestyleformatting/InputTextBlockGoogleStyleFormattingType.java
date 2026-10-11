/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public record InputTextBlockGoogleStyleFormattingType(
        @Doc(description =
                """
                Some description text.
                A second line so it is a real multi-line text block.
                """)
        String value) {

    @interface Doc {
        String description();
    }
}


@SuppressWarnings(
        """
        Text block in annotation
        """
)
interface Foo {

    void doSomething();

    String getValue();

    default String describe() {
        return
                """
                Default description
                spanning multiple lines.
                """;
    }
}

@SuppressWarnings(
        """
        Text block in annotation
        """
)
@interface Boo {

    String value() default "";
}
