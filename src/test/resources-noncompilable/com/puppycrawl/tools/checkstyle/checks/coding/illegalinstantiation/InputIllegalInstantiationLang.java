// non-compiled jdk8: different package is intentional for test
package java.lang;

class InputIllegalInstantiationLang {
    Boolean obj = new Boolean();
    Integer obj2 = new Integer();
}

class Boolean{}
class Integer{}

class Input2 {
	String a = new String();
}
