public class InputLambdaTest19 {

 static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? true
                : object -> targetRef.equals(object);
    }
}