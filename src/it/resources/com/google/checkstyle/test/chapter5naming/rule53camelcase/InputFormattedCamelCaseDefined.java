package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputFormattedCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  // violation below, 'Method name 'XmlHttpRequest' must start with a lowercase letter, min 2 chars'
  void XmlHttpRequest() {}

  // violation below, 'Method name 'YouTubeImporter' must start with a lowercase letter'
  void YouTubeImporter() {}

  // violation below, 'Method name 'YoutubeImporter' must start with a lowercase letter'
  void YoutubeImporter() {}

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    // violation below, 'Method name 'XmlHttpRequest' must start with a lowercase letter'
    void XmlHttpRequest() {}

    // violation below, 'Method name 'YouTubeImporter' must start with a lowercase letter'
    void YouTubeImporter() {}

    // violation below, 'Method name 'YoutubeImporter' must start with a lowercase letter'
    void YoutubeImporter() {}
  }

  InputFormattedCamelCaseDefined anonymousGood =
      new InputFormattedCamelCaseDefined() {

        int newCustomerId;

        String innerStopwatch;

        boolean supportsIpv6OnIos;

        // violation below, 'Method name 'XmlHttpRequest' must start with a lowercase letter'
        void XmlHttpRequest() {}

        // violation below 'Method name 'YouTubeImporter' must start with a lowercase'
        void YouTubeImporter() {}

        // violation below 'Method name 'YoutubeImporter' must start with a lowercase letter'
        void YoutubeImporter() {}
      };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;

    // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}

    // 2 violations 2 lines above:
    //  'XMLHTTPRequest.* more than '1' .* capital letters.'
    //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;

      // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'
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
          //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'
        };
  }
}
