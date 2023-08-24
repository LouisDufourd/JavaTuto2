import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class Main {
    private static byte[] buf;
    private static String pin;
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(4445);
            generatePin();
            System.out.println("Server Started");
            System.out.println("Generated pin is "+ pin);
            while (true) {
                buf = new byte[4];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String receivedString = new String(buf, 0, buf.length);
                System.out.println(receivedString);
                buf = new byte[1];
                if (receivedString.equals(pin)) {
                    buf[0] = 1;
                } else {
                    buf[0] = 0;
                }
                packet.setData(buf);
                socket.send(packet);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generatePin() {
        Random rd = new Random();
        pin = intToPin(rd.nextInt(0,9999));
    }

    public static @NotNull String intToPin(int pinNumber) {
        StringBuilder codePin = new StringBuilder(String.valueOf(pinNumber));
        while (codePin.length() != 4) {
            codePin.insert(0, 0);
        }
        return codePin.toString();
    }
}