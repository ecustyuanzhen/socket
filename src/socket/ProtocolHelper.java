package socket;

/**
 * zhenyuan
 * 2019-06-19 13:10
 */
public class ProtocolHelper {
    /**
     * 大小端统一
     *
     * @param data
     * @return
     */
    public static int ntoh1(byte[] data) {
        return ((int) data[0] << 24) | (((int) data[1] << 16) & 0xffffff) | (((int) data[2] << 8) & 0xffff) | ((int) data[3] & 0xff);
    }

    public static byte[] htonl(int length) {
        byte[] array = new byte[4];
        array[0] = (byte) ((length >> 24) & 0xff);
        array[1] = (byte) ((length >> 16) & 0xff);
        array[2] = (byte) ((length >> 8) & 0xff);
        array[3] = (byte) (length & 0xff);
        return array;
    }
}
