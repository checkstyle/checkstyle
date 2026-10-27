/*
SuperFinalize


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'Method 'finalize' should call 'super.finalize''
public void finalize() throws Throwable {
}

class Inner {
    public void finalize() throws Throwable {
        super.finalize();
    }
}
