package zmz.netdisk;

import org.junit.jupiter.api.Test;

public class TestInteger
{
    private static int x = 10;

    private static Integer y = -300;



    public static void updateX(int value) {

        value = 3 * value;

    }



    public static void updateY(Integer value) {

        value = 3 * value;

    }


    @Test
    public void test01()
    {

        updateX(x);
        updateY(y);
        print();
    }

    public void print()
    {
        System.out.println(x + "\t" + y);
    }

    public int a()
    {
        return 10;
    }


}
