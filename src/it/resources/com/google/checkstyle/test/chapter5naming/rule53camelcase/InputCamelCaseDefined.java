package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}
  // violation above 'Method name 'XmlHttpRequest' must match pattern'

  void YouTubeImporter() {}
  // violation above 'Method name 'YouTubeImporter' must match pattern'

  void YoutubeImporter() {}
  // violation above 'Method name 'YoutubeImporter' must match pattern'

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}
    // violation above 'Method name 'XmlHttpRequest' must match pattern'

    void YouTubeImporter() {}
    // violation above 'Method name 'YouTubeImporter' must match pattern'

    void YoutubeImporter() {}
    // violation above 'Method name 'YoutubeImporter' must match pattern'
  }

  InputCamelCaseDefined anonymousGood =
          new InputCamelCaseDefined() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}
            // violation above 'Method name 'XmlHttpRequest' must match pattern'

            void YouTubeImporter() {}
            // violation above 'Method name 'YouTubeImporter' must match pattern'

            void YoutubeImporter() {}
            // violation above 'Method name 'YoutubeImporter' must match pattern'
          };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;
    // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}
    // 2 violations above:
    //  'XMLHTTPRequest.* more than '1' .* capital letters.'
    //  'Method name 'XMLHTTPRequest' must match pattern'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;
      // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must match pattern'
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
              //  'Method name 'XMLHTTPRequest' must match pattern'
            };
  }
}
