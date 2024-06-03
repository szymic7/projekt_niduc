package senders;

import encoding.CoderCRC16;
import encoding.CoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;
import receivers.GoBackNReceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoBackNSender {

    /*
       Zakladamy ze liczba bitow do przeslania jest podzielna przez rozmiar pakietu
       int numberOfPackets = bits.length() / sizeOfPacket;
       String[] createdPackets = new String[numberOfPackets];
       for(int i = 0; i < numberOfPackets; i++) {
           createdPackets[i] = bits.substring(i * sizeOfPacket, i * sizeOfPacket + sizeOfPacket);
       }
    */

    byte[][] packets;
    private CoderParityBit coderParityBit;
    private CoderCRC16 coderCRC16;
    private BSC bsc;
    private GilbertElliottModifier gilbertElliott;

    public GoBackNSender() {
        coderParityBit = new CoderParityBit();
        coderCRC16 = new CoderCRC16();
        bsc = new BSC();
        gilbertElliott = new GilbertElliottModifier();
    }

    public void setPackets(byte[] bits, int sizeOfPacket) {

        // Zakladamy ze liczba bitow do przeslania jest podzielna przez rozmiar pakietu
        int numberOfPackets = bits.length / sizeOfPacket;
        packets = new byte[numberOfPackets][sizeOfPacket];
        for (int i = 0; i < numberOfPackets; i++) {
            System.arraycopy(bits, i * sizeOfPacket, packets[i], 0, sizeOfPacket);
        }
    }

    // Wyslij pakiety kanalem BSC
    // encodingMethod: 1 - bit parzystosci, 2 - CRC16 (opcja domyslna)
    public void sendPacketsBSC(GoBackNReceiver goBackNReceiver, int encodingMethod, int windowSize) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int packetToSend = 0;
        int errors = 0;
        byte[][] encodedPackets = new byte[packets.length][]; // pakiety po kodowaniu
        byte[][] sentPackets = new byte[packets.length][]; // pakietu po przeslaniu kanalem BSC

        while (packetToSend < packets.length) { // przesylamy dopoki sa pakiety do przeslania

            final int finalPacketToSend = packetToSend;
            List<Future<Integer>> futures = new ArrayList<>();

            // Przesylamy n=windowSize pakietow, od indeksu i = packetToSend
            for (int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) { // pakiet jeszcze nie zakodowany
                final int index = i;
                futures.add(executor.submit(() -> {
                    // Kodowanie pakietow
                    if (encodedPackets[index] == null) {
                        if (encodingMethod == 1) {
                            encodedPackets[index] = coderParityBit.addParityBit(packets[index]);
                        } else {
                            encodedPackets[index] = coderCRC16.addCRC16(packets[index]);
                        }
                    }
                    // Przeslanie pakietu kanalem BSC - pakiety po przeslaniu w tablicy sentPackets[]
                    sentPackets[index] = bsc.BSCcoding(encodedPackets[index], 0.001f);
                    if (encodingMethod == 1) {
                        return goBackNReceiver.receivePacketsParityBit(sentPackets, finalPacketToSend, windowSize);
                    } else {
                        return goBackNReceiver.receivePacketsCRC16(sentPackets, finalPacketToSend, windowSize);
                    }
                }));
            }

            for (Future<Integer> future : futures) {
                try {
                    int result = future.get();
                    if (result != finalPacketToSend + windowSize) {
                        errors++;
                        packetToSend = result;
                        break;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            packetToSend += windowSize;
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }


    // Wyslij pakiety kanalem Gilberta-Elliotta
    // encodingMethod: 1 - bit parzystosci, 2 - CRC16 (opcja domyslna)
    public void sendPacketsGillbertElliot(int windowSize, GoBackNReceiver goBackNReceiver, int encodingMethod) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int packetToSend = 0;
        int errors = 0;
        byte[][] encodedPackets = new byte[packets.length][]; // pakiety po kodowaniu
        byte[][] sentPackets = new byte[packets.length][]; // pakietu po przeslaniu kanalem BSC

        while (packetToSend < packets.length) { // przesylamy dopoki sa pakiety do przeslania
            final int finalPacketToSend = packetToSend;
            List<Future<Integer>> futures = new ArrayList<>();

            // Przesylamy n=windowSize pakietow, od indeksu i = packetToSend
            for (int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) {
                final int index = i;
                futures.add(executor.submit(() -> {
                    // Kodowanie pakietow
                    if (encodedPackets[index] == null) {
                        if (encodingMethod == 1) {
                            encodedPackets[index] = coderParityBit.addParityBit(packets[index]);
                        } else {
                            encodedPackets[index] = coderCRC16.addCRC16(packets[index]);
                        }
                    }
                    // Przeslanie pakietu kanalem Gilberta-Elliotta - pakiety po przeslaniu w tablicy sentPackets[]
                    sentPackets[index] = gilbertElliott.modifyStringByElliotGilbert(encodedPackets[index], 0.5f, 0.5f, 0.01f);
                    if (encodingMethod == 1) {
                        return goBackNReceiver.receivePacketsParityBit(sentPackets, finalPacketToSend, windowSize);
                    } else {
                        return goBackNReceiver.receivePacketsCRC16(sentPackets, finalPacketToSend, windowSize);
                    }
                }));
            }

            for (Future<Integer> future : futures) {
                try {
                    int result = future.get();
                    if (result != finalPacketToSend + windowSize) {
                        errors++;
                        packetToSend = result;
                        break;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            packetToSend += windowSize;
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }
}