import java.util.function.Supplier;

public class InputLambdaTest17{

    void initPartialTraversalState() {
            SpinedBuffer<P_OUT> b = new SpinedBuffer<>();
            
        P_OUT spliterator = new P_OUT();
        Supplier pusher = () -> spliterator.tryAdvance(b);
        }

    private class P_OUT
    {

        public Object tryAdvance(SpinedBuffer<P_OUT> b)
        {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class SpinedBuffer<T>
    {

    }

}