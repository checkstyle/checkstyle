package org.checkstyle.suppressionxpathfilter.methodtypeparametername;

import java.util.List;

public class InputXpathMethodTypeParameterName3<T> {

    <a_a> a_a myMethod(List<? super T> list) {return null;} // warn

}
