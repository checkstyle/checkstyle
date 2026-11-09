/*
IllegalInstantiation
classes = java.lang.Boolean, java.util.ArrayList
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF,RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
Boolean flag = new Boolean(true); // violation 'Instantiation of java.lang.Boolean'

List<String> names = new ArrayList<>(); // violation 'Instantiation of java.util.ArrayList'

List<String> full = new java.util.ArrayList<>(); // violation 'Instantiation of java.util.ArrayList'

StringBuilder builder = new StringBuilder();

void main() {
}

List<Integer> numbers() {
    return new ArrayList<>(); // violation 'Instantiation of java.util.ArrayList'
}
