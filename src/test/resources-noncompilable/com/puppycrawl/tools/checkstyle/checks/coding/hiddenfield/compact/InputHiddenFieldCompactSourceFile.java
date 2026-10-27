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
int count = 0;

void process(String name, int count) {
    // 2 violations above:
    //                   ''name' hides a field.'
    //                   ''count' hides a field.'
    System.out.println(name + ": " + count);
}

void main() { process("test", 42); }
