public class InputLambdaTest17{

    void initPartialTraversalState() {
            SpinedBuffer<P_OUT> b = new SpinedBuffer<>();
            buffer = b;
            
            pusher = () -> spliterator.tryAdvance(bufferSink);
        }
}