/*
IllegalInstantiation
classes = (default)
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF,RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
Boolean flag = new Boolean(true);

List<String> names = new ArrayList<>();

List<String> full = new java.util.ArrayList<>();

StringBuilder builder = new StringBuilder();

void main() {
}

List<Integer> numbers() {
    return new ArrayList<>();
}
