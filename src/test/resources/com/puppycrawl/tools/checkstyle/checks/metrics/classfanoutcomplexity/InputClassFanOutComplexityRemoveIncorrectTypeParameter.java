package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.util.Collection;
import java.util.SortedSet;

import javax.annotation.Nullable;
/* Config:
 * max =  1
 *
 */
public class InputClassFanOutComplexityRemoveIncorrectTypeParameter<E> { // ok
  private static <E> Collection<E> typePreservingCollection(
      Collection<E> collection, @Nullable Object mutex) {
    if (collection instanceof SortedSet) {
      return sortedSet((SortedSet<E>) collection, mutex);
    }

    return collection(collection, mutex);
  }

  private static <E> Collection<E> collection(Collection<E> collection, Object mutex) {
    return null;
  }

  private static <E> Collection<E> sortedSet(SortedSet<E> collection, Object mutex) {
    return null;
  }

}
