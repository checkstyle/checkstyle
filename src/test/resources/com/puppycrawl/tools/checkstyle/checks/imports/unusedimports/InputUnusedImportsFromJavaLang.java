/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.lang.String; // violation 'Unused import - java.lang.String.'
import java.lang.Math; // violation 'Unused import - java.lang.Math.'
import java.lang.Class; // violation 'Unused import - java.lang.Class.'
import java.lang.Exception; // violation 'Unused import - java.lang.Exception.'
import java.lang.Runnable; // violation 'Unused import - java.lang.Runnable.'
import java.lang.RuntimeException; // violation 'Unused import - java.lang.RuntimeException.'
import java.lang.ProcessBuilder; // violation 'Unused import - java.lang.ProcessBuilder.'
import java.lang.Double; // violation 'Unused import - java.lang.Double.'
import java.lang.Integer; // violation 'Unused import - java.lang.Integer.'
import java.lang.Float; // violation 'Unused import - java.lang.Float.'
import java.lang.Short; // violation 'Unused import - java.lang.Short.'

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

import java.lang.*;

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
