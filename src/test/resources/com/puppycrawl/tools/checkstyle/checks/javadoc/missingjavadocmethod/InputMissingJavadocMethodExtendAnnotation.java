/*
MissingJavadocMethod
minLineCount = 2
allowedAnnotations = MyAnnotation, Override
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/* Config:
 * allowedAnnotations = "MyAnnotation, Override"
 * scope = "private"
 * minLineCount = "2"
 */
/**
 * Common superclass for {@link MultisetSetCountUnconditionallyTester} and
 * {@link MultisetSetCountConditionallyTester}. It is used by those testers to
 * test calls to the unconditional {@code setCount()} method and calls to the
 * conditional {@code setCount()} method when the expected present count is
 * correct.
 */
@SuppressWarnings(value = "unchecked")
public abstract class InputMissingJavadocMethodExtendAnnotation<E>
  {

  private static final String SUPPORTS_ADD = "";
  private static final String SUPPORTS_REMOVE = null;

@CollectionFeature.Require // violation
  public void testSetCount_zeroToZero_unsupported() {
    try {
      assertZeroToZero();
    } catch (UnsupportedOperationException tolerated) {
    }
  }

  private void assertZeroToZero() {

  }

  @interface CollectionFeature {
      @interface Require {
      }
  }
}
