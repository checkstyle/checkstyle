package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.AbstractMap; //indent:0 exp:0
import java.util.Set; //indent:0 exp:0
import java.util.concurrent.ConcurrentMap; //indent:0 exp:0

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
class InputIndentationFromGuava<K, V> extends AbstractMap<K, V> //indent:0 exp:0
    implements ConcurrentMap<K, V> { //indent:4 exp:4

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

  static class ValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  static class ReferenceEntry<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  static class Segment<T1, T2> { //indent:2 exp:2

    protected Object valueReferenceQueue; //indent:4 exp:4

  } //indent:2 exp:2

  static class StrongAccessEntry<T1, T2> { //indent:2 exp:2

    public StrongAccessEntry(T1 key, int hash, ReferenceEntry<T1, T2> next) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  static class StrongValueReference<T1, T2> { //indent:2 exp:2

    public StrongValueReference(int value) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  static class WeightedStrongValueReference<T1, T2> { //indent:2 exp:2

    public WeightedStrongValueReference(int value, int weight) //indent:4 exp:4
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  static class SoftValueReference<T1, T2> { //indent:2 exp:2

    public SoftValueReference(Object valueReferenceQueue, int value, //indent:4 exp:4
              ReferenceEntry<Integer, Integer> entry) //indent:14 exp:>=8
    { //indent:4 exp:4

    } //indent:4 exp:4

  } //indent:2 exp:2

  static class WeightedSoftValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  static class WeakValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

  static class WeightedWeakValueReference<T1, T2> { //indent:2 exp:2

  } //indent:2 exp:2

} //indent:0 exp:0
