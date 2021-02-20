//non-compiled with javac: compiling on jdk before 9
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
