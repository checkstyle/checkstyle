package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.lang.String; // VIOLATION
import java.lang.Math; // VIOLATION
import java.lang.Class; // VIOLATION
import java.lang.Exception; // VIOLATION
import java.lang.Runnable; // VIOLATION
import java.lang.RuntimeException; // VIOLATION
import java.lang.ProcessBuilder; // VIOLATION
import java.lang.Double; // VIOLATION
import java.lang.Integer; // VIOLATION
import java.lang.Float; // VIOLATION
import java.lang.Short; // VIOLATION

import java.lang.annotation.Annotation; // OK
import java.lang.reflect.Modifier; // OK
import java.lang.reflect.Field; // OK

import java.lang.*; // OK

public class InputUnusedImportsFromJavaLang {
    private static final String SOMETHING = "a string";
    private static final double PI =  Math.PI;

    private Class clazz = this.getClass();
    private Exception ex = new RuntimeException();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private ProcessBuilder processBuilder = new ProcessBuilder();
    private int modifier = Modifier.fieldModifiers();
    private Field field;
    private Annotation annotation;

    public static void main(String[] args) {
        Double d = new Double(0.0d);
        Float f = new Float(0.1f);
        Integer i = new Integer(1);
        Short s = Short.MIN_VALUE;
    }
}
