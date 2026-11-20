/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation 'Unused import - java.util.List.'
import java.util.Map; // violation 'Unused import - java.util.Map.'
import java.util.Set;
import java.util.ArrayList; // violation 'Unused import - java.util.ArrayList.'
import java.util.HashMap; // violation 'Unused import - java.util.HashMap.'
import java.util.HashSet;
import java.util.LinkedList; // violation 'Unused import - java.util.LinkedList.'
import java.util.Deque; // violation 'Unused import - java.util.Deque.'

public class InputUnusedImportsTypeParameter<List> {
}

class MethodTypeParameter {
    <Map> void method() {
    }
}

class BoundTypeParameter {
    <T extends Set> void method() {
    }
}

class PlainUnused {
}

class LocalClassShadow {
    void method() {
        class ArrayList {
        }
        ArrayList local = new ArrayList();
    }
}

class TypeParameterAsFieldType<HashSet> {
    HashSet field;
}

record TypeParameterRecord<LinkedList>(int x) {
}

interface TypeParameterInterface<Deque> {
}
