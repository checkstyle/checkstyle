// non-compiled with javac: reference to non existent modules and packages

@Deprecated
@SuppressWarnings("CheckReturnValue")
module com.example.hello {
  requires java.sql;
}
