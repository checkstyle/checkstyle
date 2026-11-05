/*
ExplicitInitialization
onlyObjectReferences = (default)false

*/

// non-compiled with javac: Compilable with Java25

int number = 0; // violation ''number' explicitly initialized'
Object reference = null; // violation ''reference' explicitly initialized'
boolean flag = false; // violation ''flag' explicitly initialized'
char symbol = '\0'; // violation ''symbol' explicitly initialized'
double ratio = 0.0; // violation ''ratio' explicitly initialized'
int nonDefault = 1;
String text = "value";
final int constant = 0;
int unassigned;

void main() {
    int local = 0;
}
