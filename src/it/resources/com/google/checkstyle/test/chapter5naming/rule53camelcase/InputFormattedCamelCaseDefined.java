package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputFormattedCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  // violation below, 'Method name 'XmlHttpRequest' is not valid per Google Java Style Guide.'
  void XmlHttpRequest() {}

  // violation below, 'Method name 'YouTubeImporter' is not valid per Google Java Style Guide.'
  void YouTubeImporter() {}

  // violation below, 'Method name 'YoutubeImporter' is not valid per Google Java Style Guide.'
  void YoutubeImporter() {}

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    // violation below, 'Method name 'XmlHttpRequest' is not valid per Google Java Style Guide.'
    void XmlHttpRequest() {}

    // violation below, 'Method name 'YouTubeImporter' is not valid per Google Java Style Guide.'
    void YouTubeImporter() {}

    // violation below, 'Method name 'YoutubeImporter' is not valid per Google Java Style Guide.'
    void YoutubeImporter() {}
  }

  InputFormattedCamelCaseDefined anonymousGood =
      new InputFormattedCamelCaseDefined() {

        int newCustomerId;

        String innerStopwatch;

        boolean supportsIpv6OnIos;

        // violation below, 'Method name 'XmlHttpRequest' is not valid per Google Java Style Guide.'
        void XmlHttpRequest() {}

        // violation below 'Method name 'YouTubeImporter' is not valid per Google Java Style Guide.'
        void YouTubeImporter() {}

        // violation below 'Method name 'YoutubeImporter' is not valid per Google Java Style Guide.'
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
    //  'Method name 'XMLHTTPRequest' is not valid per Google Java Style Guide.'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;

      // violation 2 lines above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' is not valid per Google Java Style Guide.'
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
          //  'Method name 'XMLHTTPRequest' is not valid per Google Java Style Guide.'
        };
  }
}
