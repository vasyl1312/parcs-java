import parcs.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class WilsonTestApplication {

    public static void main(String[] args) throws Exception {

        String inputFile = System.getenv("INPUT_FILE");
        String outputFile = System.getenv("OUTPUT_FILE");
        String workersNumberString = System.getenv("WORKERS_NUMBER");

        task task = new task();
        task.addJarFile("WilsonTest.jar");

        String fileName = task.findFile(inputFile);
        int rangeStart = readRangeStart(fileName);
        int rangeEnd = readRangeEnd(fileName);

        System.out.println("Range: [" + rangeStart + ", " + rangeEnd + "]");

        int workersNumber = Integer.parseInt(workersNumberString);
        int chunkSize = (rangeEnd - rangeStart + 1) / workersNumber;

        AMInfo amInfo = new AMInfo(task, null);

        point[] points = new point[workersNumber];
        channel[] channels = new channel[workersNumber];

        for (int i = 0; i < workersNumber; i++) {
            points[i] = amInfo.createPoint();
            channels[i] = points[i].createChannel();
            points[i].execute("WilsonTest");

            int startIndex = rangeStart + i * chunkSize;
            int endIndex = (i == workersNumber - 1) ? rangeEnd : startIndex + chunkSize - 1;

            System.out.println("Worker #" + i + ": processing the range [" + startIndex + ", " + endIndex + "].");

            ArrayList<Integer> data = new ArrayList<>();
            data.add(startIndex);
            data.add(endIndex);

            channels[i].write(data);
        }

        StringBuilder finalResult = new StringBuilder();

        System.out.println("Starting timer...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < workersNumber; i++) {
            System.out.println("Running worker #" + i + "...");
            boolean[] result = (boolean[]) channels[i].readObject();
            for (int j = 0; j < result.length; j++) {
                boolean isPrime = result[j];
                finalResult.append(rangeStart + i * chunkSize + j);
                finalResult.append(isPrime ? " is prime\n" : " is not prime\n");
            }
        }

        writeOutputData(outputFile, finalResult.toString());
        System.out.println("Finished.");

        long finish = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (double) (finish - start) / 1000 + " seconds.");

        task.end();
    }

    private static void writeOutputData(String filename, String data) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(data);
            System.out.println("Output: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int readRangeStart(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        int start = Integer.parseInt(sc.nextLine());
        sc.close();
        return start;
    }

    private static int readRangeEnd(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        sc.nextLine(); // Skip the first line
        int end = Integer.parseInt(sc.nextLine());
        sc.close();
        return end;
    }
}
