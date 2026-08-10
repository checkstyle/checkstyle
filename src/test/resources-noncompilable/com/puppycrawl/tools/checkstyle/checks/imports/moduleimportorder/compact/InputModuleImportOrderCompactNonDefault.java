/*
ModuleImportOrder
option = bottom
separated = true


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
import module java.desktop; // violation ''java.desktop' should be separated from previous imports.'
import module java.sql;

void main() {
    List<String> list = List.of();
}
