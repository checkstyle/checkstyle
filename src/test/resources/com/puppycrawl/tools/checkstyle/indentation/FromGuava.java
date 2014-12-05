package com.puppycrawl.tools.checkstyle.indentation;

import java.util.AbstractMap;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.antlr.v4.runtime.misc.Nullable;

import com.google.common.base.Equivalence;

class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {

  enum Strength {
    /*
     * TODO(kevinb): If we strongly reference the value and aren't loading, we needn't wrap the
     * value. This could save ~8 bytes per entry.
     */

    STRONG {
      <K, V> Object referenceValue(
          Segment<K, V> segment, ReferenceEntry<K, V> entry, int value, int weight) {
        return (weight == 1)
            ? new StrongValueReference<K, V>(value)
            : new WeightedStrongValueReference<K, V>(value, weight);
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.equals();
      }

      @Override
     <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, 
    		    ReferenceEntry<K, V> entry, V value, int weight) {

        return null;
      }
    },

    SOFT {
      <K, V> Object referenceValue1(
          Segment<K, V> segment, ReferenceEntry<Integer, Integer> entry, int value, int weight) {
        return (weight == 1)
            ? new SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry)
            : new WeightedSoftValueReference<K, V>();
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.identity();
      }

      @Override <K, V> Object referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry,
    		    V value, int weight)
      {
        return null;
      }
    },

    WEAK {
      @Override
      <K, V> Object referenceValue(
          Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
        return (weight == 1)
            ? new WeakValueReference<K, V>()
            : new WeightedWeakValueReference<K, V>();
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.identity();
      }
    };

    /**
     * Creates a reference for the given value according to this value strength.
     */
    abstract <K, V> Object referenceValue(
        Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight);

    /**
     * Returns the default equivalence strategy used to compare and hash keys or values referenced
     * at this strength. This strategy will be used unless the user explicitly specifies an
     * alternate strategy.
     */
    abstract Equivalence<Object> defaultEquivalence();
  }

  /**
   * Creates new entries.
   */
  enum EntryFactory {
    STRONG {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }
    },
    STRONG_ACCESS {
      <K, V> StrongAccessEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongAccessEntry<K, V>((int) key, hash, next);
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
      {;
      }
     },
    STRONG_WRITE {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
    },
    STRONG_ACCESS_WRITE {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
    },

    WEAK {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }
    },
    WEAK_ACCESS {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
    },
    WEAK_WRITE {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
    },
    WEAK_ACCESS_WRITE {
      <K, V> StrongEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>();
      }

      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return newNext;
      }
    };
  }

  @Override
  public Set<java.util.Map.Entry<K, V>> entrySet()
  {
    return null;
  }

  @Override
  public V putIfAbsent(K key, V value)
  {
    return null;
  }

  @Override
  public boolean remove(Object key, Object value)
  {
    return false;
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue)
  {
    return false;
  }

  @Override
  public V replace(K key, V value)
  { 
    return null;
  }
  
  private static class ValueReference<T1, T2> {

  }

  private static class ReferenceEntry<T1, T2> {

  }

  private static class Segment<T1, T2> {

    protected Object valueReferenceQueue;

  }

  private static class StrongAccessEntry<T1, T2> {

    public StrongAccessEntry(int key, int hash, ReferenceEntry<T1, T2> next)
    {

    }

  }
  
  private static class StrongValueReference<T1, T2> {

    public StrongValueReference(int value)
    {

    }

  }
  
  private static class WeightedStrongValueReference<T1, T2> {

    public WeightedStrongValueReference(int value, int weight)
    {

    }

  }

  private static class SoftValueReference<T1, T2> {

    public SoftValueReference(Object valueReferenceQueue, int value,
              ReferenceEntry<Integer, Integer> entry)
    {

    }

  }

  private static class WeightedSoftValueReference<T1, T2> {

  }

  private static class WeakValueReference<T1, T2> {

  }

  private static class WeightedWeakValueReference<T1, T2> {

  }

  private static class StrongEntry<T1, T2> {

  }
}
