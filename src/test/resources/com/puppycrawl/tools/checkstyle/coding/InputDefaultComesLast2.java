package com.puppycrawl.tools.checkstyle.coding;

public interface JsonTranslator {

    String toJson(Response one, Response two, Response three);

    String toJson(Document document);

    default String toJson(Response one) {
      return toJson(one, one, one);
    }

    default String toJson(Response one, Response two) {
      return toJson(one, one, two);
    }
  }