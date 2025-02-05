package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example3files;

public class FileTwo {
  public void MyMethod() {} // violation,  name 'MyMethod' must
                            //match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
