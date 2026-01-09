/*
IllegalInstantiation
classes = java.lang.Boolean, .BadExample
tokens = (default)CLASS_DEF


*/

public class InputIllegalInstantiationNoPackage {
    Boolean obj1 = new Boolean(true); // violation 'Instantiation of java.lang.Boolean'
    String obj2 = new String();
    BadExample obj3 = new BadExample();

    private static class BadExample {}
}
