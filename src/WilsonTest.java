import parcs.*;

import java.util.ArrayList;

public class WilsonTest implements AM {

    public void run(AMInfo info) {
        System.out.println("From worker");
        ArrayList<Integer> data = (ArrayList<Integer>) info.parent.readObject();

        if (data.size() < 3) {
            System.out.println("Invalid data received");
            return;
        }

        int k = data.get(0);
        int start = data.get(1);
        int end = data.get(2);

        System.out.println("Received range: " + start + " to " + end);

        boolean[] results = new boolean[end - start + 1];

        for (int i = start; i <= end; i++) {
            results[i - start] = wilsonTest(i);
        }

        System.out.println("Process completed.");

        info.parent.write(results);
    }

    static boolean wilsonTest(int n) {
        if (n <= 1) return false;
        return factorial(n - 1) % n == n - 1;
    }

    static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
