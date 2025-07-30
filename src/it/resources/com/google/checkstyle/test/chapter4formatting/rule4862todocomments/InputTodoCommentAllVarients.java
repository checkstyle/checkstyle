package com.google.checkstyle.test.chapter4formatting.rule4862todocomments;

/** some javadoc. */
public class InputTodoCommentAllVarients {

  void myFunc1(int i) {
    // todo: implementing
    // ok above until #17501
  }

  void myFunc2(int i) {
    // TodO: implementing
    // ok above until #17501
  }

  void myFunc3(int i) {
    // toDo: implementing
    // ok above until #17501
  }

  void myFunc4(int i) {
    // toDO: implementing
    // ok above until #17501
  }

  void myFunc5(int i) {
    // TOdo: implementing
    // ok above until #17501
  }

  void myFunc6(int i) {
    // tOdO: implementing
    // ok above until #17501
  }

  void myFunc7(int i) {
    // tODO: implementing
    // ok above until #17501
  }

  void myFunc8(int i) {
    // tODO: implementing
    // ok above until #17501
  }

  void myFunc9(int i) {
    // Todo: implementing
    // ok above until #17501
  }

  void myFunc10(int i) {
    // TodO: implementing
    // ok above until #17501
  }

  void myFunc11(int i) {
    // ToDo: implementing
    // ok above until #17501
  }

  void myFunc12(int i) {
    // TODo: implementing
    // ok above until #17501
  }

  void myFunc13(int i) {
    // TOdO: implementing
    // ok above until #17501
  }

  void myFunc14(int i) {
    // TODo: implementing
    // ok above until #17501
  }

  void myFunc15(int i) {
    // tODo: implementing
    // ok above until #17501
  }
  void myFunc16(int i) {
    // TODO: implementing
  }
}
