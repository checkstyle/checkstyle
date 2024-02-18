package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

public class InputLineLengthJsniMethods {
  public static native void alertMessage(String msg, int a, int b, int c, int d, int e, int f, int g, int h) /*-{
    $wnd.alert(msg);
    console.log('a really long message here blah blah blah bruh bruh bruhhhhhhhhhhhhhhhhhhhhhhhhhhhh');
  }-*/;
}
