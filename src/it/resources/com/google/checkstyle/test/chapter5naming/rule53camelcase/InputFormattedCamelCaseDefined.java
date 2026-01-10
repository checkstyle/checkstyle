package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputFormattedCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  // violation below, 'Method name 'XmlHttpRequest' must be lowerCamelCase.*'
  void XmlHttpRequest() {}

  // violation below, 'Method name 'YouTubeImporter' must be lowerCamelCase.*'
  void YouTubeImporter() {}

  // violation below, 'Method name 'YoutubeImporter' must be lowerCamelCase.*'
  void YoutubeImporter() {}

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    // violation below, 'Method name 'XmlHttpRequest' must be lowerCamelCase.*'
    void XmlHttpRequest() {}

    // violation below, 'Method name 'YouTubeImporter' must be lowerCamelCase.*'
    void YouTubeImporter() {}

    // violation below, 'Method name 'YoutubeImporter' must be lowerCamelCase.*'
    void YoutubeImporter() {}
  }

  InputFormattedCamelCaseDefined anonymousGood =
      new InputFormattedCamelCaseDefined() {

        int newCustomerId;

        String innerStopwatch;

        boolean supportsIpv6OnIos;

        // violation below, 'Method name 'XmlHttpRequest' must be lowerCamelCase.*'
        void XmlHttpRequest() {}

        // violation below 'Method name 'YouTubeImporter' must be lowerCamelCase.*'
        void YouTubeImporter() {}

        // violation below 'Method name 'YoutubeImporter' must be lowerCamelCase.*'
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
    //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;

      // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'
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
          //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'
        };
  }
}
