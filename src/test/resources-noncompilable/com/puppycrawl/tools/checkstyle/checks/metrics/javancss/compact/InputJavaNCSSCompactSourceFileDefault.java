/*
JavaNCSS
methodMaximum = (default)50
classMaximum = (default)1500
fileMaximum = (default)2000
recordMaximum = (default)150


*/

// non-compiled with javac: Compilable with Java25

int a = 1;
int b = 2;

void main() {
}

int compute() {
    int x = 1;
    int y = 2;
    return x + y;
}

record Point(int x, int y) {
    Point {
        System.out.println(x);
    }
}
