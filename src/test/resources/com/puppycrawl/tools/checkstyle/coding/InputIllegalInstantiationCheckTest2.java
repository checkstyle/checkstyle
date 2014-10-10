import java.util.function.Function;
import java.util.function.Supplier;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class InputIllegalInstantiationCheckTest2
{

    public static void main(String[] args)
    {

        Supplier<InputMethodReferencesTest2> supplier = InputMethodReferencesTest2::new;
        Supplier<InputMethodReferencesTest2> suppl = InputMethodReferencesTest2::<Integer> new;
        Function<Integer, Message[]> messageArrayFactory = Message[]::new;

    }

    private class Bar<T>
    {

    }
}
