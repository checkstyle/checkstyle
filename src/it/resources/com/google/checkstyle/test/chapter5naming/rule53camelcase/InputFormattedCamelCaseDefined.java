package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputFormattedCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}

  // violation 2 lines above 'Method name 'XmlHttpRequest' must match pattern'

  void YouTubeImporter() {}

  // violation 2 lines above 'Method name 'YouTubeImporter' must match pattern'

  void YoutubeImporter() {}

  // violation 2 lines above 'Method name 'YoutubeImporter' must match pattern'

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}

    // violation 2 lines above 'Method name 'XmlHttpRequest' must match pattern'

    void YouTubeImporter() {}

    // violation 2 lines above 'Method name 'YouTubeImporter' must match pattern'

    void YoutubeImporter() {}
    // violation above 'Method name 'YoutubeImporter' must match pattern'
  }

  InputFormattedCamelCaseDefined anonymousGood =
      new InputFormattedCamelCaseDefined() {

        int newCustomerId;

        String innerStopwatch;

        boolean supportsIpv6OnIos;

        void XmlHttpRequest() {}

        // violation 2 lines above 'Method name 'XmlHttpRequest' must match pattern'

        void YouTubeImporter() {}

        // violation 2 lines above 'Method name 'YouTubeImporter' must match pattern'

        void YoutubeImporter() {}
        // violation above 'Method name 'YoutubeImporter' must match pattern'
      };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;

    // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}

    // 2 violations 2 lines above:
    //  'XMLHTTPRequest.* more than '1' .* capital letters.'
    //  'Method name 'XMLHTTPRequest' must match pattern'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;

      // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must match pattern'
    }

    InputFormattedCamelCaseDefined anonymousBad =
        new InputFormattedCamelCaseDefined() {

          int newCustomerID;
          // violation above 'newCustomerID.* more than '1' .* capital letters.'

          boolean supportsIPv6OnIOS;

          // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

          void XMLHTTPRequest() {}
          // 2 violations above:
          //  'XMLHTTPRequest.* more than '1' .* capital letters.'
          //  'Method name 'XMLHTTPRequest' must match pattern'
        };
  }
}
