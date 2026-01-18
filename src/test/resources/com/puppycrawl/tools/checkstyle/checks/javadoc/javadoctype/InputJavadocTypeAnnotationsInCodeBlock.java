/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Annotations inside pre/code blocks should NOT trigger unknown tag violations.
 * <pre>{@code
 * @Internal
 * class Other {
 *   @Nullable String test(String param) {
 *   }
 * }
 * }</pre>
 */
public class InputJavadocTypeAnnotationsInCodeBlock {
}

/**
 * Example with inline code tag on single line.
 * {@code @Pooled class Example {}}
 */
class InlineCodeExample {
}

/**
 * Example with literal tag.
 * {@literal @SomeAnnotation}
 */
class LiteralExample {
}

/**
 * Multi-line code block with annotations.
 * {@code
 * @Annotation
 * class Foo {}
 * }
 * This should still work.
 */
class MultiLineCodeExample {
}

/**
 * Snippet tag example (Java 18+).
 * {@snippet lang="java" :
 *     @Pooled(value = 5)
 *     public class LegacyApiClient {
 *         public Response call() { return null; }
 *     }
 * }
 */
class SnippetExample {
}

/**
 * Complex snippet with multiple annotations.
 * {@snippet lang="java" :
 *     @Pooled
 *     public class RandomMethod {
 *         public void myMethod() { }
 *     }
 *
 *     public class Checkstyle {
 *
 *          @Inject
 *          Checkstyle checkstyle;
 *
 *          public void run() {
 *              checkstyle.run();
 *          }
 *     }
 * }
 */
class ComplexSnippetExample {
}

/**
 * Code block with nested braces (class definitions).
 * {@code
 * class Outer {
 *     @Nested
 *     class Inner {
 *         void method() {}
 *     }
 * }
 * }
 */
class NestedBracesExample {
}

/**
 * Real unknown tag outside code block.
 * @unknowntag Hello   // violation 'Unknown tag 'unknowntag'.'
 */
class RealUnknownTag {
}

/**
 * Mixed content: real tag and code block.
 * @badtag outside  // violation 'Unknown tag 'badtag'.'
 * {@code @goodtag inside code block}
 */
class MixedContentExample {
}
