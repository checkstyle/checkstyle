/*
JavadocMethod
validateThrows = true
allowedAnnotations = (default)Override
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import static org.junit.Assert.assertThrows;

import java.nio.file.AccessDeniedException;

import org.junit.jupiter.api.Test;

public class InputJavadocMethodInTest {
    /* The reporting admin expression in pseudo-code:
   *
   *     isOrgAdmin and isAllowlisted.
   *
   * I've marked the tests with the true/false values for these conditions.
   */

  /** F && F == F */
  @Test
  // violation below, 'Expected @throws tag for 'Exception''
  void testFalseOrFalseAndFalseIsFalse() throws Exception {
    assertThrows(AccessDeniedException.class, () -> {
      throw new AccessDeniedException("User is not an org admin and is not allowlisted");
    });
  }
}
