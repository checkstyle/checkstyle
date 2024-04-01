// xdoc section -- start
public class FileOne {
  public void MyMethod() {} // OK
}

public class FileTwo {
  public void MyMethod() {} // OK
}

public class FileThree {
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
