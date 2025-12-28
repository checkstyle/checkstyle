/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java25

import module java.base;
import static java.util.Set.*;

private int base = 10;
private int delta = 1;
private String tag = "run";

int compute(int x) {
    return x + base; // violation 'Reference to instance variable 'base' needs "this.".'
}

void bump() {
    delta++; // violation 'Reference to instance variable 'delta' needs "this.".'
}

String format(int value) {
    return tag + ":" + value; // violation 'Reference to instance variable 'tag' needs "this.".'
}

void setBase(int base) {
    base = base + 1;
    this.base = base;
}

String normalize(String tag) {
    tag = tag.strip();
    return tag;
}

static int clamp(int v) {
    return Math.max(0, v);
}

void main() {
    int local = base; // violation 'Reference to instance variable 'base' needs "this.".'
    local += compute(local); // violation 'Method call to 'compute' needs "this.".'
    bump(); // violation 'Method call to 'bump' needs "this.".'
    local += this.compute(local);
    this.bump();

    delta = local; // violation 'Reference to instance variable 'delta' needs "this.".'
    this.delta = clamp(local);

    String out = format(local); // violation 'Method call to 'format' needs "this.".'
    out = this.format(local);

    String norm = normalize("  x  "); // violation 'Method call to 'normalize' needs "this.".'
    norm = this.normalize("  y  ");
    IO.println(out + norm);
}
