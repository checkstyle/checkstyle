/*
UnnecessaryFullyQualifiedType


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;

java.util.List<String> names = List.of(); // violation 'Unnecessary .* - java.util.List.'

java.lang.String text = ""; // violation 'Unnecessary .* - java.lang.String.'

java.util.Set<String> keys = null; // violation 'Unnecessary .* - java.util.Set.'

// a locally declared Map clashes, so java.util.Map needs qualification
java.util.Map<String, String> map = null;

Map local = new Map();

java.util.Map.Entry<String, String> entry = null;

@java.lang.Deprecated
void main() {
    java.util.Optional<String> value = java.util.Optional.empty();
    // violation above 'Unnecessary .* - java.util.Optional.'
}

class Map {
}

record Point(java.math.BigDecimal x) { // violation 'Unnecessary .* - java.math.BigDecimal.'
}
