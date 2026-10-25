/*
MethodTypeParameterName
format = ^foo$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodtypeparametername;

public class InputXpathMethodTypeParameterNameInner<T>{

    static class Junk <T> {
        <fo_ extends T> void foo() { // violation 'Name 'fo_' must match pattern '^foo$'.'
        }
    }
}
