/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = false
violateImpliedStaticOnNestedInterface = false
violateImpliedStaticOnNestedRecord = false

*/

// non-compiled with javac: Compilable with Java25

enum Color { RED, GREEN, BLUE }
interface Printable { }
record Point(int x, int y) { }

void main() { }
