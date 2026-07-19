/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF,ENUM_CONSTANT_DEF,ENUM_DEF,INTERFACE_DEF,ANNOTATION_DEF,METHOD_DEF, \
          RECORD_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

@interface InputMethodCount4 {
  Object object = new Object(){
    @Override
    public String toString() {
      return new String();
    }
  };
}
