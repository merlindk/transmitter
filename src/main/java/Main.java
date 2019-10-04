import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int SIZE = 32;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner ob = new Scanner(System.in);

        SerialPort comPort = SerialPort.getCommPort("COM5");
        comPort.setBaudRate(115200);
        comPort.openPort();
        OutputStream outputStream = comPort.getOutputStream();

        System.out.println("input:");
        String input = ob.nextLine();
        while (!input.equals("n")) {
            byte[] data = SerializationUtils.serialize(input);

            System.out.println("El arreglo tiene largo: " + data.length);
            System.out.println("el Array: " + Arrays.toString(data));

            List<byte[]> listOfArrays = splitArray(data, SIZE);

            for (byte[] array : listOfArrays) {
                System.out.println("El sub arreglo tiene largo: " + array.length);
                System.out.println("el sub Array: " + Arrays.toString(array));
                outputStream.write(array);
                outputStream.flush();
            }

            System.out.println("input:");
            input = ob.nextLine();
        }
    }

    private static List<byte[]> splitArray(byte[] original, int size) {
        int modulus = original.length % size;
        int numberOfArrays = (original.length / size) + 1;
        List<byte[]> listOfArrays = new ArrayList<>(numberOfArrays);

        if (numberOfArrays == 0) {
            listOfArrays.add(original);
            return listOfArrays;
        }

        for (int i = 0; i < numberOfArrays; i++) {
            byte[] aux;
            aux = new byte[size];
            Arrays.fill(aux, (byte)-127);
            if (i + 1 == numberOfArrays) {
                System.arraycopy(original, size * i, aux, 0, modulus);
            } else {
                aux = new byte[size];
                System.arraycopy(original, size * i, aux, 0, size);
            }
            listOfArrays.add(aux);
        }

        return listOfArrays;
    }
}
