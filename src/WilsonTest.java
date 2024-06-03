import parcs.*;

import java.math.BigInteger;
import java.util.ArrayList;

public class WilsonTest implements AM {

    public void run(AMInfo info) {
        System.out.println("From worker");
        ArrayList<Integer> data = (ArrayList<Integer>) info.parent.readObject();
        int n = data.get(0);

        System.out.println("Received n = " + n);

        boolean[] results = new boolean[data.size() - 1];

        System.out.println("Result size is = " + results.length);

        for (int i = 1; i < data.size(); i++) {
            results[i - 1] = isWilsonPrime(data.get(i));
        }

        System.out.println("Process completed.");

        info.parent.write(results);
    }

    static long factorial(int n) {
        if (n == 0 || n == 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    static boolean isWilsonPrime(int p) {
        if (p <= 1) return false;
        if (p == 2) return true;

        long wilsonCriterion = factorial(p - 1) + 1;

        return wilsonCriterion % p == 0;
    }
}