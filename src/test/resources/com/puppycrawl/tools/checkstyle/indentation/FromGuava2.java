package com.puppycrawl.tools.checkstyle.indentation;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

public class FromGuava2<K extends Comparable<?>, V> implements RangeMap<K, V> {

  public FromGuava2<K, V> subRangeMap1(final Range<K> range) {
    Range<K> ranges = null;
    if (checkNotNull(range).isEmpty()) {
    } else if (ranges.isEmpty() || range.encloses(span())) {
      return this;
    }
    int lowerIndex = SortedLists.binarySearch();
    int upperIndex = SortedLists.binarySearch();
    if (lowerIndex >= upperIndex) {
      return null;
    }
    final int off = lowerIndex;
    final int len = upperIndex - lowerIndex;
    FromGuava2<K, V> outer = null;
    return outer;
  }

  public V get(int index) {
    K key = null;
    int len = 0;
    checkElementIndex(index, len);
    int off;
    RangeMap<K, V> ranges = null;
    if (index == 0 || index == len - 1) {
      Object range;
      return ranges.get(key);
    } else {
      return ranges.get(key);
    }
  }

  private void checkElementIndex(int index, Object len)
  {

  }

  boolean isPartialView() {
    return true;
  }

  private Range<K> checkNotNull(Range<K> range)
  {
    return null;
  }

  @Override
  public V get(K key)
  {
    return null;
  }

  public Range<K> span()
  {
    return null;
  }

  @Override
  public void put(Range<K> range, V value)
  {

  }

  @Override
  public void putAll(RangeMap<K, V> rangeMap)
  {

  }

  @Override
  public void clear()
  {

  }

  @Override
  public void remove(Range<K> range)
  {

  }

  @Override
  public Map<Range<K>, V> asMapOfRanges()
  {
    return null;
  }

  @Override
  public RangeMap<K, V> subRangeMap(Range<K> range)
  {
    return null;
  }

  @Override
  public Entry<Range<K>, V> getEntry(K key)
  {
    return null;
  }

  private static class SortedLists {
    public static int binarySearch() { return 4; }
  }
}
