class Foo {

    /**
     * This class implements the GWT serialization of {@link HashMultimap}.
     * 
     * @author Jord Sonneveld
     * 
     */
  public static <T extends Enum<T>> Function<String, T> valueOfFunction(
      Class<T> enumClass) {
    return new ValueOfFunction<T>(enumClass);
  }
}