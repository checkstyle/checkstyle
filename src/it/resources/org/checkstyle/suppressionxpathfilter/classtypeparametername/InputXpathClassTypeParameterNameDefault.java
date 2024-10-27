package org.checkstyle.suppressionxpathfilter.classtypeparametername;

public class InputXpathClassTypeParameterNameDefault {
    class MyClass1<A> {}
    class MyClass2<abc> {} // warn
    class MyClass3<B> {}
}
