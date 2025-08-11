package com.google.checkstyle.test.chapter4formatting.rule4862todocomments;

/** some javadoc. */
public class InputTodoCommentAllVariants {

  void myFunc1(int i) {
    // todo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc2(int i) {
    // TodO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc3(int i) {
    // toDo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc4(int i) {
    // toDO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc5(int i) {
    // TOdo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc6(int i) {
    // tOdO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc7(int i) {
    // tODO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc8(int i) {
    // tODO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc9(int i) {
    // Todo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc10(int i) {
    // TodO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc11(int i) {
    // ToDo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc12(int i) {
    // TODo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc13(int i) {
    // TOdO: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc14(int i) {
    // TODo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc15(int i) {
    // tODo: implementing
    // violation above 'Comments must use 'TODO' in all caps'
  }

  void myFunc16(int i) {
    // TODO: implementing
  }
}
