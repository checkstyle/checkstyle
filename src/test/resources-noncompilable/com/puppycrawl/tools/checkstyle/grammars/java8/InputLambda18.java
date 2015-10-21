//Compilable with Java8
import java.util.function.Predicate;
public class InputLambda19 {

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? null
                : object -> targetRef.equals(object);
    }
}
