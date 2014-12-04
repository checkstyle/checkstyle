import java.util.function.Predicate;

public class InputLambdaTest19 {

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? null
                : object -> targetRef.equals(object);
    }
}