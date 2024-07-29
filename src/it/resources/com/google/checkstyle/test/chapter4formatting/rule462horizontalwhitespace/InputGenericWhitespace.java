package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

class InputGenericWhitespace implements Comparable<InputGenericWhitespace>, Serializable {
  <T> InputGenericWhitespace(List<T> things, int i) {}

  public <T> InputGenericWhitespace(List<T> things) {}

  public static <T> Callable<T> callable(Runnable task, T result) {
    return null;
  }

  // 2 violations 3 lines below:
  //  ''\<' is not preceded with whitespace.'
  //  ''\>' should followed by whitespace.'
  public static<T>Callable<T> callable2(Runnable task, T result) {
    Map<Class<?>, Integer> x = new HashMap<Class<?>, Integer>();
    for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
      entry.getValue();
    }
    Class<?>[] parameterClasses = new Class<?>[0];
    return null;
  }

  void meth() {
    List<Integer> x = new ArrayList<Integer>();
    List<List<Integer>> y = new ArrayList<List<Integer>>();
    List < Integer > a = new ArrayList < Integer >();
    // 6 violations above:
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is preceded with whitespace.'
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is preceded with whitespace.'
    List < List < Integer > > b = new ArrayList < List < Integer > >();
    // 14 violations above:
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is followed by whitespace.'
    //  ''\>' is preceded with whitespace.'
    //  ''\>' is preceded with whitespace.'
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\<' is followed by whitespace.'
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is followed by whitespace.'
    //  ''\>' is preceded with whitespace.'
    //  ''\>' is preceded with whitespace.'
  }

  public int compareTo(InputGenericWhitespace obj) {
    return 0;
  }

  public int getConstructor(Class<?>... parameterTypes) {
    Collections.<Object>emptySet();
    Collections. <Object> emptySet();
    // 2 violations above:
    //  ''\<' is preceded with whitespace.'
    //  ''\>' is followed by whitespace.'
    return 666;
  }

  public interface IntEnum {}

  public static class IntEnumValueType<E extends Enum<E> & IntEnum> {}

  public static class IntEnumValueType2<E extends Enum<E>& IntEnum> {
    // 2 violations above:
    //  ''&' is not preceded with whitespace.'
    //  ''&' is not preceded with whitespace.'
  }

  public static class IntEnumValueType3<E extends Enum<E>  & IntEnum> {
    // violation above ''\>' is followed by whitespace.'
  }
}
