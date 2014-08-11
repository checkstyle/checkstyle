public abstract class CronExpression<E> {

  private static final String SUPPORTS_REMOVE = "";

@CollectionFeature.Require(absent = SUPPORTS_REMOVE)
  public void testSetCount_negative_removeUnsupported() {
  }
}

@interface CollectionFeature {

    public @interface Require
    {
        String absent();
    }
}
