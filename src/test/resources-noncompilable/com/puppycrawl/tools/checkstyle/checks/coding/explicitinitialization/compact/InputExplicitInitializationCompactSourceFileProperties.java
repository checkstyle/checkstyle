/*
ExplicitInitialization
onlyObjectReferences = true

*/

// non-compiled with javac: Compilable with Java25

Object reference = null; // violation ''reference' explicitly initialized'
String text = null; // violation ''text' explicitly initialized'
int number = 0;
boolean flag = false;
char symbol = '\0';
double ratio = 0.0;
int nonDefault = 1;

void main() {
}
