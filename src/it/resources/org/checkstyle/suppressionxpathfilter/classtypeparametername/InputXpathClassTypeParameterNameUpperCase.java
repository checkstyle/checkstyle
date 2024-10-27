package org.checkstyle.suppressionxpathfilter.classtypeparametername;

public class InputXpathClassTypeParameterNameUpperCase {
    class MyClass1<aBc> {} // warn
    class MyClass2<LISTENER> {}
    class MyClass3<REQUESTA> {}
}
