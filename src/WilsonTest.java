import parcs.*;

import java.math.BigInteger;
import java.util.ArrayList;

public class WilsonTest implements AM {

    public void run(AMInfo info) {
        System.out.println("From worker");
        ArrayList<Integer> data = (ArrayList<Integer>) info.parent.readObject();
        int start = data.get(0);
        int end = data.get(1);

        System.out.println("Received range: " + start + " to " + end);

        boolean[] results = new boolean[end - start + 1];

        for (int i = 0; i <= end - start; i++) {
            results[i] = wilsonTest(start + i);
        }

        System.out.println("Process completed.");

        info.parent.write(results);
    }

    static boolean wilsonTest(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i < n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial.mod(BigInteger.valueOf(n)).equals(BigInteger.valueOf(n - 1));
    }
}
