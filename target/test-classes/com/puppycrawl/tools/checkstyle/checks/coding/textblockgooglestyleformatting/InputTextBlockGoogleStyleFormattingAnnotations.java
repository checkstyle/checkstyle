/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SuppressWarnings(
        """
        Text block in annotation
        """
)
public class InputTextBlockGoogleStyleFormattingAnnotations {
    public static void textFun() {}

    @Target(ElementType.TYPE)
    public @interface GetAnnon {}

    @Target(ElementType.TYPE)
    public @interface SetAnnon {}

    @Target(ElementType.TYPE)
    public @interface NamedNativeQuery{
        String name();
        String query();
    }

    @NamedNativeQuery(
            name = "InventoryHost",
            query =
                    """
                    h.id as inventory_id, h.org_id, h.modified_on, h.account, h.display_name,
                    """)
    @GetAnnon
    @SetAnnon
    class Inner {
        public @interface Command{
            String name();
            String description();
        }
    }

    @Inner.Command(
            name = "Inner2",
            description =
                """
                Create a private key and print the associated address on the blockchain
                """
    )
    class Inner2 {}

    @Inner.Command(
            name = "Inner3",
            // violation below 'Opening quotes (""") of text-block must be on the new line'
            description = """
                Create a private key and print the associated address on the blockchain
                          """)
    class Inner3 {}
}

// violation below 'Opening quotes (""") of text-block must be on the new line'
@SuppressWarnings("""
Text block in Annotation
""") // violation 'Text-block quotes are not vertically aligned'
class OuterFirst{}
