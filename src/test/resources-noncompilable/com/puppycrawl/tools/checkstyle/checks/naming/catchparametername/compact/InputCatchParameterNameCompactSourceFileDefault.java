/*
CatchParameterName
format = (default)^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    try {
    } catch (Exception e) {
    }
    try {
    } catch (Exception ex) {
    }
    try {
    } catch (Exception exception1) { // violation 'Name 'exception1' must match pattern'
    }
    try { Thread.sleep(1);
    } catch (InterruptedException ie) { // violation 'Name 'ie' must match pattern'
    }
    try {
    } catch (Exception exception) {
    }
}
