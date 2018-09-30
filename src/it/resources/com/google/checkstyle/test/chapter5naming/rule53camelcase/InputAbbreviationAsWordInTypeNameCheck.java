package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class AbbreviationsCorrect {
    
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
    
        AbbreviationsCorrect anonymousGood = new AbbreviationsCorrect() {
        
            int newCustomerId;
        
            String innerStopwatch;
        
            boolean supportsIpv6OnIos;
        
            void XmlHttpRequest() {}
        
            void YouTubeImporter() {}
        
            void YoutubeImporter() {}
    };
}

class AbbreviationsIncorrect {
    
    int newCustomerID;
    
    boolean supportsIPv6OnIOS; //warn
    
    void XMLHTTPRequest() {} //warn
    
    class InnerBad {
        
        int newCustomerID;
        
        boolean supportsIPv6OnIOS; //warn
        
        void XMLHTTPRequest() {} //warn
    }
    
        AbbreviationsCorrect anonymousBad = new AbbreviationsCorrect() {
        
            int newCustomerID;
            
            boolean supportsIPv6OnIOS; //warn
            
            void XMLHTTPRequest() {} //warn
    };
}
