import parcs.*;

import java.util.ArrayList;

public class WilsonTest implements AM {

    public void run(AMInfo info) {
        System.out.println("From worker");
        ArrayList<Integer> data = (ArrayList<Integer>) info.parent.readObject();
        int rangeStart = data.get(0);
        int rangeEnd = data.get(1);

        System.out.println("Received range: [" + rangeStart + ", " + rangeEnd + "]");

        boolean[] results = new boolean[rangeEnd - rangeStart + 1];

        for (int i = rangeStart; i <= rangeEnd; i++) {
            results[i - rangeStart] = wilsonTest(i);
        }

        System.out.println("Process completed.");

        info.parent.write(results);
    }

    static boolean wilsonTest(int n) {
        if (n <= 1) return false;

        long factorial = 1;
        for (int i = 2; i < n; i++) {
            factorial = (factorial * i) % n;
        }

        return (factorial == n - 1);
    }
}
