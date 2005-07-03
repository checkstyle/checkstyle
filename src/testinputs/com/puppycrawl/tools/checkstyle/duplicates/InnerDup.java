public class InnerDup
{
    int x = 0;
    
    public void m1()
    {
        x += 1;
        x += 2;
        x += 3;
        x += 4;
        x += 5;
        x += 6;
        x += 7;
        x += 8;
        x += 9;
        x += 10;
        x += 11;
        x += 12;
    }

    public void m2()
    {
        x += 1;
        x += 2;
        x += 3;
        x += 4;
        x += 5;
        x += 6;
        x += 7;
        x += 8;
        x += 9;
        x += 10;
        x += 11;
        x += 12;
        x += 13;
        x += 14;
    }

    public void m3()
    {
        x += 1;
        x += 2;
        x += 3;
        x += 4;
        x += 5;
        x += 6;
        x += 7;
        x += 8;
        x += 9;
        x += 10;
    }

}