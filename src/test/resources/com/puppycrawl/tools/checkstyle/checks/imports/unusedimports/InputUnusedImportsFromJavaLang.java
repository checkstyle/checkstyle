/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.lang.String; // violation
import java.lang.Math; // violation
import java.lang.Class; // violation
import java.lang.Exception; // violation
import java.lang.Runnable; // violation
import java.lang.RuntimeException; // violation
import java.lang.ProcessBuilder; // violation
import java.lang.Double; // violation
import java.lang.Integer; // violation
import java.lang.Float; // violation
import java.lang.Short; // violation

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
