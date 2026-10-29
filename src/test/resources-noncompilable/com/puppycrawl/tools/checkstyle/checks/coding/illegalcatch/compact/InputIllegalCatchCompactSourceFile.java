/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatch"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() {
    try {
    } catch (RuntimeException ex) { // violation "Catching 'RuntimeException' is not allowed"
    } catch (Exception ex) { // violation "Catching 'Exception' is not allowed"
    } catch (Error ex) { // violation "Catching 'Error' is not allowed"
    } catch (Throwable ex) { // violation "Catching 'Throwable' is not allowed"
    }
}

void qualifiedExceptions() {
    try {
    } catch (java.lang.RuntimeException ex) {
        // violation above "Catching 'java.lang.RuntimeException' is not allowed"
    } catch (java.lang.Exception ex) {
        // violation above "Catching 'java.lang.Exception' is not allowed"
    } catch (java.lang.Error ex) {
        // violation above "Catching 'java.lang.Error' is not allowed"
    } catch (java.lang.Throwable ex) {
        // violation above "Catching 'java.lang.Throwable' is not allowed"
    }
}

void allowedExceptions() {
    try {
    } catch (IllegalArgumentException | IllegalStateException ex) {
    }
}
