/*
AvoidStaticImport
excludes = (default)


*/

// non-compiled with javac: Compilable with Java25

import static javax.swing.WindowConstants.*; // violation 'Using a static member import should be avoided.'
import static java.lang.Math.PI; // violation 'Using a static member import should be avoided.'
import static java.lang.Math.sin; // violation 'Using a static member import should be avoided.'

void main() { }
