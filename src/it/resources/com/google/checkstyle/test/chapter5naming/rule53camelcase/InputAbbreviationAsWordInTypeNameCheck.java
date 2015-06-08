package com.google.checkstyle.test.chapter5naming.rule53camelcase;

class AbbrevationsCorrect {
    
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
    
        AbbrevationsCorrect anonumousGood = new AbbrevationsCorrect() {
        
            int newCustomerId;
        
            String innerStopwatch;
        
            boolean supportsIpv6OnIos;
        
            void XmlHttpRequest() {}
        
            void YouTubeImporter() {}
        
            void YoutubeImporter() {}
    };
}

class AbbrevationsIncorrect {
    
    int newCustomerID; //warn
    
    boolean supportsIPv6OnIOS; //warn
    
    void XMLHTTPRequest() {} //warn
    
    class InnerBad {
        
        int newCustomerID; //warn
        
        boolean supportsIPv6OnIOS; //warn
        
        void XMLHTTPRequest() {} //warn
    }
    
        AbbrevationsCorrect anonumousBad = new AbbrevationsCorrect() {
        
            int newCustomerID; //warn
            
            boolean supportsIPv6OnIOS; //warn
            
            void XMLHTTPRequest() {} //warn
    };
}