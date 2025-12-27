package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

/**
 * This file contains incorrect vertical whitespace inside class
 * members i.e enums and interface.
 */
public class InputMultipleEmptyLineInsideClassMembers1 {

  enum Status { // false negative until #18437


    STARTED, // false negative until #18437


    RUNNING, // false negative until #18437


    FINISHED // false negative until #18437


  }

  interface Service {

    void execute();


    default void log() {
      // 2 violations above:
      // ''METHOD_DEF' has more than 1 empty lines before.'
      // 'There is more than 1 empty line after this line.'


      helper(); // violation 'There is more than 1 empty line after this line.'


    }


    static void printInfo() {
      // 2 violations above:
      // ''METHOD_DEF' has more than 1 empty lines before.'
      // 'There is more than 1 empty line after this line.'


      System.out.println("Service info 1");


      System.out.println("Service info 2"); // false positive until #18438
      // violation above 'There is more than 1 empty line after this line.'

      // violation 'There is more than 1 empty line after this line.'


    }


    private void helper() {
      // 2 violations above:
      // ''METHOD_DEF' has more than 1 empty lines before.'
      // 'There is more than 1 empty line after this line.'


      System.out.println("Helper logic 1");


      System.out.println("Helper logic 2"); // false positive until #18438
      // violation above 'There is more than 1 empty line after this line.'

      // violation 'There is more than 1 empty line after this line.'


    }
  }
}
