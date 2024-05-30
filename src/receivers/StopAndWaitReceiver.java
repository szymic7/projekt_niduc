package receivers;

import encoding.DecoderCRC16;
import encoding.DecoderParityBit;

public class StopAndWaitReceiver {

    private DecoderParityBit decoderParityBit;
    private DecoderCRC16 decoderCRC16;

    public StopAndWaitReceiver() {
        decoderParityBit = new DecoderParityBit();
        decoderCRC16 = new DecoderCRC16();
    }

    public boolean receivePacketParityBit(String packet) {
        return decoderParityBit.decode(packet);
    }

    public boolean receivePacketCRC16(String packet) {
        return decoderCRC16.decode(packet);
    }

}
