package com.google.checkstyle.test.chapter4formatting.rule4862todocomments;

/** Some javadoc. */
public class InputTodoCommentAllVariants {

  void myFunc1(int i) {
    // todo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc2(int i) {
    // TodO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc3(int i) {
    // toDo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc4(int i) {
    // toDO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc5(int i) {
    // TOdo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc6(int i) {
    // tOdO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc7(int i) {
    // tODO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc8(int i) {
    // tODO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc9(int i) {
    // Todo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc10(int i) {
    // TodO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc11(int i) {
    // ToDo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc12(int i) {
    // TODo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc13(int i) {
    // TOdO: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc14(int i) {
    // TODo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc15(int i) {
    // tODo: implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc16(int i) {
    // TODO: implementing
  }

  void myFunc17(int i) {
    // todo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc18(int i) {
    // TodO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc19(int i) {
    // toDo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc20(int i) {
    // toDO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc21(int i) {
    // TOdo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc22(int i) {
    // tOdO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc23(int i) {
    // tODO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc24(int i) {
    // tODO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc25(int i) {
    // Todo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc26(int i) {
    // TodO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc27(int i) {
    // ToDo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc28(int i) {
    // TODo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc29(int i) {
    // TOdO:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc30(int i) {
    // TODo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc31(int i) {
    // tODo:implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc32(int i) {
    // TODO:implementing
  }

  void myFunc33(int i) {
    // tODo implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc34(int i) {
    // TODO implementing
    // violation above ''TODO:' must be written in all caps and followed by a colon'
  }

  void myFunc35(int i) {
    // TODO: correct TODO comment
  }

  void myFunc36(int i) {
    // TODO:correct TODO comment
  }
}
