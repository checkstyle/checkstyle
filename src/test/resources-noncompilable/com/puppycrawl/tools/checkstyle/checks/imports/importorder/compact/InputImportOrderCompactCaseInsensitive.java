/*
ImportOrder
option = inflow
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = false
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)IMPORT,STATIC_IMPORT


*/

// non-compiled with javac: Compilable with Java25

import static java.lang.Math.max;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable; // violation 'Wrong lexicographical order for 'java.util.concurrent.Callable' import. Should be before 'java.util.Map'.'

void main() {
    List<Callable<Integer>> tasks = List.of();
    Map<String, Integer> map = Map.of();
    int size = max(tasks.size(), map.size());
}
