package main;

import generating_data.RandomNumberND;
import receivers.GoBackNReceiver;
import receivers.StopAndWaitReceiver;
import senders.GoBackNSender;
import senders.StopAndWaitSender;

public class Main {

    public static void main(String[] args) {

        RandomNumberND generator = new RandomNumberND();
        StopAndWaitSender stopAndWaitSender = new StopAndWaitSender();
        StopAndWaitReceiver stopAndWaitReceiver = new StopAndWaitReceiver();
        GoBackNSender goBackNSender = new GoBackNSender();
        GoBackNReceiver goBackNReceiver = new GoBackNReceiver();
        long start;
        long end;
        long duration;
        int iterations = 100; // <-------- liczba powtorzen testu




        //512KB ->  4194304
        //1MB   ->  8388608
        //10MB  ->  83886080
        int sizeOfData = 8388608;



        //512B  ->  4096
        //1KB   ->  8192
        int sizeOfPacket = 4096;





        System.out.println("\nSymulacja przesyłania kilku pakietów kanałem komunikacyjnym.");
        System.out.println("Prawdopodobieństwo wysłania bitu przeciwnego: " + 0.001f);
        System.out.println("Rozmiar pakietu: " + sizeOfPacket + " bitów.\n");
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 1a - STOP-AND-WAIT ARQ, kanał BSC, bit parzystości:");
        System.out.println("--------------------------------------");
        System.out.println();
        // SYMULACJA 1 - STOP-AND-WAIT ARQ, KANAL BSC
        double totalDuration1a = 0;
        double totalErrors1a = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            stopAndWaitSender.setPackets(ciag, sizeOfPacket);
            stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 1);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration1a += duration;
            totalErrors1a += stopAndWaitSender.getErrors();
        }
        System.out.println("Symulacja 1a - STOP-AND-WAIT ARQ, kanał BSC, bit parzystości:");
        System.out.println("Średni czas wykonania: " + (totalDuration1a / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors1a / iterations));

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 1b - STOP-AND-WAIT ARQ, kanał BSC, CRC16:");
        System.out.println("--------------------------------------");
        System.out.println();

        double totalDuration1b = 0;
        double totalErrors1b = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            stopAndWaitSender.setPackets(ciag, sizeOfPacket);
            stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 2);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration1b += duration;
            totalErrors1b += stopAndWaitSender.getErrors();
        }
        System.out.println("Symulacja 1b - STOP-AND-WAIT ARQ, kanał BSC, CRC16:");
        System.out.println("Średni czas wykonania: " + (totalDuration1b / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors1b / iterations));

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 2a - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        System.out.println("--------------------------------------");
        System.out.println();


        // SYMULACJA 2 - STOP-AND-WAIT ARQ, KANAL GILBERTA-ELLIOTTA
        double totalDuration2a = 0;
        double totalErrors2a = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            stopAndWaitSender.setPackets(ciag, sizeOfPacket);
            stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 1);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration2a += duration;
            totalErrors2a += stopAndWaitSender.getErrors();
        }
        System.out.println("Symulacja 2a - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        System.out.println("Średni czas wykonania: " + (totalDuration2a / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors2a / iterations));

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 2b - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, CRC16:");
        System.out.println("--------------------------------------");
        System.out.println();


        double totalDuration2b = 0;
        double totalErrors2b = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            stopAndWaitSender.setPackets(ciag, sizeOfPacket);
            stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 2);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration2b += duration;
            totalErrors2b += stopAndWaitSender.getErrors();
        }
        System.out.println("Symulacja 2b - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, CRC16:");
        System.out.println("Średni czas wykonania: " + (totalDuration2b / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors2b / iterations));


        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 3a - GO-BACK-N ARQ, kanał BSC, bit parzystości:");
        System.out.println("--------------------------------------");
        System.out.println();


        // SYMULACJA 3 - GO-BACK-N ARQ, KANAL BSC
        double totalDuration3a = 0;
        double totalErrors3a = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            goBackNSender.setPackets(ciag, sizeOfPacket);
            goBackNSender.sendPacketsBSC(goBackNReceiver, 1, 4);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration3a += duration;
            totalErrors3a += goBackNSender.getErrors();
        }
        System.out.println("Symulacja 3a - GO-BACK-N ARQ, kanał BSC, bit parzystości:");
        System.out.println("Średni czas wykonania: " + (totalDuration3a / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors3a / iterations));


        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 3b - GO-BACK-N ARQ, kanał BSC, CRC16:");
        System.out.println("--------------------------------------");
        System.out.println();


        double totalDuration3b = 0;
        double totalErrors3b = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            goBackNSender.setPackets(ciag, sizeOfPacket);
            goBackNSender.sendPacketsBSC(goBackNReceiver, 2, 64);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration3b += duration;
            totalErrors3b += goBackNSender.getErrors();
        }
        System.out.println("Symulacja 3b - GO-BACK-N ARQ, kanał BSC, CRC16:");
        System.out.println("Średni czas wykonania: " + (totalDuration3b / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors3b / iterations));


        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 4a - GO-BACK-N ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        System.out.println("--------------------------------------");
        System.out.println();


        // SYMULACJA 4 - GO-BACK-N ARQ, KANAL GILBERTA-ELLIOTTA
        double totalDuration4a = 0;
        double totalErrors4a = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            goBackNSender.setPackets(ciag, sizeOfPacket);
            goBackNSender.sendPacketsGillbertElliot(64, goBackNReceiver, 1);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration4a += duration;
            totalErrors4a += goBackNSender.getErrors();
        }
        System.out.println("Symulacja 4a - GO-BACK-N ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        System.out.println("Średni czas wykonania: " + (totalDuration4a / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors4a / iterations));

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("Symulacja 4b - GO-BACK-N ARQ, kanał Gilberta-Elliotta, CRC16:");
        System.out.println("--------------------------------------");
        System.out.println();

        double totalDuration4b = 0;
        double totalErrors4b = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.println("Test: " + (i + 1));
            start = System.nanoTime();
            byte[] ciag = generator.generateArray(0, 100, sizeOfData);
            goBackNSender.setPackets(ciag, sizeOfPacket);
            goBackNSender.sendPacketsGillbertElliot(64, goBackNReceiver, 2);
            end = System.nanoTime();
            duration = (end - start) / 1000000;
            totalDuration4b += duration;
            totalErrors4b += goBackNSender.getErrors();
        }
        System.out.println("Symulacja 4b - GO-BACK-N ARQ, kanał Gilberta-Elliotta, CRC16:");
        System.out.println("Średni czas wykonania: " + (totalDuration4b / iterations) + " ms");
        System.out.println("Średnia liczba błędów transmisji: " + (totalErrors4b / iterations));

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println();



    }
}
