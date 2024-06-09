package senders;

import encoding.CoderCRC16;
import encoding.CoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;
import receivers.GoBackNReceiver;

import java.util.Collections;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class GoBackNSender {

    byte[][] packets;
    private CoderParityBit coderParityBit;
    private CoderCRC16 coderCRC16;
    private BSC bsc;
    private GilbertElliottModifier gilbertElliott;
    private int errors;

    public GoBackNSender() {
        coderParityBit = new CoderParityBit();
        coderCRC16 = new CoderCRC16();
        bsc = new BSC();
        gilbertElliott = new GilbertElliottModifier();
        errors = 0;
    }

    public void setPackets(byte[] bits, int sizeOfPacket) {
        int numberOfPackets = bits.length / sizeOfPacket;
        packets = new byte[numberOfPackets][sizeOfPacket];
        for (int i = 0; i < numberOfPackets; i++) {
            System.arraycopy(bits, i * sizeOfPacket, packets[i], 0, sizeOfPacket);
        }
    }

    public void sendPacketsBSC(GoBackNReceiver goBackNReceiver, int encodingMethod, int windowSize) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int packetToSend = 0;
        errors = 0;  // Reset errors before sending
        byte[][] encodedPackets = new byte[packets.length][];
        byte[][] sentPackets = new byte[packets.length][];

        while (packetToSend < packets.length) {
            final int finalPacketToSend = packetToSend;
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) {
                final int index = i;
                futures.add(executor.submit(() -> {
                    if (encodedPackets[index] == null) {
                        if (encodingMethod == 1) {
                            encodedPackets[index] = coderParityBit.addParityBit(packets[index]);
                        } else {
                            encodedPackets[index] = coderCRC16.addCRC16(packets[index]);
                        }
                    }
                    sentPackets[index] = bsc.BSCcoding(encodedPackets[index], 0.001f);
                    if (encodingMethod == 1) {
                        return goBackNReceiver.receivePacketParityBit(sentPackets[index], index);
                    } else {
                        return goBackNReceiver.receivePacketCRC16(sentPackets[index], index);
                    }
                }));
            }

            int indexOfPacket = finalPacketToSend;
            for (Future<Integer> future : futures) {
                try {
                    int result = future.get();
                    if (result == indexOfPacket) {
                        errors++;
                        packetToSend = result;
                        break;
                    } else {
                        packetToSend = result;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                indexOfPacket++;
            }
            futures.clear();
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }

    public void sendPacketsGillbertElliot(int windowSize, GoBackNReceiver goBackNReceiver, int encodingMethod) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int packetToSend = 0;
        errors = 0;  // Reset errors before sending
        byte[][] encodedPackets = new byte[packets.length][];
        byte[][] sentPackets = new byte[packets.length][];

        while (packetToSend < packets.length) {
            final int finalPacketToSend = packetToSend;
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) {
                final int index = i;
                futures.add(executor.submit(() -> {
                    if (encodedPackets[index] == null) {
                        if (encodingMethod == 1) {
                            encodedPackets[index] = coderParityBit.addParityBit(packets[index]);
                        } else {
                            encodedPackets[index] = coderCRC16.addCRC16(packets[index]);
                        }
                    }
                    sentPackets[index] = gilbertElliott.modifyStringByElliotGilbert(encodedPackets[index], 0.5f, 0.5f, 0.001f);
                    if (encodingMethod == 1) {
                        return goBackNReceiver.receivePacketParityBit(sentPackets[index], index);
                    } else {
                        return goBackNReceiver.receivePacketCRC16(sentPackets[index], index);
                    }
                }));
            }

            int indexOfPacket = finalPacketToSend;
            for (Future<Integer> future : futures) {
                try {
                    int result = future.get();
                    if (result == indexOfPacket) {
                        errors++;
                        packetToSend = result;
                        break;
                    } else {
                        packetToSend = result;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                indexOfPacket++;
            }
            futures.clear();
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }

    public int getErrors() {
        return errors;
    }
}
