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

package com.google.common.collect.testing.google;

import static com.google.common.collect.testing.features.CollectionFeature.ALLOWS_NULL_VALUES;
import static com.google.common.collect.testing.features.CollectionFeature.FAILS_FAST_ON_CONCURRENT_MODIFICATION;
import static com.google.common.collect.testing.features.CollectionFeature.RESTRICTS_ELEMENTS;
import static com.google.common.collect.testing.features.CollectionFeature.SUPPORTS_ADD;
import static com.google.common.collect.testing.features.CollectionFeature.SUPPORTS_REMOVE;
import static com.google.common.collect.testing.features.CollectionSize.SEVERAL;
import static com.google.common.collect.testing.features.CollectionSize.ZERO;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.testing.Helpers;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Common superclass for {@link MultisetSetCountUnconditionallyTester} and
 * {@link MultisetSetCountConditionallyTester}. It is used by those testers to
 * test calls to the unconditional {@code setCount()} method and calls to the
 * conditional {@code setCount()} method when the expected present count is
 * correct.
 *
 * @author Chris Povirk
 */
@GwtCompatible(emulated = true)
public abstract class AbstractMultisetSetCountTester<E>
    extends AbstractMultisetTester<E> {

  @CollectionFeature.Require(absent = {SUPPORTS_ADD, SUPPORTS_REMOVE})
  public void testSetCount_zeroToZero_unsupported() {
    try {
      assertZeroToZero();
    } catch (UnsupportedOperationException tolerated) {
    }
  }
}
