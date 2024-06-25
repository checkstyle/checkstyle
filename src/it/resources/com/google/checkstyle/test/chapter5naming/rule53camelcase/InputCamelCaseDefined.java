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

        InputCamelCaseDefined anonymousGood
            = new InputCamelCaseDefined() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}

            void YouTubeImporter() {}

            void YoutubeImporter() {}
    };
}

class AbbreviationsIncorrect {

    int newCustomerID; // violation 'Abbreviation .* 'newCustomerID' .* '1' .*'

    boolean supportsIPv6OnIOS; // violation 'Abbreviation .* 'supportsIPv6OnIOS' .* '1' .*'

    void XMLHTTPRequest() {} // violation 'Abbreviation .* 'XMLHTTPRequest' .* '1' .*'

    class InnerBad {

        int newCustomerID; // violation 'Abbreviation .* 'newCustomerID' .* '1' .*'

        boolean supportsIPv6OnIOS; // violation 'Abbreviation .* 'supportsIPv6OnIOS' .* '1' .*'

        void XMLHTTPRequest() {} // violation 'Abbreviation .* 'XMLHTTPRequest' .* '1' .*'
    }

        InputCamelCaseDefined anonymousBad
            = new InputCamelCaseDefined() {

            int newCustomerID; // violation 'Abbreviation .* 'newCustomerID' .* '1' .*'

            boolean supportsIPv6OnIOS; // violation 'Abbreviation .* 'supportsIPv6OnIOS' .* '1' .*'

            void XMLHTTPRequest() {} // violation 'Abbreviation .* 'XMLHTTPRequest' .* '1' .*'
    };
}
