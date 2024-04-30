package arq;

public class CoderParityBit {

    public CoderParityBit() {}

    // Kodowanie dla pojedynczego pakietu
    public String addParityBit(String bits) {
        int ones = 0;
        String codedPacket;

        for(int i = 0; i < bits.length(); i++) {
            if(bits.charAt(i) == '1') ones++;
        }

        if(ones % 2 == 1) {             // nieparzysta liczba jedynek
            codedPacket = bits + "1";   // bit parzystosci rowny 1
        } else {                        // parzysta liczba jedynek
            codedPacket = bits + "0";   // bit parzystosci rowny 0
        }

        return codedPacket;
    }


    // Kodowanie dla tablicy - ciagu pakietow
    public String[] addParityBits(String[] bitsArray) {
        String[] codedPackets = new String[bitsArray.length];
        for(int i = 0; i < bitsArray.length; i++) {
            codedPackets[i] = this.addParityBit(bitsArray[i]);
        }

        return codedPackets;
    }

}
