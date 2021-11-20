/*
FinalClass


*/

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

public enum InputFinalClassEnum { // ok

    MALE("M", "gender.male"),
    FEMALE("F", "gender.female");

    private String initial;
    private String i18nCode;

    private InputFinalClassEnum(String initial, String i18nCode) {
          this.initial = initial;
          this.i18nCode = i18nCode;
      }

    public String getName() {
        return name();
    }

    public String getInitial() {
        return initial;
    }

    public String getI18nCode() {
        return i18nCode;
    }

}

