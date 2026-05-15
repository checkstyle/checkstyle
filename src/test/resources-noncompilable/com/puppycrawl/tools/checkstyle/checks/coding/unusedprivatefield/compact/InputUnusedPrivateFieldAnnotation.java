/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = Inject

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield.compact;

@interface Inject {}

@Inject
private Object service;

void main() { }
