//Compilable with Java8 //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

import java.util.ArrayList; //indent:0 exp:0
import java.util.List; //indent:0 exp:0
import java.util.Map; //indent:0 exp:0
import java.util.StringJoiner; //indent:0 exp:0
import java.util.stream.Collector; //indent:0 exp:0
import java.util.stream.Collectors; //indent:0 exp:0
import java.util.stream.IntStream; //indent:0 exp:0
import java.util.stream.Stream; //indent:0 exp:0

public class InputLambda1 { //indent:0 exp:0

  interface Printer //indent:2 exp:2
  { //indent:2 exp:2
    void print(String s); //indent:4 exp:4
  } //indent:2 exp:2

  class LongTypeName { //indent:2 exp:2
  } //indent:2 exp:2

  interface SomeInterface { //indent:2 exp:2
    void someFunction(LongTypeName arg); //indent:4 exp:4
  } //indent:2 exp:2

  void function1(Runnable x) { //indent:2 exp:2
    Runnable r1 = () -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }; //indent:4 exp:4

    Runnable r2 = () -> { x.run(); }; //indent:4 exp:4

    Runnable r3 = () -> //indent:4 exp:4
        x.run(); //indent:8 exp:8

    Runnable r4 = () -> x.run(); //indent:4 exp:4

    Printer r5 = s -> System.out.print(s); //indent:4 exp:4

    Printer r6 = s -> System.out //indent:4 exp:4
        .print(s); //indent:8 exp:8

    Runnable r7 = () //indent:4 exp:4
        -> //indent:8 exp:8
     { //indent:5 exp:4 warn
     }; //indent:5 exp:4 warn

    Runnable r8 = //indent:4 exp:4
         () //indent:9 exp:8 warn
           -> //indent:11 exp:12 warn
         {}; //indent:9 exp:8 warn

    Runnable r9 = //indent:4 exp:4
        () //indent:8 exp:8
            -> //indent:12 exp:12
        {}; //indent:8 exp:8

    Object o = new Thread(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }); //indent:4 exp:4

    Runnable r01 = () -> { //indent:4 exp:4
       x.run(); //indent:7 exp:6 warn
     }; //indent:5 exp:4 warn

    Runnable r11 = //indent:4 exp:4
        () -> { //indent:8 exp:8
          x.run(); //indent:10 exp:10
        }; //indent:8 exp:8

    Runnable r21 = //indent:4 exp:4
        () -> { x.run(); }; //indent:8 exp:8

    Runnable r31 = //indent:4 exp:4
        () -> x //indent:8 exp:8
            .run(); //indent:12 exp:12

    Runnable r41 = //indent:4 exp:4
        () -> x.run(); //indent:8 exp:8

    Printer r51 = //indent:4 exp:4
        s -> System.out.print(s); //indent:8 exp:8

    Printer r61 = //indent:4 exp:4
        s -> System.out //indent:8 exp:8
            .print(s); //indent:12 exp:12

    Object o1 = new Thread( //indent:4 exp:4
        () -> { //indent:8 exp:8
          x.run(); //indent:10 exp:10
        }); //indent:8 exp:8

    Object o2 = new Thread(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }).toString(); //indent:4 exp:4

    SomeInterface i1 = (LongTypeName //indent:4 exp:4
        arg) -> { //indent:8 exp:8
      System.out.print(arg.toString()); //indent:6 exp:6
    }; //indent:4 exp:4

    Printer[] manyRunnable = new Printer[]{ //indent:4 exp:4
        s -> System.out.print(s), //indent:8 exp:6,8
        s -> { System.out.print(s); }, //indent:8 exp:6,8
        s -> System.out //indent:8 exp:6,8
            .print(s), //indent:12 exp:12
        s -> { //indent:8 exp:6,8
          System.out.print(s); //indent:10 exp:10
        }, //indent:8 exp:8
    }; //indent:4 exp:4
  } //indent:2 exp:2

  void function3(Runnable x) { //indent:2 exp:2
    function1(() -> { //indent:4 exp:4
      x.run(); //indent:6 exp:6
    }); //indent:4 exp:4
  } //indent:2 exp:2

  class Person { //indent:2 exp:2
    String name; //indent:4 exp:4
    int age; //indent:4 exp:4
    Person(String name, int age) { //indent:4 exp:4
    } //indent:4 exp:4
  } //indent:2 exp:2

  class Foo { //indent:2 exp:2
    String name; //indent:4 exp:4
    List<Bar> bars = new ArrayList<>(); //indent:4 exp:4

    Foo(String name) { //indent:4 exp:4
      this.name = name; //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  class Bar { //indent:2 exp:2
    String name; //indent:4 exp:4
    Bar(String name) { //indent:4 exp:4
      this.name = name; //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  public void f() { //indent:2 exp:2
    Stream.of("d2", "a2", "b1", "b3", "c") //indent:4 exp:4
        .map(s -> { //indent:8 exp:8
          System.out.println("map: " + s); //indent:10 exp:10
          return s.toUpperCase(); //indent:10 exp:10
        }) //indent:8 exp:8
        .anyMatch(s -> { //indent:8 exp:8
          System.out.println("anyMatch: " + s); //indent:10 exp:10
          return s.startsWith("A"); //indent:10 exp:10
        }); //indent:8 exp:8

    List<Person> persons = null; //indent:4 exp:4

    Map<Integer, List<Person>> personsByAge = persons //indent:4 exp:4
        .stream() //indent:8 exp:8
        .collect(Collectors.groupingBy(p -> p.age)); //indent:8 exp:8

    personsByAge //indent:4 exp:4
        .forEach((age, p) -> System.out.format("age %s: %s\n", age, p)); //indent:8 exp:8

    Collector<Person, StringJoiner, String> personNameCollector = //indent:4 exp:4
        Collector.of( //indent:8 exp:8
            () -> new StringJoiner(" | "), //indent:12 exp:12
            (j, p) -> j.add(p.name.toUpperCase()), //indent:12 exp:12
            (j1, j2) -> j1.merge(j2), //indent:12 exp:12
            StringJoiner::toString); //indent:12 exp:12

    List<Foo> foos = new ArrayList<>(); //indent:4 exp:4

    foos.forEach(f -> //indent:4 exp:4
        IntStream //indent:8 exp:8
            .range(1, 4) //indent:12 exp:12
            .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name)))); //indent:12 exp:12

    Stream.of("d2", "a2", "b1", "b3", "c") //indent:4 exp:4
        .filter(s -> { //indent:8 exp:8
         System.out.println("filter: " + s); //indent:9 exp:10 warn
           return s.startsWith("a"); //indent:11 exp:10 warn
        }) //indent:8 exp:8
        .map(s -> { //indent:8 exp:8
          System.out.println("map: " + s); //indent:10 exp:10
          return s.toUpperCase(); //indent:10 exp:10
       }) //indent:7 exp:8 warn
        .forEach(s -> //indent:8 exp:8
            System.out.println("forEach: " + s)); //indent:12 exp:12

    IntStream.range(1, 4) //indent:4 exp:4
        .mapToObj(i -> new Foo("Foo" + i)) //indent:8 exp:8
        .peek(f -> IntStream.range(1, 4) //indent:8 exp:8
            .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name)) //indent:12 exp:12
            .forEach(f.bars::add)) //indent:12 exp:12
        .flatMap(f -> f.bars.stream()) //indent:8 exp:8
        .forEach(b -> System.out.println(b.name)); //indent:8 exp:8

    IntStream.range(1, 4) //indent:4 exp:4
        .mapToObj(i -> new Foo("Foo" + i)) //indent:8 exp:8
        .peek(f -> IntStream.range(1, 4) //indent:8 exp:8
            .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name)) //indent:12 exp:12
            .forEach(f.bars::add)) //indent:12 exp:12
        .flatMap(f -> f.bars.stream()) //indent:8 exp:8
        .forEach(b -> System.out.println(b.name)); //indent:8 exp:8
  } //indent:2 exp:2

  Runnable r2r(Runnable x) { //indent:2 exp:2
    return x; //indent:4 exp:4
  } //indent:2 exp:2

  void function2(Runnable x) { //indent:2 exp:2
    Runnable r0 = r2r(() -> { //indent:4 exp:4
      int i = 1; //indent:6 exp:6
    }); //indent:4 exp:4

    Runnable r1 = r2r(() -> { //indent:4 exp:4
          int i = 1; //indent:10 exp:10
        } //indent:8 exp:8
    ); //indent:4 exp:4

    Runnable r2 = r2r(r2r(() -> { //indent:4 exp:4
              int i = 1; //indent:14 exp:14
            } //indent:12 exp:12
        ) //indent:8 exp:8
    ); //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
