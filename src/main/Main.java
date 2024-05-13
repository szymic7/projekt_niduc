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

        int sizeOfPacket = 20;
        System.out.println("\nSymulacja przesylania kilku pakietow kanalem komunikacyjnym.");
        System.out.println("Prawdopodobienstwo wyslania bitu przeciwnego: " + 0.05f);
        System.out.println("Rozmiar pakietu: " + sizeOfPacket + " bitow.\n");


        // SYMULACJA 1 - STOP-AND-WAIT ARQ, KANAL BSC

        // SYMULACJA 1a - kodowanie metoda bitu parzystosci
        String ciag = generator.generateArray(0, 100, 400);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 1a - STOP-AND-WAIT ARQ, kanal BSC, bit parzystosci:");
        stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 1);

        // SYMULACJA 1b - kodowanie metoda CRC16
        ciag = generator.generateArray(0, 100, 400);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 1b - STOP-AND-WAIT ARQ, kanal BSC, CRC16:");
        stopAndWaitSender.sendPacketsBSC(stopAndWaitReceiver, 2);



        // SYMULACJA 2 - STOP-AND-WAIT ARQ, KANAL GILBERTA-ELLIOTTA

        // SYMULACJA 2a - kodowanie metoda bitu parzystosci
        ciag = generator.generateArray(0, 100, 400);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 2a - STOP-AND-WAIT ARQ, kanal Giblerta-Elliotta, bit parzystosci:");
        stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 1);

        // SYMULACJA 2b - kodowanie metoda CRC16
        ciag = generator.generateArray(0, 100, 400);
        stopAndWaitSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 2b - STOP-AND-WAIT ARQ, kanal Giblerta-Elliotta, CRC16:");
        stopAndWaitSender.sendPacketsGillbertElliot(stopAndWaitReceiver, 2);



        // SYMULACJA 3 - GO-BACK-N ARQ, KANAL BSC

        // SYMULACJA 3a - kodowanie metoda bitu parzystosci
        ciag = generator.generateArray(0, 100, 400);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 3a - GO-BACK-N ARQ, kanal BSC, bit parzystosci:");
        goBackNSender.sendPacketsBSC(5, goBackNReceiver, 1);

        // SYMULACJA 3b - kodowanie metoda CRC16
        ciag = generator.generateArray(0, 100, 400);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 3b - GO-BACK-N ARQ, kanal BSC, CRC16:");
        goBackNSender.sendPacketsBSC(5, goBackNReceiver, 2);



        // SYMULACJA 4 - GO-BACK-N ARQ, KANAL GILBERTA-ELLIOTTA

        // SYMULACJA 4a - kodowanie metoda bitu parzystosci
        ciag = generator.generateArray(0, 100, 400);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 4a - GO-BACK-N ARQ, kanal Gilberta-Elliotta, bit parzystosci:");
        goBackNSender.sendPacketsGillbertElliot(5, goBackNReceiver, 1);

        // SYMULACJA 4b - kodowanie metoda CRC16
        ciag = generator.generateArray(0, 100, 400);
        goBackNSender.setPackets(ciag, sizeOfPacket);
        System.out.println("Symulacja 4b - GO-BACK-N ARQ, kanal Gilberta-Elliotta, CRC16:");
        goBackNSender.sendPacketsGillbertElliot(5, goBackNReceiver, 2);

    }


}
