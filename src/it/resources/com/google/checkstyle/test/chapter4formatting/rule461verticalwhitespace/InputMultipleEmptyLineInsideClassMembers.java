package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

/**
 * This file contains incorrect vertical whitespace inside class
 * members and also between class members.
 */
public class InputMultipleEmptyLineInsideClassMembers {


  static int count; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'


  // violation below ''VARIABLE_DEF' has more than 1 empty lines before.'
  String str;


  // violation below ''VARIABLE_DEF' has more than 1 empty lines before.'
  int value;


  { // violation ''INSTANCE_INIT' has more than 1 empty lines before.'
    str = "Hello"; // violation 'There is more than 1 empty line after this line.'


  }


  { // violation ''INSTANCE_INIT' has more than 1 empty lines before.'
    value = 10; // violation 'There is more than 1 empty line after this line.'


  }


  InputMultipleEmptyLineInsideClassMembers() {
    // violation above ''CTOR_DEF' has more than 1 empty lines before.'
    int a; // violation 'There is more than 1 empty line after this line.'


    int b; // violation 'There is more than 1 empty line after this line.'


  }


  static { // violation ''STATIC_INIT' has more than 1 empty lines before.'
    // violation above 'There is more than 1 empty line after this line.'


    count = 5; // violation 'There is more than 1 empty line after this line.'


  }


  static { // violation ''STATIC_INIT' has more than 1 empty lines before.'
    // violation above 'There is more than 1 empty line after this line.'


    count++; // violation 'There is more than 1 empty line after this line.'


  }

  /** Method. */
  public void foo() {

    int x = 10;
    // violation above 'There is more than 1 empty line after this line.'


    // violation below 'There is more than 1 empty line after this line.'
    int y = 20;


  }

  /** Another method. */
  public void bar() {
    // violation above 'There is more than 1 empty line after this line.'


    // violation below 'There is more than 1 empty line after this line.'
    int z = 30;


  }

  /** Main method. */
  public static void main(String[] args) {
    // violation above 'There is more than 1 empty line after this line.'


    // violation below 'There is more than 1 empty line after this line.'
    new InputMultipleEmptyLineInsideClassMembers();


  }
}
