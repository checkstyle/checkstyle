## Issue Reference

Closes #17882

## Description

This PR adds detailed Javadoc documentation for **JAVADOC_INLINE_TAG_START** token in JavadocCommentsTokenTypes.java.

The documentation includes:
- Description of the token's purpose
- Example Javadoc snippet
- AST tree representation
- @see references to related tokens

## CLI Output

Example input:
```java
* {@code text}
```

Command:
```bash
java -jar checkstyle-13.1.0-SNAPSHOT-all.jar -j src/Test.java | sed "s/\[[0-9]\+:[0-9]\+\]//g"
```

Output:
```
JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
`--CODE_INLINE_TAG -> CODE_INLINE_TAG
    |--JAVADOC_INLINE_TAG_START -> {@
    |--TAG_NAME -> code
    |--TEXT ->  text
    `--JAVADOC_INLINE_TAG_END -> }
```

## Testing

- Local compilation passed
