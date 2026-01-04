package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}
  // violation above 'Method name 'XmlHttpRequest' must be lowerCamelCase.*'

  void YouTubeImporter() {}
  // violation above 'Method name 'YouTubeImporter' must be lowerCamelCase.*'

  void YoutubeImporter() {}
  // violation above 'Method name 'YoutubeImporter' must be lowerCamelCase.*'

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}
    // violation above 'Method name 'XmlHttpRequest' must be lowerCamelCase.*'

    void YouTubeImporter() {}
    // violation above 'Method name 'YouTubeImporter' must be lowerCamelCase.*'

    void YoutubeImporter() {}
    // violation above 'Method name 'YoutubeImporter' must be lowerCamelCase.*'
  }

  InputCamelCaseDefined anonymousGood =
          new InputCamelCaseDefined() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}
            // violation above, ''XmlHttpRequest' must be lowerCamelCase.*'

            void YouTubeImporter() {}
            // violation above, ''YouTubeImporter' must be lowerCamelCase.*'

            void YoutubeImporter() {}
            // violation above, ''YoutubeImporter' must be lowerCamelCase.*'
          };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;
    // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}
    // 2 violations above:
    //  'XMLHTTPRequest.* more than '1' .* capital letters.'
    //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;
      // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'
    }

    InputCamelCaseDefined anonymousBad =
            new InputCamelCaseDefined() {

              int newCustomerID;
              // violation above 'newCustomerID.* more than '1' .* capital letters.'

              boolean supportsIPv6OnIOS;
              // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

              void XMLHTTPRequest() {}
              // 2 violations above:
              //  'XMLHTTPRequest.* more than '1' .* capital letters.'
              //  'Method name 'XMLHTTPRequest' must be lowerCamelCase.*'
            };
  }
}
