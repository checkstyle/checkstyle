# Description

Related PR discussion:
https://github.com/checkstyle/checkstyle/pull/20807#discussion_r3610730364

If Javadoc documents an exception that is not present in the method or
constructor `throws` clause, the documented exception is assumed to be unchecked.
Such documentation should be allowed only when the exception is explicitly thrown
in the method body.

Proposed Check:

```text
JavadocThrowsClauseConsistency
```

The Check should visit methods, constructors, and compact constructors and
report a violation when Javadoc documents an exception in an `@throws` or
`@exception` tag, but the exception is not declared in the Java `throws` clause
and is not explicitly thrown in the method body with `throw new`.

If Javadoc has more exception tags than the method signature, the extra tags are
assumed to document unchecked exceptions. Such documentation is reasonable only
when the unchecked exception is explicitly thrown by the method. If the exception
is not explicitly thrown in the method code, documenting it should be discouraged
because the tag is likely stale, speculative, or tied to hidden implementation
behavior.

Matching should compare simple class names, so `java.io.IOException` in the
signature matches `IOException` in Javadoc and vice versa. This keeps the Check
independent from import and type resolution.

For explicit throws in the method body, only direct
`throw new ExceptionType(...)` expressions should be matched reliably. Type
hierarchy resolution is still out of scope.

# Example

```java
import java.io.EOFException;
import java.io.IOException;

public class Test {

    /**
     * Reads the next byte.
     *
     * @return the next byte
     * @throws IOException if reading fails
     */
    public int valid1() throws IOException {
        return 0;
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte
     * @exception java.io.IOException if reading fails
     */
    public int valid2() throws IOException {
        return 0;
    }

    /**
     * Parses the input.
     *
     * @param value input value
     * @throws IllegalArgumentException if the input is invalid
     */
    public void validUncheckedDocumentation(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte
     */
    public int validMissingJavadocForThrows() throws IOException {
        return 0;
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte
     * @throws EOFException if the stream ended unexpectedly
     */
    public int validExtraJavadocForExplicitThrow() throws IOException {
        throw new EOFException();
    }

    /**
     * Parses the input.
     *
     * @param value input value
     * @throws NullPointerException if the input is null
     */
    public void invalidDocumentedButNotThrown(String value) { // violation
        System.out.println(value);
    }

    /**
     * Updates the state.
     *
     * @exception IllegalStateException if the state cannot be updated
     */
    public void invalidExceptionTagButNotThrown() { // violation
    }
}
```

# Output

```text
[ERROR] Test.java::: Exception 'NullPointerException' is documented in a @throws or @exception tag but is neither declared in the throws clause nor explicitly thrown in the method body. [JavadocThrowsClauseConsistency]
[ERROR] Test.java::: Exception 'IllegalStateException' is documented in a @throws or @exception tag but is neither declared in the throws clause nor explicitly thrown in the method body. [JavadocThrowsClauseConsistency]
Audit done.
Checkstyle ends with 2 errors.
```
