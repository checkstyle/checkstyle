// someexamples of 1.5 extensions

@interface MyAnnotation {
    String name();
}

@MyAnnotation(name = "ABC")
public class Input15Extensions
{

}

enum Enum1
{
    A, B, C;
    Enum1() {}
    public String toString() {
        return ""; //some custom implementation
    }
}
