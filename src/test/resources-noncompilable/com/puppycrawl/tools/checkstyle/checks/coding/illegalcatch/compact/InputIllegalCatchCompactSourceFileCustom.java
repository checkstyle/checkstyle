/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalCatch">
      <property name="illegalClassNames" value="java.io.IOException, IllegalStateException"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;

void main() {
    try {
        throw new IOException();
    } catch (IOException | IllegalStateException ex) {
        // 2 violations above:
        // "Catching 'IOException' is not allowed"
        // "Catching 'IllegalStateException' is not allowed"
    }
}

void qualifiedExceptions() {
    try {
        throw new IOException();
    } catch (java.io.IOException ex) {
        // violation above "Catching 'java.io.IOException' is not allowed"
    } catch (java.lang.IllegalStateException ex) { // ok, only the simple name is configured
    }
}

void configuredException() {
    try {
    } catch (IllegalStateException ex) {
        // violation above "Catching 'IllegalStateException' is not allowed"
    }
}

void defaultExceptionsAreAllowed() {
    try {
    } catch (RuntimeException ex) {
    } catch (Exception ex) {
    } catch (Error ex) {
    } catch (Throwable ex) {
    }
}
