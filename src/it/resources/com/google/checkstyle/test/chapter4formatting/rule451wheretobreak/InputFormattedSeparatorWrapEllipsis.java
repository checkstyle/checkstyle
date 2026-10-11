package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

class InputFormattedSeparatorWrapEllipsis {

  public void testMethodWithGoodWrapping(String... parameters) {}

  public void testMethodWithBadWrapping(String... parameters) {}
}
