package org.checkstyle.suppressionxpathfilter.classtypeparametername;

public class InputXpathClassTypeParameterNameCamelCase {
    class MyClass1<T> {}
    class MyClass2<ABC> {} // warn
    class MyClass3<RequestT> {}
}
