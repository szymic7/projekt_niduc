package senders;

import arq.CoderCRC16;
import arq.CoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;
import receivers.StopAndWaitReceiver;

public class StopAndWaitSender {

    String[] packets;
    private CoderParityBit coderParityBit;
    private CoderCRC16 coderCRC16;
    private BSC bsc;
    private GilbertElliottModifier gillbertElliott;

    public StopAndWaitSender() {

        /*// Zakladamy ze liczba bitow do przeslania jest podzielna przez rozmiar pakietu
        int numberOfPackets = bits.length() / sizeOfPacket;
        String[] createdPackets = new String[numberOfPackets];
        for(int i = 0; i < numberOfPackets; i++) {
            createdPackets[i] = bits.substring(i * sizeOfPacket, i * sizeOfPacket + sizeOfPacket);
        }
        packets = createdPackets;*/

        coderParityBit = new CoderParityBit();
        coderCRC16 = new CoderCRC16();
        bsc = new BSC();
        gillbertElliott = new GilbertElliottModifier();
    }

    public void setPackets(String bits, int sizeOfPacket) {

        // Zakladamy ze liczba bitow do przeslania jest podzielna przez rozmiar pakietu
        int numberOfPackets = bits.length() / sizeOfPacket;
        String[] createdPackets = new String[numberOfPackets];
        for(int i = 0; i < numberOfPackets; i++) {
            createdPackets[i] = bits.substring(i * sizeOfPacket, i * sizeOfPacket + sizeOfPacket);
        }

        packets = createdPackets;
    }


    // Wyslij pakiety kanalem BSC
    // encodingMethod: 1 - bit parzystosci, 2 - CRC16 (opcja domyslna)
    public void sendPacketsBSC(StopAndWaitReceiver stopAndWaitReceiver, int encodingMethod) {

        boolean receivedCorrectly;
        int errors = 0;
        String encodedPacket, sentPacket;

        for(String packet: packets) {

            // Kodowanie pakietu
            if(encodingMethod == 1) encodedPacket = coderParityBit.addParityBit(packet);
            else encodedPacket = coderCRC16.addCRC16(packet);

            do {
                // Wyslanie pakietu
                sentPacket = bsc.BSCcoding(encodedPacket, 0.05f);
                if(encodingMethod == 1) receivedCorrectly = stopAndWaitReceiver.receivePacketParityBit(sentPacket);
                else receivedCorrectly = stopAndWaitReceiver.receivePacketCRC16(sentPacket);
                errors += 1;

            } while(!receivedCorrectly);

        }

        errors -= packets.length; // zmniejszenie liczby bledow o transmisje wymagane
        System.out.println("Przesylanie pakietow zakonczone.\nLiczba przeslanych pakietow: " + packets.length);
        System.out.println("Liczba bledow transmisji: " + errors + "\n");

    }

    // Wyslij pakiety kanalem Gilberta-Elliotta
    // encodingMethod: 1 - bit parzystosci, 2 - CRC16 (opcja domyslna)
    public void sendPacketsGillbertElliot(StopAndWaitReceiver stopAndWaitReceiver, int encodingMethod) {

        boolean receivedCorrectly;
        int errors = 0;
        String encodedPacket, sentPacket;

        for(String packet: packets) {

            // Kodowanie pakietu
            if(encodingMethod == 1) encodedPacket = coderParityBit.addParityBit(packet);
            else encodedPacket = coderCRC16.addCRC16(packet);

            do {
                // Wyslanie pakietu
                sentPacket = gillbertElliott.modifyString(encodedPacket, 0.5f, 0.5f, 0.05f);
                if(encodingMethod == 1) receivedCorrectly = stopAndWaitReceiver.receivePacketParityBit(sentPacket);
                else receivedCorrectly = stopAndWaitReceiver.receivePacketCRC16(sentPacket);
                errors += 1;

            } while(!receivedCorrectly);

        }

        errors -= packets.length; // zmniejszenie liczby bledow o transmisje wymagane
        System.out.println("Przesylanie pakietow zakonczone.\nLiczba przeslanych pakietow: " + packets.length);
        System.out.println("Liczba bledow transmisji: " + errors + "\n");
    }

}
