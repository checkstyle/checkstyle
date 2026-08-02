package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputFormattedCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}

  // violation 2 lines above """Method name 'XmlHttpRequest' must be more
  // than a character, start lowercase, and not have a single lowercase
  // followed by uppercase, or consecutive uppercase."""

  void YouTubeImporter() {}

  // violation 2 lines above """Method name 'YouTubeImporter' must be more
  // than a character, start lowercase, and not have a single lowercase
  // followed by uppercase, or consecutive uppercase."""

  void YoutubeImporter() {}

  // violation 2 lines above """Method name 'YoutubeImporter' must be more than
  // a character, start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}

    // violation 2 lines above """Method name 'XmlHttpRequest' must be more than
    // a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void YouTubeImporter() {}

    // violation 2 lines above """Method name 'YouTubeImporter' must be more than
    // a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void YoutubeImporter() {}
    // violation above """Method name 'YoutubeImporter' must be more than
    // a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""
  }

  InputCamelCaseDefined anonymousGood =
      new InputCamelCaseDefined() {

        int newCustomerId;

        String innerStopwatch;

        boolean supportsIpv6OnIos;

        void XmlHttpRequest() {}

        // violation 2 lines above """Method name 'XmlHttpRequest' must be more than
        // a character, start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void YouTubeImporter() {}

        // violation 2 lines above """Method name 'YouTubeImporter' must be more than
        // a character, start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void YoutubeImporter() {}
        // violation above """Method name 'YoutubeImporter' must be more than
        // a character, start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""
      };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;

    // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    // violation below 'XMLHTTPRequest.* more than '1' .* capital letters.'
    void XMLHTTPRequest() {}

    // violation 2 lines above """Method name 'XMLHTTPRequest' must be more than
    // a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;

      // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      // violation below 'XMLHTTPRequest.* more than '1' .* capital letters.'
      void XMLHTTPRequest() {}
      // violation above """Method name 'XMLHTTPRequest' must be more than
      // a character, start lowercase, and not have a single lowercase followed by
      // uppercase, or consecutive uppercase."""
    }

    InputCamelCaseDefined anonymousBad =
        new InputCamelCaseDefined() {

          int newCustomerID;
          // violation above 'newCustomerID.* more than '1' .* capital letters.'

          boolean supportsIPv6OnIOS;

          // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

          // violation below 'XMLHTTPRequest.* more than '1' .* capital letters.'
          void XMLHTTPRequest() {}
          // violation above """Method name 'XMLHTTPRequest' must be more than
          // a character, start lowercase, and not have a single lowercase followed by
          // uppercase, or consecutive uppercase."""
        };
  }
}
