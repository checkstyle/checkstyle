package com.google.checkstyle.test.chapter4formatting.rule42blockindentaion; //indent:0 exp:0

import java.util.ArrayList; //indent:0 exp:0
import java.util.HashMap; //indent:0 exp:0
import java.util.Iterator; //indent:0 exp:0
import java.util.List; //indent:0 exp:0
import java.util.Map; //indent:0 exp:0

class FooForClass { //indent:0 exp:0

  String getString(int someInt, String someString) { //indent:2 exp:2
    return "String"; //indent:4 exp:4
  } //indent:2 exp:2

  void fooMethodWithIf() { //indent:2 exp:2

    for (int i = 1; i < 10; i ++) {} //indent:4 exp:4

    for (int i = new SecondForClassWithVeryVeryVeryLongName("Loooooooooooooooooooo" //indent:4 exp:4
        + "oong").getInteger(new FooForClass(), //indent:8 exp:8
          getString(1000000000, "Loooooooooooooooong")); i < 10; i++) {} //indent:10 exp:>=8

    for (Map.Entry<String, String> entry : new SecondForClassWithVeryVeryVeryLongName("Loo" //indent:4 exp:4
        + "ooooooooooooooooooooong").getMap(new //indent:8 exp:8
        FooForClass(), 10000000, //indent:8 exp:8
        getString(10000, "Loooooooooo" //indent:8 exp:8
        + "ooooong")).entrySet()) {} //indent:8 exp:8

    for (Map.Entry<String, String> entry : new SecondForClassWithVeryVeryVeryLongName("Loo" //indent:4 exp:4
        + "ooooooooooooooooooooong").getMap(new //indent:8 exp:8
            FooForClass(), 10000000, //indent:12 exp:>=8
          getString(10000, "Loooooooooo" //indent:10 exp:>=8
               + "ooooong")).entrySet()) {} //indent:15 exp:>=8

    for (String string : new SecondForClassWithVeryVeryVeryLongName(getString(1024 //indent:4 exp:4
        , "Looooooooooooooooooong")). //indent:8 exp:8
        getList(new FooForClass(), 1000, getString(1024, //indent:8 exp:8
        "Loooooooooooooooooooooooooooooooo" //indent:8 exp:8
        + "oooooooooooooooooooooooooooooooooooooooo" //indent:8 exp:8
        + "oooooooooong"))) {} //indent:8 exp:8

    for (String string : new SecondForClassWithVeryVeryVeryLongName(getString(1024 //indent:4 exp:4
        , "Looooooooooooooooooong")). //indent:8 exp:8
            getList(new FooForClass(), 1000, getString(1024, //indent:12 exp:>=8
              "Loooooooooooooooooooooooooooooooo" //indent:14 exp:>=8
          + "oooooooooooooooooooooooooooooooooooooooo" //indent:10 exp:>=8
             + "oooooooooong"))) {} //indent:13 exp:>=8

  } //indent:2 exp:2

  class InnerClassFoo { //indent:2 exp:2

    void fooMethodWithIf() { //indent:4 exp:4

      for (int i = 1; i < 10; i ++) {} //indent:6 exp:6

      for (int i = new SecondForClassWithVeryVeryVeryLongName("Loooooooooooooooooooo" //indent:6 exp:6
          + "oong").getInteger(new FooForClass(), //indent:10 exp:10
            getString(1000000000, "Loooooooooooooooong")); i < 10; i++) {} //indent:12 exp:>=10

      for (Map.Entry<String, String> entry : new SecondForClassWithVeryVeryVeryLongName("Loo" //indent:6 exp:6
          + "ooooooooooooooooooooong").getMap(new //indent:10 exp:10
          FooForClass(), 10000000, //indent:10 exp:10
          getString(10000, "Loooooooooo" //indent:10 exp:10
          + "ooooong")).entrySet()) {} //indent:10 exp:10

      for (Map.Entry<String, String> entry : new SecondForClassWithVeryVeryVeryLongName("Loo" //indent:6 exp:6
            + "ooooooooooooooooooooong").getMap(new //indent:12 exp:>=10
             FooForClass(), 10000000, //indent:13 exp:>=10
                  getString(10000, "Loooooooooo" //indent:18 exp:>=10
               + "ooooong")).entrySet()) {} //indent:15 exp:>=10

      for (String string : new SecondForClassWithVeryVeryVeryLongName(getString(1024 //indent:6 exp:6
          , "Looooooooooooooooooong")). //indent:10 exp:10
          getList(new FooForClass(), 1000, getString(1024, //indent:10 exp:10
          "Loooooooooooooooooooooooooooooooo" //indent:10 exp:10
          + "oooooooooooooooooooooooooooooooooooooooo" //indent:10 exp:10
          + "oooooooooong"))) {} //indent:10 exp:10

      for (String string : new SecondForClassWithVeryVeryVeryLongName(getString(1024 //indent:6 exp:6
               , "Looooooooooooooooooong")). //indent:15 exp:>=10
             getList(new FooForClass(), 1000, getString(1024, //indent:13 exp:>=10
                   "Loooooooooooooooooooooooooooooooo" //indent:19 exp:>=10
                     + "oooooooooooooooooooooooooooooooooooooooo" //indent:21 exp:>=10
          + "oooooooooong"))) {} //indent:10 exp:10
    } //indent:4 exp:4
  } //indent:2 exp:2
}  //indent:0 exp:0

class SecondForClassWithVeryVeryVeryLongName //indent:0 exp:0
        implements Iterable<String>{ //indent:8 exp:>=4

  public SecondForClassWithVeryVeryVeryLongName(String string) { //indent:2 exp:2

  } //indent:2 exp:2

  int getInteger(FooForClass instance, String string) { //indent:2 exp:2
    return -1;   //indent:4 exp:4
  } //indent:2 exp:2

  Map<String, String> getMap(FooForClass instance, int integer, String string) { //indent:2 exp:2
    return new HashMap<String, String>(); //indent:4 exp:4
  } //indent:2 exp:2

  List<String> getList(FooForClass instance, long longLong, String string) { //indent:2 exp:2
    return new ArrayList<String>(); //indent:4 exp:4
  } //indent:2 exp:2

  public Iterator<String> iterator() { //indent:2 exp:2
    return null; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0