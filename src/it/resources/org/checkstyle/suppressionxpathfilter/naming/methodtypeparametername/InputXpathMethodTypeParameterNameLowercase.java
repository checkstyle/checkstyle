/*
MethodTypeParameterName
format = ^[a-z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.methodtypeparametername;

import java.util.List;

public class InputXpathMethodTypeParameterNameLowercase<T> {

    // violation below 'Name 'a_a' must match pattern '^[a-z]$'.'
    <a_a> a_a myMethod(List<? super T> list) {return null;}

}
