/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

// non-compiled with javac: Compilable with Java25

static String name = "default";

void process(String name) { // violation ''name' hides a field'
    System.out.println(name);
}

void main() { process("test"); }
