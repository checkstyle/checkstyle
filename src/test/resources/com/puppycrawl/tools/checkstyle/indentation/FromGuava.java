@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {

  enum Strength {
    /*
     * TODO(kevinb): If we strongly reference the value and aren't loading, we needn't wrap the
     * value. This could save ~8 bytes per entry.
     */

    STRONG {
      @Override
      <K, V> ValueReference<K, V> referenceValue(
          Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
        return (weight == 1)
            ? new StrongValueReference<K, V>(value)
            : new WeightedStrongValueReference<K, V>(value, weight);
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.equals();
      }
    },

    SOFT {
      @Override
      <K, V> ValueReference<K, V> referenceValue(
          Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
        return (weight == 1)
            ? new SoftValueReference<K, V>(segment.valueReferenceQueue, value, entry)
            : new WeightedSoftValueReference<K, V>(
                segment.valueReferenceQueue, value, entry, weight);
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.identity();
      }
    },

    WEAK {
      @Override
      <K, V> ValueReference<K, V> referenceValue(
          Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
        return (weight == 1)
            ? new WeakValueReference<K, V>(segment.valueReferenceQueue, value, entry)
            : new WeightedWeakValueReference<K, V>(
                segment.valueReferenceQueue, value, entry, weight);
      }

      @Override
      Equivalence<Object> defaultEquivalence() {
        return Equivalence.identity();
      }
    };

    /**
     * Creates a reference for the given value according to this value strength.
     */
    abstract <K, V> ValueReference<K, V> referenceValue(
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
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongEntry<K, V>(key, hash, next);
      }
    },
    STRONG_ACCESS {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongAccessEntry<K, V>(key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyAccessEntry(original, newEntry);
        return newEntry;
      }
    },
    STRONG_WRITE {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongWriteEntry<K, V>(key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyWriteEntry(original, newEntry);
        return newEntry;
      }
    },
    STRONG_ACCESS_WRITE {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new StrongAccessWriteEntry<K, V>(key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyAccessEntry(original, newEntry);
        copyWriteEntry(original, newEntry);
        return newEntry;
      }
    },

    WEAK {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new WeakEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
      }
    },
    WEAK_ACCESS {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new WeakAccessEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyAccessEntry(original, newEntry);
        return newEntry;
      }
    },
    WEAK_WRITE {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new WeakWriteEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyWriteEntry(original, newEntry);
        return newEntry;
      }
    },
    WEAK_ACCESS_WRITE {
      @Override
      <K, V> ReferenceEntry<K, V> newEntry(
          Segment<K, V> segment, K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        return new WeakAccessWriteEntry<K, V>(segment.keyReferenceQueue, key, hash, next);
      }

      @Override
      <K, V> ReferenceEntry<K, V> copyEntry(
          Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
        copyAccessEntry(original, newEntry);
        copyWriteEntry(original, newEntry);
        return newEntry;
      }
    };
  }
}
