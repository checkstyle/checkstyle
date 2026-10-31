/*
UnusedLocalVariable
allowUnnamedVariables = false
jdkVersion = 21

*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 99;

void main() {
    var _ = 2; // violation 'Unused local variable '_''
    System.out.println(topLevelField);
}
