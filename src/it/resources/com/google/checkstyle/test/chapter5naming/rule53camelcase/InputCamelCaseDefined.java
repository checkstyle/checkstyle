package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputCamelCaseDefined {

  int newCustomerId;

  String innerStopwatch;

  boolean supportsIpv6OnIos;

  void XmlHttpRequest() {}
  // violation above 'Method name 'XmlHttpRequest' must start with a lowercase letter, min 2 chars'

  void YouTubeImporter() {}
  // violation above 'Method name 'YouTubeImporter' must start with a lowercase letter, min 2 chars'

  void YoutubeImporter() {}
  // violation above 'Method name 'YoutubeImporter' must start with a lowercase letter, min 2 chars'

  class InnerGood {

    int newCustomerId;

    String innerStopwatch;

    boolean supportsIpv6OnIos;

    void XmlHttpRequest() {}
    // violation above 'Method name 'XmlHttpRequest' must start with a lowercase letter'

    void YouTubeImporter() {}
    // violation above 'Method name 'YouTubeImporter' must start with a lowercase letter'

    void YoutubeImporter() {}
    // violation above 'Method name 'YoutubeImporter' must start with a lowercase letter'
  }

  InputCamelCaseDefined anonymousGood =
          new InputCamelCaseDefined() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}
            // violation above, ''XmlHttpRequest' must start with a lowercase letter, min 2 chars'

            void YouTubeImporter() {}
            // violation above, ''YouTubeImporter' must start with a lowercase letter, min 2 chars'

            void YoutubeImporter() {}
            // violation above, ''YoutubeImporter' must start with a lowercase letter, min 2 chars'
          };

  class AbbreviationsIncorrect {

    int newCustomerID;
    // violation above 'newCustomerID.* more than '1' .* capital letters.'

    boolean supportsIPv6OnIOS;
    // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

    void XMLHTTPRequest() {}
    // 2 violations above:
    //  'XMLHTTPRequest.* more than '1' .* capital letters.'
    //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'

    class InnerBad {

      int newCustomerID;
      // violation above 'newCustomerID.* more than '1' .* capital letters.'

      boolean supportsIPv6OnIOS;
      // violation above 'supportsIPv6OnIOS.* more than '1' .* capital letters.'

      void XMLHTTPRequest() {}
      // 2 violations above:
      //  'XMLHTTPRequest.* more than '1' .* capital letters.'
      //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'
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
              //  'Method name 'XMLHTTPRequest' must start with a lowercase letter, min 2 chars'
            };
  }
}
