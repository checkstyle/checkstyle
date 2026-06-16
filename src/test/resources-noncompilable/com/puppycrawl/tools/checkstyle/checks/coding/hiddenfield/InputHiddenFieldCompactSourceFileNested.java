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

void main() { }

static class StaticNested {
    void process(String name) { }
}

class Inner {
    void process(String name) { } // violation ''name' hides a field'
}
