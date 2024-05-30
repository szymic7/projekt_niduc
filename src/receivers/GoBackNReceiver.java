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

    public int receivePacketsParityBit(String[] packets, int firstPacket, int windowSize) {
        int packetToSend = firstPacket; // wartosc zwracana przez funckje, mowiaca, ktorego kolejnego pakietu oczekuje odbiornik

        for(int i = firstPacket; i < firstPacket + windowSize && i < packets.length; i++) {
            // jesli dekoder wykryje blad - nie przetwarza kolejnych pakietow i zwraca indeks blednie przeslanego pakietu
            if(!decoderParityBit.decode(packets[i])) break;
            packetToSend++; // w przypadku poprawnie odebranego pakietu zwiekszamy indeks oczekiwanego pakietu
        }

        return packetToSend;
    }

    public int receivePacketsCRC16(String[] packets, int firstPacket, int windowSize) {
        int packetToSend = firstPacket; // wartosc zwracana przez funckje, mowiaca, ktorego kolejnego pakietu oczekuje odbiornik

        for(int i = firstPacket; i < firstPacket + windowSize && i < packets.length; i++) {
            // jesli dekoder wykryje blad - nie przetwrza kolejnych pakietow i zwraca indeks blednie przeslanego pakietu
            if(!decoderCRC16.decode(packets[i])) break;
            packetToSend++; // w przypadku poprawnie odebranego pakietu zwiekszamy indeks oczekiwanego pakietu
        }

        return packetToSend;
    }


}
