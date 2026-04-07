# Specifying Violations in Input Files

When writing tests for Checkstyle checks, it is crucial to accurately specify where violations 
are expected to occur in the input files. This guide details the various ways to mark expected 
violations using inline comments within your test input files (`Input*.java`).

## Basic Violation Specifiers

The most common way to specify a violation is by adding a comment on the same line where the 
violation is expected.

### Same Line Violation

To specify that a violation occurs on a specific line, append `// violation '<message>'` to the 
end of that line.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/typename/InputTypeName.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/typename/InputTypeName.java#L25):**
```java
public class InputTypeName {} // violation 'Name 'InputTypeName' must match pattern'
```

### Violation Above or Below

Sometimes, placing the comment on the same line makes the line too long or unreadable. 
You can specify that the violation occurs on the line immediately above or below the comment.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/typename/InputTypeName2.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/typename/InputTypeName2.java#L15) (violation below):**
```java
class inputHeaderClass2 { // violation 'Name 'inputHeaderClass2' must match pattern'
    // violation below 'Name 'inputHeaderInterface' must match pattern'
    public interface inputHeaderInterface {};
```

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodTags.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod/InputJavadocMethodTags.java#L87) (violation above):**
```java
    /** @return
     * nothing
     * @return
     * oops */
    // violation 2 lines above 'Duplicate @return tag.'
    String[] results() default {};
```

### Violation N Lines Above or Below

If the violation is further away, you can specify the exact number of lines above or below the 
comment.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java#L11):**
```java
    // violation 3 lines below 'tag should be preceded with an empty line.'
    /**
     * Some Summary.
     * <p>
     * Some paragraph.<p>
     *
     */
    // violation 3 lines above 'tag should be preceded with an empty line.'
    class InputJavadocParagraphIncorrect {
```

## Violation Message Content and Format

### Content

The simplest way to find the message content of a check is via Checkstyle's website. 
Take `AvoidStaticImport` as an example. Go to its [webpage](https://checkstyle.org/checks/imports/avoidstaticimport.html#AvoidStaticImport),
scoll all the way to ['Violation Messages'](https://checkstyle.org/checks/imports/avoidstaticimport.html#Violation_Messages),
and click on `import.avoidStatic`. You will see the actual message content in all languages.

You should use the English content from `messages.properties` and replace placeholders `{x}` with
the actual values.

### Format

It is best to provide the exact violation message enclosed in single (`'`) or double (`"`) quotes. 
Single quotes are generally preferred for consistency.

The violation message specified in the comment is treated as a regular expression. 
This means that if your expected message contains special regex characters 
(like `*`, `[`, `]`, `(`, `)`), they must be properly escaped.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoccontentlocation/InputJavadocContentLocationDefault.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoccontentlocation/InputJavadocContentLocationDefault.java#L21):**
```java
    /** // violation 'Javadoc content should start from the next line after /\*\*.'
     *
     * Third line.
     */
    void thirdLineViolation();
```
Note that single and double quotes inside messages do not need to be escaped.

You can also use regex to truncate excessively long or dynamic messages.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter/InputSuppressWithNearbyTextFilterDefaultConfig.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter/InputSuppressWithNearbyTextFilterDefaultConfig.java#L44):**
```java
    final static int badConstant = 5; // violation '.*'badConstant' must match pattern.*'
```

## Multiple Violations on Same Line

When a single line or block triggers multiple violations, you can group them using a 
multi-line format. The syntax `X violations` can be followed by 
`Y lines above:`, `Y lines below:`, `above:`, or `below:`.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java#L43):**
```java
    // 2 violations 4 lines below:
    //  'Redundant <p> tag.'
    //  'tag should be preceded with an empty line.'
    // violation 2 lines below 'tag should be preceded with an empty line.'
    /**<p>Some summary.<p>
```

## Filtered Violations

When testing filters (like `SuppressWithNearbyTextFilter`), you need to specify violations 
that are expected to be suppressed by the filter. These are marked as `filtered violation`.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter/InputSuppressWithNearbyTextFilterDefaultConfig.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter/InputSuppressWithNearbyTextFilterDefaultConfig.java#L30):**
```java
    // filtered violation below 'Line is longer than 90 characters (found 97).'
    for (int longName = 0; longName < 5; longName++) { // SUPPRESS CHECKSTYLE LineLengthCheck
```

## Specifying Expected Valid Code

Sometimes it is useful to explicitly mark a line of code as valid or expected to pass 
without violations, often to explain why a specific construct is acceptable. You can use 
`// ok, <explanation>` for this purpose.

**Example from [`src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly/InputRightCurlySwitchWhen.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly/InputRightCurlySwitchWhen.java#L19):**
```java
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x >= 2) -> { int y = 0; } // ok, single line
            case ColoredPoint(int x, _, _) when (x == 1) -> {
```

## Reference
- Violation messages RegExp and parsing: [`src/test/java/com/puppycrawl/tools/checkstyle/bdd/InlineConfigParser.java`](https://github.com/checkstyle/checkstyle/blob/master/src/test/java/com/puppycrawl/tools/checkstyle/bdd/InlineConfigParser.java)