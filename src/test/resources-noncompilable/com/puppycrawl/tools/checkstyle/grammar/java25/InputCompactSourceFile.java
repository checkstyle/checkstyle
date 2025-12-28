// non-compiled with javac: Compilable with Java25

int counter = 3;
String message = "Hello from compact source file";

void main() {
    IO.println(message);
    IO.println("Counter value: " + counter);
    IO.println("Computed value: " + compute());
}

int compute() {
    return counter * 2;
}
