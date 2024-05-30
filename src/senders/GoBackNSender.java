package senders;

import encoding.CoderCRC16;
import encoding.CoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;
import receivers.GoBackNReceiver;

public class GoBackNSender {

    String[] packets;
    private CoderParityBit coderParityBit;
    private CoderCRC16 coderCRC16;
    private BSC bsc;
    private GilbertElliottModifier gillbertElliott;

    public GoBackNSender() {

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
    public void sendPacketsBSC(int windowSize, GoBackNReceiver goBackNReceiver, int encodingMethod) {

        int packetToSend = 0, firstPacketSent, errors = 0;
        String[] encodedPackets = new String[packets.length]; // pakiety po kodowaniu
        String[] sentPackets = new String[packets.length]; // pakietu po przeslaniu kanalem BSC


        while(packetToSend < packets.length) { // przesylamy dopoki sa pakiety do przeslania

            // Przesylamy n=windowSize pakietow, od indeksu i = packetToSend
            for(int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) {

                // Kodowanie pakietow
                if(encodedPackets[i] == null) { // pakiet jeszcze nie zakodowany
                    if (encodingMethod == 1) encodedPackets[i] = coderParityBit.addParityBit(packets[i]);
                    else encodedPackets[i] = coderCRC16.addCRC16(packets[i]);
                }

                // Przeslanie pakietu kanalem BSC - pakiety po przeslaniu w tablicy sentPackets[]
                sentPackets[i] = bsc.BSCcoding(encodedPackets[i], 0.05f);
            }

            firstPacketSent = packetToSend; // aby sprawdzic, czy wszystkie n=windowSize pakietow zostalo przeslanych bezblednie

            // Odebranie n=windowSize pakietow przez odbiornik, kodowanie w zaleznosci od parametru funkcji
            if(encodingMethod == 1)
                packetToSend = goBackNReceiver.receivePacketsParityBit(sentPackets, packetToSend, windowSize);
            else
                packetToSend = goBackNReceiver.receivePacketsCRC16(sentPackets, packetToSend, windowSize);


            // Jesli wartosc packetToSend nie jest wieksza o n=windowSize od wartosci przed przeslaniem
            // to znaczy ze wystapil blad - ciag n=windowSize pakietow nie zostal przeslany poprawnie
            if(packetToSend - windowSize != firstPacketSent && packetToSend < packets.length) {
                errors++;
                System.out.println("Blad przesylania. Blednie przeslany pakiet - pakiet " + packetToSend);
            }

        }

        System.out.println("Przesylanie pakietow zakonczone.\nLiczba przeslanych pakietow: " + packets.length);
        System.out.println("Liczba bledow transmisji: " + errors + "\n");

    }



    // Wyslij pakiety kanalem Gilberta-Elliotta
    // encodingMethod: 1 - bit parzystosci, 2 - CRC16 (opcja domyslna)
    public void sendPacketsGillbertElliot(int windowSize, GoBackNReceiver goBackNReceiver, int encodingMethod) {

        int packetToSend = 0, firstPacketSent, errors = 0;
        String[] encodedPackets = new String[packets.length]; // pakiety po kodowaniu
        String[] sentPackets = new String[packets.length]; // pakietu po przeslaniu kanalem BSC


        while(packetToSend < packets.length) { // przesylamy dopoki sa pakiety do przeslania

            // Przesylamy n=windowSize pakietow, od indeksu i = packetToSend
            for(int i = packetToSend; i < packetToSend + windowSize && i < packets.length; i++) {

                // Kodowanie pakietow
                if(encodedPackets[i] == null) { // pakiet jeszcze nie zakodowany
                    if (encodingMethod == 1) encodedPackets[i] = coderParityBit.addParityBit(packets[i]);
                    else encodedPackets[i] = coderCRC16.addCRC16(packets[i]);
                }

                // Przeslanie pakietu kanalem Gilberta-Elliotta - pakiety po przeslaniu w tablicy sentPackets[]
                sentPackets[i] = gillbertElliott.modifyString(encodedPackets[i], 0.5f, 0.5f, 0.05f);
            }

            firstPacketSent = packetToSend; // aby sprawdzic, czy wszystkie n=windowSize pakietow zostalo przeslanych bezblednie

            // Odebranie n=windowSize pakietow przez odbiornik, kodowanie w zaleznosci od parametru funkcji
            if(encodingMethod == 1)
                packetToSend = goBackNReceiver.receivePacketsParityBit(sentPackets, packetToSend, windowSize);
            else
                packetToSend = goBackNReceiver.receivePacketsCRC16(sentPackets, packetToSend, windowSize);

            // Jesli wartosc packetToSend nie jest wieksza o n=windowSize od wartosci przed przeslaniem
            // to znaczy ze wystapil blad - ciag n=windowSize pakietow nie zostal przeslany poprawnie
            if(packetToSend - windowSize != firstPacketSent && packetToSend < packets.length) {
                errors++;
                System.out.println("Blad przesylania. Blednie przeslany pakiet - pakiet " + packetToSend);
            }

        }

        System.out.println("Przesylanie pakietow zakonczone.\nLiczba przeslanych pakietow: " + packets.length);
        System.out.println("Liczba bledow transmisji: " + errors + "\n");
    }


}
