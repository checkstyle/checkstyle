/*
JavaNCSS
methodMaximum = (default)50
classMaximum = 3
fileMaximum = 5
recordMaximum = (default)150


*/

// non-compiled with javac: Compilable with Java25

int a = 1; // violation 'NCSS for this class is 6 (max allowed is 3).'
// violation 1 lines above 'NCSS for this file is 6 (max allowed is 5).'

class Inner { // violation 'NCSS for this class is 4 (max allowed is 3).'
    int x = 1;
    int y = 2;
    void m() {}
}

void main() {}
