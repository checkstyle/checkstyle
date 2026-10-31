/*
CatchParameterName
format = ^[a-z]+$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    try {
    } catch (Exception e) {
    }
    try {
    } catch (Exception exception) {
    }
    try {
    } catch (Exception exception1) { // violation 'Name 'exception1' must match pattern'
    }
    try {
    } catch (Exception myException) { // violation 'Name 'myException' must match pattern'
    }
}
