package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

@interface InputMethodCount4 {
  Object object = new Object(){
    @Override
    public String toString() {
      return new String();
    }
  };
}
