/*
IllegalInstantiation
classes = java.lang.Boolean,java.lang.String
tokens = (default)CLASS_DEF


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support
package java.lang;

class InputIllegalInstantiationLang {
    Boolean obj = new Boolean(); // violation 'Instantiation of java.lang.Boolean'
    Integer obj2 = new Integer();
}

class Boolean{}
class Integer{}

class Input2 {
	String a = new String(); // violation 'Instantiation of java.lang.String'
}
