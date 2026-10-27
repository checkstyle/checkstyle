/*
IllegalToken
tokens = LITERAL_NATIVE


*/

package org.checkstyle.suppressionxpathfilter.coding.illegaltoken;

public class InputXpathIllegalTokenNative {
  public native void myTest(); // violation 'Using 'native' is not allowed.'
}
