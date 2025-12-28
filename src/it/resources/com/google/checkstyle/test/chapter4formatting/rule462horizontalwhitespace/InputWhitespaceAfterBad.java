package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputWhitespaceAfterBad {
  /** Some javadoc. */
  public void check1(int x,int y) { // violation '',' is not followed by whitespace.'
    for(int a = 1,b = 2;a < 5;a++,b--)
      ;
    // 6 violations 2 lines above:
    //  ''for' construct must use '{}'s.'
    //  ''for' is not followed by whitespace.'
    //  '',' is not followed by whitespace.'
    //  '';' is not followed by whitespace.'
    //  '';' is not followed by whitespace.'
    //  '',' is not followed by whitespace.'
    while(x == 0) { // violation ''while' is not followed by whitespace.'
      int a = 0,b = 1;
      // 2 violations above:
      //  'Each variable declaration must be in its own statement.'
      //  '',' is not followed by whitespace.'
    }
    do{
      // 2 violations above:
      //  ''do' is not followed by whitespace.'
      //  ''{' is not preceded with whitespace.'
      System.out.println("Testing");
    } while(x == 0 || y == 2); // violation ''while' is not followed by whitespace.'
  }

  /** Some javadoc. */
  public void check2(final int a,final int b) { // violation '',' is not followed by whitespace.'
    if((float)a == 0.0) {
      // 2 violations above:
      //  ''if' is not followed by whitespace.'
      //  ''typecast' is not followed by whitespace.'
      System.out.println("true");
    } else{
      // 2 violations above:
      //  ''else' is not followed by whitespace.'
      //  ''{' is not preceded with whitespace.'
      System.out.println("false");
    }
  }

  /** Some javadoc. */
  public void check3(int...a) { // violation ''...' is not followed by whitespace.'
    // violation below, ''->' is not followed by whitespace.'
    Runnable r2 = () ->String.valueOf("Hello world two!");
    // violation below, ''switch' is not followed by whitespace.'
    switch(a[0]) {
      default:
        break;
    }
  }

  /** Some javadoc. */
  public void check4() throws java.io.IOException {
    // violation below, ''try' is not followed by whitespace.'
    try(java.io.InputStream ignored = System.in;) {}
  }

  /** Some javadoc. */
  public void check5() {
    try {
      /* foo */
    } finally{
      // 2 violations above:
      //  ''finally' is not followed by whitespace.'
      //  ''{' is not preceded with whitespace.'
    }
    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally{
      // 2 violations above:
      //  ''finally' is not followed by whitespace.'
      //  ''{' is not preceded with whitespace.'
    }
  }

  /** Some javadoc. */
  public void check6() {
    try {
      /* foo */
    } catch(Exception e) { // violation ''catch' is not followed by whitespace.'
    }
  }

  /** Some javadoc. */
  public void check7() {
    synchronized(this) { // violation ''synchronized' is not followed by whitespace.'
    }

    synchronized (this) {
    }
  }

  /** Some javadoc. */
  public String check8() {
    return("a" + "b"); // violation ''return' is not followed by whitespace.'
  }
}
