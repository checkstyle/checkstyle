/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22

*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 99;

void m() {
    int unused = 1; // violation, 'Unused named local variable 'unused''
    int used = 2;
    System.out.println(used);
}

Runnable r = new Runnable() {
    public void run() {}
};

void main() { }
