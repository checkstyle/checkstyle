/*
JavaNCSS
methodMaximum = 2
classMaximum = 3
fileMaximum = 4
recordMaximum = 1


*/

// non-compiled with javac: Compilable with Java25

// until #22537: the top-level fields are not counted and classMaximum is not checked
int a = 1; // violation 'NCSS for this file is 8'
int b = 2;

void main() {
}

int compute() { // violation 'NCSS for this method is 4'
    int x = 1;
    int y = 2;
    return x + y;
}

record Point(int x, int y) { // violation 'NCSS for this record is 3'
    Point {
        System.out.println(x);
    }
}
