package com.google.checkstyle.test.chapter3filestructure.rule32packagestatement; // ok
import java.io.*;
final class InputLineLength
{
    // Long line ---------------------------------------------------------------------------------------- //warn
    // Contains a tab ->    <-
    // Contains trailing whitespace ->

    // Name format tests
    //
    /** Invalid format **/
    public static final int badConstant = 2;
    /** Valid format **/
    public static final int MAX_ROWS = 2;

    /** Invalid format **/
    private static int badStatic = 2;
    /** Valid format **/
    private static int sNumCreated = 0;

    /** Invalid format **/
    private int badMember = 2;
    /** Valid format **/
    private int mNumCreated1 = 0;
    /** Valid format **/
    protected int mNumCreated2 = 0;

    /** commas are wrong **/
    private int[] mInts = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24}; //warn
    
    /**
     * Very long url: https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java
     */
    public void fooMethod() {}
    
    /**
     * Long url without wraping: http://ftp.dlink.ru/pub/D-Link_Solutions/D-Link_Solutions_for_Business.pdf
     */
    public void fooMethodLongFtp() {}
    
    public void fooLongStringUrl() {
        String url = "https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java"; //ok
        processUrl("https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java"); //ok
        processUrl("some line"
                + "https://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java" //ok
                + "+ long fooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo00000000000o line"); //warn
        processUrl("Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line"); //warn
        String[] soooooooooooooooooooooooooooooooooooolongfooooooooooooooooooooooooooooooooooooooooooo = { //warn
            "http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java", //ok
            "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line", //warn
        };
        
        String fakehttps = "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line"; //warn
        
        processUrl(new String[] {
            "http://github.com/checkstyle/checkstyle/blob/master/src/main/java/com/puppycrawl/tools/checkstyle/checks/AvoidEscapedUnicodeCharactersCheck.java", //ok
            "Some long foooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo line", //warn
        });
        
        String s = "text"
                + "text"
                + "text something more.. <a href=\"https://groups.google.com/forum/#!topic/checkstyle-devel/E0z89fzvxGs%5B226-250-false%5D\">long url name, long url name, long url name</a>" //ok
                + "other text";
    }
    
    /**
     * 
     * @param url
     */
    public void processUrl(String url) {}

    /**
     * 
     * @param urls
     */
    public void processUrl(String[] urls){}
}
