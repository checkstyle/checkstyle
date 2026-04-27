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

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/leftcurly/InputLeftCurlyTestNewLineOptionWithLambda.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/leftcurly/InputLeftCurlyTestNewLineOptionWithLambda.java#L23):**

```java
// violation below ''{' at column 32 should be on a new line'
static Runnable r3 = () -> {String.valueOf("ok");};
```

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable/InputFinalLocalVariableConstructor.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable/InputFinalLocalVariableConstructor.java#L23):**

```java
InputFinalLocalVariableConstructor(String str) {
    // violation above 'Variable 'str' should be declared final'
}
```

### Violation N Lines Above or Below

If the violation is further away, you can specify the exact number of lines above or below the
comment.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle/InputJavadocStyleHtml4.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle/InputJavadocStyleHtml4.java#L26):**

```java
// violation 3 lines below 'Unclosed HTML tag found: <code>'
/**
 * This Javadoc contains unclosed tag.
 * <code>unclosed 'code' tag<code>
 */
private void unclosedTag() {}
```

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof/InputUnnecessaryNullCheckWithInstanceOfTernary.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof/InputUnnecessaryNullCheckWithInstanceOfTernary.java#L36):**

```java
return obj != null && obj instanceof Integer ? (Integer) obj * 2
        : obj != null && obj instanceof Double ? (Double) obj / 2
        : "Unknown";
        // violation 2 lines above 'Unnecessary nullity check'
```

## Violation Message Content and Format

### Content

The simplest way to find the message content of a check is by running unit tests.
Follow the steps below:

1) Temporarily specify a dummy message, like this: `// violation 'xxx'`.

2) Run unit test. The test class is located under `src/test/java/...` with the same folder
structure as the input files.

3) The test is expected to fail. Look for the actual violation message printed as part of the
error content.

4) Replace `'xxx'` with the actual message in step 3)

Alternatively, you can also check Checkstyle's online documentation.
Take `AvoidStaticImport` as an example:

1) Go to its [doc page](https://checkstyle.org/checks/imports/avoidstaticimport.html#AvoidStaticImport),

2) Scroll all the way to ['Violation Messages'](https://checkstyle.org/checks/imports/avoidstaticimport.html#Violation_Messages),
and click on `import.avoidStatic`. You will see the actual message content in all languages.

3) Use the English content from `messages.properties` and replace placeholders `{x}` with
the actual values.

### Format

It is best to provide the exact violation message enclosed in single (`'`) or double (`"`) quotes.
Single quotes are generally preferred for consistency.

The violation message specified in the comment is treated as a regular expression (RegExp).
This means that if your expected message contains special RegExp characters
(like `*`, `[`, `]`, `(`, `)`), they must be properly escaped.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/illegaltype/InputIllegalTypeTestPlainAndArraysTypes.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/illegaltype/InputIllegalTypeTestPlainAndArraysTypes.java#L38):**

```java
Boolean[][] value = matrix != null ? matrix : new Boolean[0][0];
// violation above, 'Usage of type 'Boolean\[\]\[\]' is not allowed'
```

Note that single and double quotes inside messages do not need to be escaped.

> [!Important]
> Even message is RegExp based, it is highly discouraged to use RegExp syntax.
>
> If a message is excessively long, you can simply specify part of the message,
> instead of using `.*` to truncate it,
>
> **Bad example**
>
> ```java
> final static int badConstant = 5; // violation '.*'badConstant' must match pattern.*'
> ```
>
> **Good example**
>
> ```java
> final static int badConstant = 5; // violation ''badConstant' must match pattern'
> ```
>

## Multiple Violations on Same Line

When a single line triggers multiple violations, you can group them using a
multi-line format.

**Example from [`src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph/InputJavadocParagraphIncorrect.java#L43):**

```java
// 2 violations 4 lines below:
//  'Redundant <p> tag.'
//  'tag should be preceded with an empty line.'
// violation 2 lines below 'tag should be preceded with an empty line.'
/**<p>Some summary.<p>
 * <p>
 * <p><p>
 * <p>   Some paragraph.<p>*/
```

The syntax `X violations` can be followed by
`Y lines above:`, `Y lines below:`, `above:`, or `below:`.

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

**Example from [`src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator/InputEmptyLineSeparatorWithComments.java`](https://github.com/checkstyle/checkstyle/blob/e9e67c42120524ea613d960af4e443375e37e799/src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator/InputEmptyLineSeparatorWithComments.java#L183):**

```java
public static class Class1 { }


public static class Class2 { } // violation ''CLASS_DEF' has more than 1 empty lines before.'

// ok, because no more than 1 empty lines before
public static class Class3 { }
```

## Good Practice

Violation comments should not be placed arbitrarily. These rules apply:

1) Do not place them inside Javadoc.

**Bad example:**

```java
/** // violation 'Javadoc content should start from the next line after /\*\*.'
 *
 * Third line.
 */
void thirdLineViolation();
```

**Good example:**

```java
// violation below 'Javadoc content should start from the next line after /\*\*.'
/**
 *
 * Third line.
 */
void thirdLineViolation();
```

2) Do not place them between method signature, annotation and Javadoc.

**Bad example:**

```java
/**
 * Description.
 *
 * @param BAD
 *            This param doesn't exist.
 */
@MyAnnotation
// violation 4 lines above 'Unused @param tag for 'BAD'.'
public void test() {}
```

**Good example:**

```java
// violation 4 lines below 'Unused @param tag for 'BAD'.'
/**
 * Description.
 *
 * @param BAD
 *            This param doesn't exist.
 */
@MyAnnotation
public void test() {}
```

## Reference

- Violation messages RegExp and parsing: [`src/test/java/com/puppycrawl/tools/checkstyle/bdd/InlineConfigParser.java`](https://github.com/checkstyle/checkstyle/blob/master/src/test/java/com/puppycrawl/tools/checkstyle/bdd/InlineConfigParser.java)
