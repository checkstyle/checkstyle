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

String name = "default";

static void process(String name) {
    System.out.println(name);
}

void main() { process("test"); }
