public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V> {

  @Override
  public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
    if (checkNotNull(range).isEmpty()) {
      return ImmutableRangeMap.of();
    } else if (ranges.isEmpty() || range.encloses(span())) {
      return this;
    }
    int lowerIndex = SortedLists.binarySearch(
        ranges, Range.<K>upperBoundFn(), range.lowerBound,
        KeyPresentBehavior.FIRST_AFTER, KeyAbsentBehavior.NEXT_HIGHER);
    int upperIndex = SortedLists.binarySearch(ranges, 
        Range.<K>lowerBoundFn(), range.upperBound,
        KeyPresentBehavior.ANY_PRESENT, KeyAbsentBehavior.NEXT_HIGHER);
    if (lowerIndex >= upperIndex) {
      return ImmutableRangeMap.of();
    }
    final int off = lowerIndex;
    final int len = upperIndex - lowerIndex;
    ImmutableList<Range<K>> subRanges = new ImmutableList<Range<K>>() {
      @Override
      public int size() {
        return len;
      }

      @Override
      public Range<K> get(int index) {
        checkElementIndex(index, len);
        if (index == 0 || index == len - 1) {
          return ranges.get(index + off).intersection(range);
        } else {
          return ranges.get(index + off);
        }
      }

      @Override
      boolean isPartialView() {
        return true;
      }
    };
    final ImmutableRangeMap<K, V> outer = this;
    return new ImmutableRangeMap<K, V>(
        subRanges, values.subList(lowerIndex, upperIndex)) {
          @Override
          public ImmutableRangeMap<K, V> subRangeMap(Range<K> subRange) {
            if (range.isConnected(subRange)) {
              return outer.subRangeMap(subRange.intersection(range));
            } else {
              return ImmutableRangeMap.of();
            }
          }
    };
  }
}
