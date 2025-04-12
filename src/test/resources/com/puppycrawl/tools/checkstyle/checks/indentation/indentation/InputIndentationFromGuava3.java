package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.Map; //indent:0 exp:0
import java.util.Map.Entry; //indent:0 exp:0

import com.google.common.collect.Range; //indent:0 exp:0
import com.google.common.collect.RangeMap; //indent:0 exp:0

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
public abstract class InputIndentationFromGuava3<K extends Comparable<?>, V> { //indent:0 exp:0

  public InputIndentationFromGuava3<K, V> subRangeMap1(final Range<K> range) { //indent:2 exp:2
    Range<K> ranges = null; //indent:4 exp:4
    if (checkNotNull(range).isEmpty()) { //indent:4 exp:4
    } else if (ranges.isEmpty() || range.encloses(span())) { //indent:4 exp:4
      return this; //indent:6 exp:6
    } //indent:4 exp:4
    int lowerIndex = SortedLists.binarySearch(); //indent:4 exp:4
    int upperIndex = SortedLists.binarySearch(); //indent:4 exp:4
    if (lowerIndex >= upperIndex) { //indent:4 exp:4
      return null; //indent:6 exp:6
    } //indent:4 exp:4
    final int off = lowerIndex; //indent:4 exp:4
    final int len = upperIndex - lowerIndex; //indent:4 exp:4
    InputIndentationFromGuava3<K, V> outer = null; //indent:4 exp:4
    return outer; //indent:4 exp:4
  } //indent:2 exp:2

  public V get(int index) { //indent:2 exp:2
    K key = null; //indent:4 exp:4
    int len = 0; //indent:4 exp:4
    checkElementIndex(index, len); //indent:4 exp:4
    int off; //indent:4 exp:4
    RangeMap<K, V> ranges = null; //indent:4 exp:4
    if (index == 0 || index == len - 1) { //indent:4 exp:4
      Object range; //indent:6 exp:6
      return ranges.get(key); //indent:6 exp:6
    } else { //indent:4 exp:4
      return ranges.get(key); //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  private void checkElementIndex(int index, Object len) //indent:2 exp:2
  { //indent:2 exp:2

  } //indent:2 exp:2

  private Range<K> checkNotNull(Range<K> range) //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public V get(K key) //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  public Range<K> span() //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public void put(Range<K> range, V value) //indent:2 exp:2
  { //indent:2 exp:2

  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public void putAll(RangeMap<K, V> rangeMap) //indent:2 exp:2
  { //indent:2 exp:2

  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public void remove(Range<K> range) //indent:2 exp:2
  { //indent:2 exp:2

  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public Map<Range<K>, V> asMapOfRanges() //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public RangeMap<K, V> subRangeMap(Range<K> range) //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
  public Entry<Range<K>, V> getEntry(K key) //indent:2 exp:2
  { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2

  private static class SortedLists { //indent:2 exp:2
    public static int binarySearch() { return 4; } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
