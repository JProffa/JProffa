package fi.lolcatz.profiler;

public class Util {

    /**
     * Prints bytes as byte string with newline every 4 bytes.
     * @param bytes Bytes to print.
     */
    public static void printBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int bytecounter = 1;
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
            if (bytecounter % 4 == 0) {
                sb.append(System.getProperty("line.separator"));
            }
            bytecounter++;
        }
        System.out.println(sb.toString());
    }
}
