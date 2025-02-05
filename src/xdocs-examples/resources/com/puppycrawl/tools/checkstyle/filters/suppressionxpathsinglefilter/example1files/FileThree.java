package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example1files;

public class FileThree {
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
