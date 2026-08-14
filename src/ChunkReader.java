import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.toBinaryString;


public class ChunkReader {

    int dataLength = 126;
    private int counter = 0;
    private final int chunkLength;
    List<BigInteger> finalByteStream = new ArrayList<>();

    ChunkReader(byte[] bytes) {
        chunkLength = bytes.length/dataLength;
        for (int i = 0; i <= chunkLength; i++) {

            StringBuilder subChunk = new StringBuilder();
            if (i == chunkLength && bytes.length % dataLength != 0) {
                subChunk.append(String.format("%8s", toBinaryString(bytes.length % dataLength)).replace(' ', '0'));
            } else if (i == chunkLength && bytes.length % dataLength == 0) {
                break;
            } else {
                subChunk.append(String.format("%8s", toBinaryString(dataLength)).replace(' ', '0'));
            }

            for(int j = 0; j < dataLength; j++) {
                try {
                    subChunk.append(String.format("%8s", toBinaryString(bytes[i * dataLength + j] & 0xFF)).replace(' ', '0'));

                } catch (ArrayIndexOutOfBoundsException e) {
                    subChunk.append("0".repeat(8));
                }
            }



            BigInteger chunkInt = new BigInteger(subChunk.toString(), 2);
            finalByteStream.add(chunkInt);
        }



    }

    boolean hasNext() {
         return counter < chunkLength;
    }

    BigInteger next() {
        if (hasNext()) {
            counter++;
            return finalByteStream.get(counter - 1);
        } else {
            throw new IndexOutOfBoundsException("No further Chunks.");
        }

    }

}
