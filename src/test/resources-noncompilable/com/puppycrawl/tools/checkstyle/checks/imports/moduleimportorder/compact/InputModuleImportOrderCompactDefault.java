/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;

void main() {
    List<String> list = List.of();
}
