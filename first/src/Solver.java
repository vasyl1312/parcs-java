import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private String inputFileName;
    private String outputFileName;
    private List<Worker> workers;

    public Solver(List<Worker> workers, String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.workers = workers;
    }

    public void solve() throws IOException {
        List<Integer> arrayNumbers = readInput();
        int step = arrayNumbers.size() / workers.size();
        int addedAmount = 0;

        List<List<Integer>> mapped = new ArrayList<>();
        for (int i = 0; i < workers.size(); i++) {
            int start = i * step;
            int end = Math.min(start + step, arrayNumbers.size());
            mapped.add(workers.get(i).mymap(arrayNumbers.subList(start, end)));
        }

        if (addedAmount != arrayNumbers.size()) {
            mapped.add(workers.get(workers.size() - 1).mymap(arrayNumbers.subList(arrayNumbers.size() - 1, arrayNumbers.size())));
        }

        List<Integer> reduced = myreduce(mapped);
        writeOutput(reduced);
    }

    public static List<Integer> mymap(List<Integer> arr) {
        List<Integer> result = new ArrayList<>();
        for (int number : arr) {
            if (factorial(number - 1).add(BigInteger.ONE).mod(BigInteger.valueOf(number)).equals(BigInteger.ZERO)) {
                result.add(number);
            }
        }
        return result;
    }

    public static List<Integer> myreduce(List<List<Integer>> mapped) {
        List<Integer> toReturn = new ArrayList<>();
        for (List<Integer> numList : mapped) {
            toReturn.addAll(numList);
        }
        return toReturn;
    }

    public List<Integer> readInput() throws IOException {
        List<Integer> array = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));
        String line;
        while ((line = br.readLine()) != null) {
            array.add(Integer.parseInt(line));
        }
        br.close();
        return array;
    }

    public void writeOutput(List<Integer> output) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName, true));
        bw.write(output.toString());
        bw.newLine();
        bw.close();
    }

    private static BigInteger factorial(int n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        // Example usage
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            workers.add(new Worker());
        }
        Solver solver = new Solver(workers, "input.txt", "output.txt");
        solver.solve();
    }
}

class Worker {
    public List<Integer> mymap(List<Integer> arr) {
        return Solver.mymap(arr);
    }
}
