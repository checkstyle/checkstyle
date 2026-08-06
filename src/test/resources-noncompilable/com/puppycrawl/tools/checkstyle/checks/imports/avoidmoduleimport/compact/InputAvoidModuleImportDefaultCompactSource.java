/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = (default)0

*/

// non-compiled with javac: Compilable with Java25

import module java.sql;
// violation above 'Using the 'import module' form of import should be avoided'
import module java.base;
// violation above 'Using the 'import module' form of import should be avoided'

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
