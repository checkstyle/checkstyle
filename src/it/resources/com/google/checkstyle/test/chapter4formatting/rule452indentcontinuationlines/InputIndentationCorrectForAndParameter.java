package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class InputIndentationCorrectForAndParameter {

  String getString(int someInt, String someString) {
    return "String";
  }

  void fooMethodWithIf() {

    for (int i = 1; i < 10; i++) {}

    for (int i =
            new SecondForClassWithLongName1("Loooooooooooooooooooo" + "oong")
                .getInteger(new FooForClass(), getString(1000000000, "Loooooooooooooooong"));
        i < 10;
        i++) {}

    for (Map.Entry<String, String> entry :
        new SecondForClassWithLongName1("Loo" + "ooooooooooooooooooooong")
            .getMap(new FooForClass(), 10000000, getString(10000, "Loooooooooo" + "ooooong"))
            .entrySet()) {}

    for (Map.Entry<String, String> entry :
        new SecondForClassWithLongName1("Loo" + "ooooooooooooooooooooong")
            .getMap(new FooForClass(), 10000000, getString(10000, "Loooooooooo" + "ooooong"))
            .entrySet()) {}

    for (String string :
        new SecondForClassWithLongName1(getString(1024, "Looooooooooooooooooong"))
            .getList(
                new FooForClass(),
                1000,
                getString(
                    1024,
                    "Loooooooooooooooooooooooooooooooo"
                        + "oooooooooooooooooooooooooooooooooooooooo"
                        + "oooooooooong"))) {}

    for (String string :
        new SecondForClassWithLongName1(getString(1024, "Looooooooooooooooooong"))
            .getList(
                new FooForClass(),
                1000,
                getString(
                    1024,
                    "Loooooooooooooooooooooooooooooooo"
                        + "oooooooooooooooooooooooooooooooooooooooo"
                        + "oooooooooong"))) {}
  }

  class InnerClassFoo {

    void fooMethodWithIf() {

      for (int i = 1; i < 10; i++) {}

      for (int i =
              new SecondForClassWithLongName1("Loooooooooooooooooooo" + "oong")
                  .getInteger(new FooForClass(), getString(1000000000, "Loooooooooooooooong"));
          i < 10;
          i++) {}

      for (Map.Entry<String, String> entry :
          new SecondForClassWithLongName1("Loo" + "ooooooooooooooooooooong")
              .getMap(new FooForClass(), 10000000, getString(10000, "Loooooooooo" + "ooooong"))
              .entrySet()) {}

      for (Map.Entry<String, String> entry :
          new SecondForClassWithLongName1("Loo" + "ooooooooooooooooooooong")
              .getMap(new FooForClass(), 10000000, getString(10000, "Loooooooooo" + "ooooong"))
              .entrySet()) {}

      for (String string :
          new SecondForClassWithLongName1(getString(1024, "Looooooooooooooooooong"))
              .getList(
                  new FooForClass(),
                  1000,
                  getString(
                      1024,
                      "Loooooooooooooooooooooooooooooooo"
                          + "oooooooooooooooooooooooooooooooooooooooo"
                          + "oooooooooong"))) {}

      for (String string :
          new SecondForClassWithLongName1(getString(1024, "Looooooooooooooooooong"))
              .getList(
                  new FooForClass(),
                  1000,
                  getString(
                      1024,
                      "Loooooooooooooooooooooooooooooooo"
                          + "oooooooooooooooooooooooooooooooooooooooo"
                          + "oooooooooong"))) {}
    }
  }

  class SecondForClassWithLongName1 implements Iterable<String> {

    public SecondForClassWithLongName1(String string) {}

    int getInteger(FooForClass instance, String string) {
      return -1;
    }

    Map<String, String> getMap(FooForClass instance, int integer, String string) {
      return new HashMap<String, String>();
    }

    List<String> getList(FooForClass instance, long longLong, String string) {
      return new ArrayList<String>();
    }

    public Iterator<String> iterator() {
      return null;
    }
  }

  class FooForClass {}
}
