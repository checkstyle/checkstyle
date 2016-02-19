package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

public abstract class InputInvalidThrowsIndent2 { //indent:0 exp:0
 public void m1() throws Exception { //indent:1 exp:1
 } //indent:1 exp:1
 public void m2() throws //indent:1 exp:1
Exception { //indent:0 exp:6 warn
 } //indent:1 exp:1
 public void m3() throws Exception, //indent:1 exp:1
NullPointerException { //indent:0 exp:6 warn
 } //indent:1 exp:1
 public void m4() //indent:1 exp:1
throws Exception { //indent:0 exp:6 warn
 } //indent:1 exp:1
 public abstract void m5() //indent:1 exp:1
throws Exception; //indent:0 exp:6 warn
 public void m6() //indent:1 exp:1
throws //indent:0 exp:6 warn
Exception { //indent:0 exp:6 warn
 } //indent:1 exp:1
 public void m7() //indent:1 exp:1
throws //indent:0 exp:6 warn
Exception, //indent:0 exp:6 warn
NullPointerException { //indent:0 exp:6 warn
 } //indent:1 exp:1
 double[] m8() //indent:1 exp:1
throws //indent:0 exp:6 warn
Exception { return null; //indent:0 exp:6 warn
 } //indent:1 exp:1
 public InputInvalidThrowsIndent2() //indent:1 exp:1
throws Exception {//indent:0 exp:6 warn
 } //indent:1 exp:1
 @TestAnnotation //indent:1 exp:1
 public //indent:1 exp:1
    static //indent:4 exp:4
    void m9() //indent:4 exp:4
throws Exception {} //indent:0 exp:6 warn
} //indent:0 exp:0

@interface TestAnnotation {} //indent:0 exp:0