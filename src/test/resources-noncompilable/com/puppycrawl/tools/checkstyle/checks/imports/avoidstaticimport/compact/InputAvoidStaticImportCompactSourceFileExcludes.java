/*
AvoidStaticImport
excludes = java.lang.Math.PI,javax.swing.WindowConstants.*


*/

// non-compiled with javac: Compilable with Java25

import static javax.swing.WindowConstants.*;
import static java.lang.Math.PI;
import static java.lang.Math.sin; // violation 'Using a static member import should be avoided.'

void main() { }
