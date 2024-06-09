package senders;

import encoding.CoderCRC16;
import encoding.CoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;
import receivers.StopAndWaitReceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StopAndWaitSender {

    byte[][] packets;
    private CoderParityBit coderParityBit;
    private CoderCRC16 coderCRC16;
    private BSC bsc;
    private GilbertElliottModifier gilbertElliott;
    private int errors;

    public StopAndWaitSender() {
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

    public void sendPacketsBSC(StopAndWaitReceiver stopAndWaitReceiver, int encodingMethod) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Boolean>> futures = new ArrayList<>();
        errors = 0;  // Reset errors before sending

        for (byte[] packet : packets) {
            futures.add(executor.submit(() -> {
                byte[] encodedPacket;
                if (encodingMethod == 1) {
                    encodedPacket = coderParityBit.addParityBit(packet);
                } else {
                    encodedPacket = coderCRC16.addCRC16(packet);
                }
                byte[] sentPacket = bsc.BSCcoding(encodedPacket, 0.001f);
                if (encodingMethod == 1) {
                    return stopAndWaitReceiver.receivePacketParityBit(sentPacket);
                } else {
                    return stopAndWaitReceiver.receivePacketCRC16(sentPacket);
                }
            }));
        }

        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) errors++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }

    public void sendPacketsGillbertElliot(StopAndWaitReceiver stopAndWaitReceiver, int encodingMethod) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Boolean>> futures = new ArrayList<>();
        errors = 0;  // Reset errors before sending

        for (byte[] packet : packets) {
            futures.add(executor.submit(() -> {
                byte[] encodedPacket;
                if (encodingMethod == 1) {
                    encodedPacket = coderParityBit.addParityBit(packet);
                } else {
                    encodedPacket = coderCRC16.addCRC16(packet);
                }
                byte[] sentPacket = gilbertElliott.modifyStringByElliotGilbert(encodedPacket, 0.5f, 0.5f, 0.001f);
                if (encodingMethod == 1) {
                    return stopAndWaitReceiver.receivePacketParityBit(sentPacket);
                } else {
                    return stopAndWaitReceiver.receivePacketCRC16(sentPacket);
                }
            }));
        }

        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) errors++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        System.out.println("Przesyłanie pakietów zakończone.\nLiczba przesłanych pakietów: " + packets.length);
        System.out.println("Liczba błędów transmisji: " + errors + "\n");
    }

    public int getErrors() {
        return errors;
    }
}
