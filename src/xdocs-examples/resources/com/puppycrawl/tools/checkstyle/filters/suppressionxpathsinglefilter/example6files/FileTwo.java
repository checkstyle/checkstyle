package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example6files;

public class FileTwo {
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
