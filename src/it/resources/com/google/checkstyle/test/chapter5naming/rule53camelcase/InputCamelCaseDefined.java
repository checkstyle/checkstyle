package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}

  void YouTubeImporter() {}

  void YoutubeImporter() {}

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}

    void YouTubeImporter() {}

    void YoutubeImporter() {}
  }

  InputCamelCaseDefined anonymousGood =
          new InputCamelCaseDefined() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}

            void YouTubeImporter() {}

            void YoutubeImporter() {}
          };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;
    // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}
    // violation above 'XMLHTTPRequest.* more than '1' .* capital letters.'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;
      // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // violation above 'XMLHTTPRequest.* more than '1' .* capital letters.'
    }

    InputCamelCaseDefined anonymousBad =
            new InputCamelCaseDefined() {

              int newCustomerID;
              // violation above 'newCustomerID.* more than '1' .* capital letters.'

              boolean supportsIPv6OnIOS;
              // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

              void XMLHTTPRequest() {}
              // violation above 'XMLHTTPRequest.* more than '1' .* capital letters.'
            };
  }
}
