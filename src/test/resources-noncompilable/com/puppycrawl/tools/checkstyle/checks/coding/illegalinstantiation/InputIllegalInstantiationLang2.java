/*
IllegalInstantiation
classes = jjva.lang.Boolean,java.lang*Boolean
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support
package java.lang;

class InputIllegalInstantiationLang2 {
    Boolean obj = new Boolean();
    Integer obj2 = new Integer();
}

class Boolean{}
class Integer{}

class Input2 {
	String a = new String();
}
