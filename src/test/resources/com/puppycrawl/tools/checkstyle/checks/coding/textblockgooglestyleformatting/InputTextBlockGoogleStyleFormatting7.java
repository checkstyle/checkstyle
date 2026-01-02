/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting7 {
    public void testMethod1() {
        char[] channelNames =
            getVi( // violation below 'Opening quotes (""") of text-block must be on the new line'
                        new ObjectString("""
                                </doc>
                                """))
                        .toCharArray();
        // violation 4 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 3 lines above 'Text-block quotes are not vertically aligned'

        String ctx =
            // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
            // violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
            getTestAppContext("""
                        <bean id='docBuilderFactory'
                        """ + // violation 'Text-block quotes are not vertically aligned'
            // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
            // violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
                        getVi(new ObjectString("")) + """
                            <si-xml:xpath-splitter id='splitter'
                        """); // violation 'Text-block quotes are not vertically aligned'
    }

    public void testMethod2(Object config) {
      try {
          Object o1 = new Object();
      } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException(
                  """
                  you may run into rate limiting issues with your IdP""",
                  e);
          // 2 violations 2 lines above:
          //  'Closing quotes (""") of text-block should not be preceded by non-whitespace'
          //  'Text-block quotes are not vertically aligned'
      }

      String v0 = "345";
      String v1 = switch (v0) {
          case "2": // violation below 'Opening quotes (""") of text-block must be on the new line'
              yield """
                       dsfdsf
                       """; // violation 'Text-block quotes are not vertically aligned'
          case "3":
              yield
                      """
                      jkdf
                      """;
          default: yield "12";
      };
    }

    public String testMethod3(String s1) {
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        String s2 = s1.isBlank() ? """
                Mode 1
                """ // violation 'Text-block quotes are not vertically aligned'
                : s1.equals("s1") ?
                """
                Mode 2
                """ : """
                Default Mode
                """;
        // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 3 lines above 'Text-block quotes are not vertically aligned'

        return switch (s1) {
            // violation below 'Opening quotes (""") of text-block must be on the new line'
            case "1" -> """
                        jk
                        """;
            case "2" ->
                """
                method
                """;
            default -> "?";
        };
    }

    public String getVi(ObjectString s1) {
        return s1 + "";
    }

    public String getTestAppContext(String s1) {
        return s1 + "";
    }

    class ObjectString {
        public ObjectString(String s1) {}
    }

    class ComponentModification extends ObjectString {
        public ComponentModification(String n1) {
            // violation below 'Opening quotes (""") of text-block must be on the new line'
            super("""
                    Component %s was modified during phase with priority %s by %s.
                    """); // violation 'Text-block quotes are not vertically aligned'
        }
    }
}
