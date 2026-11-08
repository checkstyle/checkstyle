/*
IllegalImport
illegalPkgs = java.io
illegalClasses = java.util.Random
illegalModules = java.logging
regexp = true


*/

// non-compiled with javac: Compilable with Java25

import module java.logging; // violation 'Illegal import - java.logging'
import module java.sql;

import java.io.File; // violation 'Illegal import - java.io.File'
import java.util.List;
import java.util.Random; // violation 'Illegal import - java.util.Random'

void main() {
}

int size(List<String> values) {
    return values.size();
}

boolean exists(String path) {
    return new File(path).exists();
}

int roll() {
    return new Random().nextInt(6);
}
