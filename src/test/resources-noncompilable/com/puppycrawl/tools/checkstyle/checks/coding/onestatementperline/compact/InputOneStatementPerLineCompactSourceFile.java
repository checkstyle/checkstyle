/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

// non-compiled with javac: Compilable with Java25

import java.util.List; import java.util.Map; // violation 'Only one statement per line allowed.'

int field1 = 1; int field2 = 2; // violation 'Only one statement per line allowed.'

int field3 = 3;
int field4 = 4;

void helper() { } int field5 = 5; // violation 'Only one statement per line allowed.'

class Nested { } class Nested2 { } // violation 'Only one statement per line allowed.'

interface Foo { } record Rec(int x) { } // violation 'Only one statement per line allowed.'

void main() {
    int a = 1; int b = 2; // violation 'Only one statement per line allowed.'
    a++; b++; // violation 'Only one statement per line allowed.'
    ;;
    List<Integer> ints = List.of(a, b);
    ints.forEach(t -> { int m = t; });
    // violation below 'Only one statement per line allowed.'
    ints.forEach(t -> { int m = t; int n = t; });
    Runnable runnable = new Runnable() { public void run() { int q = 1; int w = 2; } };
    // violation above 'Only one statement per line allowed.'
    Map<String, String> map = Map.of();
    // violation below 'Only one statement per line allowed.'
    runnable.run(); System.out.println(map.size() + field1 + field2 + field3 + field4 + field5);
}
