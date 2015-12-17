package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

import java.util.AbstractMap; //indent:0 exp:0
import java.util.Set; //indent:0 exp:0
import java.util.concurrent.ConcurrentMap; //indent:0 exp:0

import javax.xml.bind.annotation.XmlElement; //indent:0 exp:0

import com.google.common.base.Equivalence; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> { //indent:0 exp:0

  enum Strength { //indent:2 exp:2
    /*                                                                                            //indent:4 exp:4
     *     (kevinb): If we strongly reference the value and aren't loading, we needn't wrap the   //indent:5 exp:5
     * value. This could save ~8 bytes per entry.                                                 //indent:5 exp:5
     */                                                                                           //indent:5 exp:5

    STRONG { //indent:4 exp:4
      <K, V> Object referenceValue( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> entry, int value, int weight) { //indent:10 exp:>=10
        return (weight == 1) //indent:8 exp:8
            ? new StrongValueReference<K, V>(value) //indent:12 exp:>=12
            : new WeightedStrongValueReference<K, V>(value, weight); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      Equivalence<Object> defaultEquivalence() { //indent:6 exp:6
        return Equivalence.equals(); //indent:8 exp:8
      } //indent:6 exp:6

      @Override //indent:6 exp:6
     <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, //indent:5 exp:5
    		    ReferenceEntry<K, V> entry, V value, int weight) { //indent:16 exp:>=10

        return null; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    SOFT { //indent:4 exp:4
      <K, V> Object referenceValue1( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<Integer, Integer> entry, int value, int weight) { //indent:10 exp:>=10
        return (weight == 1) //indent:8 exp:8
            ? new SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry) //indent:12 exp:>=12
            : new WeightedSoftValueReference<K, V>(); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      Equivalence<Object> defaultEquivalence() { //indent:6 exp:6
        return Equivalence.identity(); //indent:8 exp:8
      } //indent:6 exp:6

      @Override <K, V> Object referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, //indent:6 exp:6
    		    V value, int weight) //indent:16 exp:>=10
      { //indent:6 exp:6
        return null; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    WEAK { //indent:4 exp:4
      @Override //indent:6 exp:6
      <K, V> Object referenceValue( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) { //indent:10 exp:>=10
        return (weight == 1) //indent:8 exp:8
            ? new WeakValueReference<K, V>() //indent:12 exp:>=12
            : new WeightedWeakValueReference<K, V>(); //indent:12 exp:>=12
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      Equivalence<Object> defaultEquivalence() { //indent:6 exp:6
        return Equivalence.identity(); //indent:8 exp:8
      } //indent:6 exp:6
    }; //indent:4 exp:4

    /**                                                                          //indent:4 exp:4
     * Creates a reference for the given value according to this value strength. //indent:5 exp:5
     */                                                                          //indent:5 exp:5
    abstract <K, V> Object referenceValue( //indent:4 exp:4
        Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight); //indent:8 exp:>=8

    /**                                                                                            //indent:4 exp:4
     * Returns the default equivalence strategy used to compare and hash keys or values referenced //indent:5 exp:5
     * at this strength. This strategy will be used unless the user explicitly specifies an        //indent:5 exp:5
     * alternate strategy.                                                                         //indent:5 exp:5
     */                                                                                            //indent:5 exp:5
    abstract Equivalence<Object> defaultEquivalence(); //indent:4 exp:4
  } //indent:2 exp:2

  /**                        //indent:2 exp:2
   * Creates new entries.    //indent:3 exp:3
   */                        //indent:3 exp:3
  enum EntryFactory { //indent:2 exp:2
    STRONG { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    STRONG_ACCESS { //indent:4 exp:4
      <K, V> StrongAccessEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongAccessEntry<K, V>(key, hash, next); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
      {; //indent:6 exp:6
      } //indent:6 exp:6
     }, //indent:5 exp:5
    STRONG_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    STRONG_ACCESS_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4

    WEAK { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_ACCESS { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
    }, //indent:4 exp:4
    WEAK_ACCESS_WRITE { //indent:4 exp:4
      <K, V> StrongEntry<K, V> newEntry( //indent:6 exp:6
          Segment<K, V> segment, K key, int hash, @XmlElement ReferenceEntry<K, V> next) { //indent:10 exp:>=10
        return new StrongEntry<K, V>(); //indent:8 exp:8
      } //indent:6 exp:6

      <K, V> ReferenceEntry<K, V> copyEntry( //indent:6 exp:6
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) { //indent:10 exp:>=10
        return newNext; //indent:8 exp:8
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2

  @Override //indent:2 exp:2
  public Set<java.util.Map.Entry<K, V>> entrySet() //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Override //indent:2 exp:2
  public V putIfAbsent(K key, V value) //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Override //indent:2 exp:2
  public boolean remove(Object key, Object value) //indent:2 exp:2
  { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  @Override //indent:2 exp:2
  public boolean replace(K key, V oldValue, V newValue) //indent:2 exp:2
  { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  @Override //indent:2 exp:2
  public V replace(K key, V value) //indent:2 exp:2
  {  //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  private static class ValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  private static class ReferenceEntry<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  private static class Segment<T1, T2> { //indent:2 exp:2

    protected Object valueReferenceQueue; //indent:4 exp:4

  } //indent:2 exp:2

  private static class StrongAccessEntry<T1, T2> { //indent:2 exp:2

    public StrongAccessEntry(T1 key, int hash, ReferenceEntry<T1, T2> next) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  private static class StrongValueReference<T1, T2> { //indent:2 exp:2

    public StrongValueReference(int value) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  private static class WeightedStrongValueReference<T1, T2> { //indent:2 exp:2

    public WeightedStrongValueReference(int value, int weight) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  private static class SoftValueReference<T1, T2> { //indent:2 exp:2

    public SoftValueReference(Object valueReferenceQueue, int value, //indent:4 exp:4
              ReferenceEntry<Integer, Integer> entry) //indent:14 exp:>=8
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  private static class WeightedSoftValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  private static class WeakValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  private static class WeightedWeakValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  private static class StrongEntry<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2
} //indent:0 exp:0
