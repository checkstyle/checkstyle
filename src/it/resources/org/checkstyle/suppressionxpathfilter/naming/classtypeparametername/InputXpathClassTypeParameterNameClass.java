/*
ClassTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.classtypeparametername;

public class InputXpathClassTypeParameterNameClass {
    class MyClass1<A> {}
    class MyClass2<abc> {} // violation 'Name 'abc' must match pattern '^[A-Z]$'.'
    class MyClass3<B> {}
}
