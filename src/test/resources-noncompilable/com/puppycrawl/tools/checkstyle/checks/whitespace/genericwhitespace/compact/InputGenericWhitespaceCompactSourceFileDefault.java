/*
GenericWhitespace


*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

interface Marker {
}

class Box<E extends Enum<E>& Marker> { // violation ''&' is not preceded with whitespace.'
}

<T> List<T> goodDefinition(T value) {
    return null;
}

static<T>List<T> badDefinition(T value) {
// 2 violations above:
//  ''<' is not preceded with whitespace.'
//  ''>' is followed by an illegal character.'
    return null;
}

void checkLocals() {
    List<String> good = new ArrayList<>();
    Map<String, Integer> counts = null;
    List<List<Integer>> nested = null;
    List <String> spacedBefore = null; // violation ''<' is preceded with whitespace.'
    List< String> spacedAfter = null; // violation ''<' is followed by whitespace.'
    List<String > spacedEnd = null; // violation ''>' is preceded with whitespace.'
    List<String>joined = null; // violation ''>' is followed by an illegal character.'
    List<String> witnessed = Collections.<String> emptyList();
    // violation above ''>' is followed by whitespace.'
    System.out.println(good.size() + counts.size() + nested.size());
    System.out.println(spacedEnd.size() + joined.size() + witnessed.size());
}

void main() {
}
