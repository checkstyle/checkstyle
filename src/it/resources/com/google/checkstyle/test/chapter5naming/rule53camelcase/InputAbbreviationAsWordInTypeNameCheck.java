package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class InputAbbreviationAsWordInTypeNameCheck {

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

        InputAbbreviationAsWordInTypeNameCheck anonymousGood
            = new InputAbbreviationAsWordInTypeNameCheck() {

            int newCustomerId;

            String innerStopwatch;

            boolean supportsIpv6OnIos;

            void XmlHttpRequest() {}

            void YouTubeImporter() {}

            void YoutubeImporter() {}
    };
}

class AbbreviationsIncorrect {

    int newCustomerID; // warn

    boolean supportsIPv6OnIOS; //warn

    void XMLHTTPRequest() {} //warn

    class InnerBad {

        int newCustomerID; // warn

        boolean supportsIPv6OnIOS; //warn

        void XMLHTTPRequest() {} //warn
    }

        InputAbbreviationAsWordInTypeNameCheck anonymousBad
            = new InputAbbreviationAsWordInTypeNameCheck() {

            int newCustomerID; // warn

            boolean supportsIPv6OnIOS; //warn

            void XMLHTTPRequest() {} //warn
    };
}
