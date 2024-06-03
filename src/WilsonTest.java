import parcs.*;

import java.math.BigInteger;
import java.util.ArrayList;

public class WilsonTest implements AM {

    public void run(AMInfo info) {
        System.out.println("From worker");
        ArrayList<Integer> data = (ArrayList<Integer>) info.parent.readObject();
        int n = data.get(0);

        System.out.println("Received n = " + n);

        boolean result = wilsonTest(n);

        System.out.println("Process completed.");

        info.parent.write(result);
    }

    static boolean wilsonTest(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;

        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        BigInteger leftSide = factorial.add(BigInteger.ONE);
        BigInteger rightSide = BigInteger.valueOf(n).pow(n - 1);

        return leftSide.mod(BigInteger.valueOf(n)).equals(BigInteger.ZERO) && rightSide.mod(BigInteger.valueOf(n)).equals(BigInteger.ZERO);
    }
}
