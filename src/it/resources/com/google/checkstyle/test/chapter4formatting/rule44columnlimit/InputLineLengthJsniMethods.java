package com.google.checkstyle.test.chapter4formatting.rule44columnlimit;

public class InputLineLengthJsniMethods {

  // JSNI method
  public static native void alertMessage(String msg) /*-{
    $wnd.alert(msg);
    console.log('my very long message ....................................................................................................................');
  }-*/;

  // just a comment, no JSNI delimiters
  public static native void alertMessage2(String msg) /*
    $wnd.alert(msg);
    console.log('my very long message ....................................................................................................................'); // warn
  */;
}
