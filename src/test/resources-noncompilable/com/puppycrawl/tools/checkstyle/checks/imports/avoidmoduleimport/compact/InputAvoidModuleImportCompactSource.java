/*
AvoidModuleImport
excludes = java.base
maxAllowedModuleImports = 1

*/

// non-compiled with javac: Compilable with Java25

import module java.sql;
import module java.base;

void doSomething() {
    List<String> names = new ArrayList<>();
    names.add("foo");
    names.add("bar");

    Map<String, Integer> counts = new HashMap<>();
    counts.put("foo", 42);

    System.out.println(names + " " + counts);
}

void main() {
    doSomething();
}
