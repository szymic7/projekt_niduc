package receivers;

import encoding.DecoderCRC16;
import encoding.DecoderParityBit;

public class GoBackNReceiver {

    private DecoderParityBit decoderParityBit;
    private DecoderCRC16 decoderCRC16;

    public GoBackNReceiver() {
        decoderParityBit = new DecoderParityBit();
        decoderCRC16 = new DecoderCRC16();
    }

    public int receivePacketParityBit(byte[] packet, int index) {
        return decoderParityBit.decode(packet) ? (index + 1) : index;
    }

    public int receivePacketCRC16(byte[] packet, int index) {
        return decoderCRC16.decode(packet) ? (index + 1) : index;
    }

    public int receivePacketsParityBit(byte[][] packets, int firstPacket, int windowSize) {
        int packetToSend = firstPacket; // wartosc zwracana przez funckje, mowiaca, ktorego kolejnego pakietu oczekuje odbiornik

        for (int i = firstPacket; i < firstPacket + windowSize && i < packets.length; i++) {
            // jesli dekoder wykryje blad - nie przetwarza kolejnych pakietow i zwraca indeks blednie przeslanego pakietu
            if (packets[i] == null || !decoderParityBit.decode(packets[i])) break;
            packetToSend++; // w przypadku poprawnie odebranego pakietu zwiekszamy indeks oczekiwanego pakietu
        }

        return packetToSend;
    }

    public int receivePacketsCRC16(byte[][] packets, int firstPacket, int windowSize) {
        int packetToSend = firstPacket; // wartosc zwracana przez funckje, mowiaca, ktorego kolejnego pakietu oczekuje odbiornik

        for (int i = firstPacket; i < firstPacket + windowSize && i < packets.length; i++) {
            // jesli dekoder wykryje blad - nie przetwrza kolejnych pakietow i zwraca indeks blednie przeslanego pakietu
            if (packets[i] == null || !decoderCRC16.decode(packets[i])) break;
            packetToSend++; // w przypadku poprawnie odebranego pakietu zwiekszamy indeks oczekiwanego pakietu
        }

        return packetToSend;
    }
}