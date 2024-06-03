package main;

import generating_data.RandomNumberND;
import receivers.GoBackNReceiver;
import receivers.StopAndWaitReceiver;
import senders.GoBackNSender;
import senders.StopAndWaitSender;

public class Main {

    public static void main(String[] args) {

        // Inicjalizacja generatora liczb losowych, odbiornikow i nadajnikow
        RandomNumberND generator = new RandomNumberND();
        StopAndWaitSender stopAndWaitSender = new StopAndWaitSender();
        StopAndWaitReceiver stopAndWaitReceiver = new StopAndWaitReceiver();
        GoBackNSender goBackNSender = new GoBackNSender();
        GoBackNReceiver goBackNReceiver = new GoBackNReceiver();
        long start;
        long end;
        long duration;

        //512KB ->  4194304
        //1MB   ->  8388608
        //10MB  ->  83886080
        int sizeOfData = 83886080;

        //512B  ->  4096
        //1KB   ->  8192
        int sizeOfPacket = 4096;
        System.out.println("\nSymulacja przesyłania kilku pakietów kanałem komunikacyjnym.");
        System.out.println("Prawdopodobieństwo wysłania bitu przeciwnego: " + 0.01f);
        System.out.println("Rozmiar pakietu: " + sizeOfPacket + " bitów.\n");


        // SYMULACJA 1 - STOP-AND-WAIT ARQ, KANAL BSC

        // SYMULACJA 1a - kodowanie metoda bitu parzystosci
        start = System.nanoTime();
        byte[] ciag = generator.generateArray(0, 100, sizeOfData);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 1a - STOP-AND-WAIT ARQ, kanał BSC, bit parzystości:");
        stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 1);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");

        // SYMULACJA 1b - kodowanie metoda CRC16
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 1b - STOP-AND-WAIT ARQ, kanał BSC, CRC16:");
        stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 2);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");


        // SYMULACJA 2 - STOP-AND-WAIT ARQ, KANAL GILBERTA-ELLIOTTA

        // SYMULACJA 2a - kodowanie metoda bitu parzystosci
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 2a - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 1);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");

        // SYMULACJA 2b - kodowanie metoda CRC16
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 2b - STOP-AND-WAIT ARQ, kanał Gilberta-Elliotta, CRC16:");
        stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 2);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");


        // SYMULACJA 3 - GO-BACK-N ARQ, KANAL BSC

        // SYMULACJA 3a - kodowanie metoda bitu parzystosci
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 3a - GO-BACK-N ARQ, kanał BSC, bit parzystości:");
        goBackNSender.sendPacketsBSC(goBackNReceiver, 1, 64);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");

        // SYMULACJA 3b - kodowanie metoda CRC16
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 3b - GO-BACK-N ARQ, kanał BSC, CRC16:");
        goBackNSender.sendPacketsBSC(goBackNReceiver, 2, 64);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");


        // SYMULACJA 4 - GO-BACK-N ARQ, KANAL GILBERTA-ELLIOTTA

        // SYMULACJA 4a - kodowanie metoda bitu parzystosci
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 4a - GO-BACK-N ARQ, kanał Gilberta-Elliotta, bit parzystości:");
        goBackNSender.sendPacketsGillbertElliot(64, goBackNReceiver, 1);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");

        // SYMULACJA 4b - kodowanie metoda CRC16
        start = System.nanoTime();
        ciag = generator.generateArray(0, 100, sizeOfData);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 4b - GO-BACK-N ARQ, kanał Gilberta-Elliotta, CRC16:");
        goBackNSender.sendPacketsGillbertElliot(64, goBackNReceiver, 2);
        end = System.nanoTime();
        duration = (end - start) / 1000000;
        System.out.println("Czas wykonania: " + duration + " ms");
    }
}