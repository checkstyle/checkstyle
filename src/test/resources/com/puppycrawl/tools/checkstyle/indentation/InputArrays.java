package InputAnonymousClasses;

class InputArrays { // indent:0 ; exp:0; ok
  /**
   * Look-up table for factories.
   */
  static final int[] factories = { 666666, 666666, // indent:2 ; exp:2; ok
    666666, 666666, 666666, // indent:4 ; exp:4; ok
    666666, 666666, 666666, // indent:4 ; exp:4; ok
  }; // indent:2 ; exp:2; ok

  static final int[][] factories1 = { // indent:2 ; exp:2; ok
    { 666666, 666666, 666666, 666666 }, // indent:4 ; exp:4; ok
    {}, // no support for SOFT keys // indent:4 ; exp:4; ok
    { 666666, 666666, 666666, 666666 } // indent:4 ; exp:4; ok
  }; // indent:2 ; exp:2; ok

  // binomial(biggestBinomials[k], k) fits in an int, but not
  // binomial(biggestBinomials[k]+1,k).
  static int[] biggestBinomials = { // indent:2 ; exp:2; ok
    Integer.MAX_VALUE, // indent:4 ; exp:4; ok
    Integer.MAX_VALUE, // indent:4 ; exp:4; ok
    65536, // indent:4 ; exp:4; ok
    2345, // indent:4 ; exp:4; ok
    477, // indent:4 ; exp:4; ok
    193, // indent:4 ; exp:4; ok
    110, // indent:4 ; exp:4; ok
    75, // indent:4 ; exp:4; ok
    58, // indent:4 ; exp:4; ok
    49, // indent:4 ; exp:4; ok
    43, // indent:4 ; exp:4; ok
    39, // indent:4 ; exp:4; ok
    37, // indent:4 ; exp:4; ok
    35, // indent:4 ; exp:4; ok
    34, // indent:4 ; exp:4; ok
    34, // indent:4 ; exp:4; ok
    33 // indent:4 ; exp:4; ok
  }; // indent:2 ; exp:2; ok

  @VisibleForTesting static final int[] halfPowersOf10 =
      {3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
  
  @Override
  public byte[] asBytes() {
    return new byte[] {
        (byte) hash,
        (byte) (hash >> 8),
        (byte) (hash >> 16),
        (byte) (hash >> 24),
        (byte) (hash >> 32),
        (byte) (hash >> 40),
        (byte) (hash >> 48),
        (byte) (hash >> 56)};
  }
}

@interface VisibleForTesting {}