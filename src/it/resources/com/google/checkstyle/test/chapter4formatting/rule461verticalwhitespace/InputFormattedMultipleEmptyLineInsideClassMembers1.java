package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

/** This file contains correct vertical whitespace inside class members i.e enums and interface. */
public class InputFormattedMultipleEmptyLineInsideClassMembers1 {

  enum Status {
    STARTED,

    RUNNING,

    FINISHED
  }

  interface Service {

    void execute();

    default void log() {

      helper();
    }

    static void printInfo() {

      System.out.println("Service info 1");

      System.out.println("Service info 2");
    }

    private void helper() {

      System.out.println("Helper logic 1");

      System.out.println("Helper logic 2");
    }
  }
}
