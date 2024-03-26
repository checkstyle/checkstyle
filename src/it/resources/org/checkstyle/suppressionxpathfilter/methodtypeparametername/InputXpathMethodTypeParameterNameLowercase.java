package org.checkstyle.suppressionxpathfilter.methodtypeparametername;

import java.util.List;

public class InputXpathMethodTypeParameterNameLowercase<T> {

    <a_a> a_a myMethod(List<? super T> list) {return null;} // warn

}
