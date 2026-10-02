/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below ''private' modifier.*order.*JLS suggestions.'
static private void util() {
}

@Deprecated final void annotated() {
}

// violation below ''@Deprecated' annotation modifier.*precede non-annotation modifiers.'
final @Deprecated void wronglyAnnotated() {
}
