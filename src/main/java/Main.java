import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner ob = new Scanner(System.in);

        SerialPort comPort = SerialPort.getCommPort("COM5");
        comPort.setBaudRate(115200);
        comPort.openPort();
        OutputStream outputStream = comPort.getOutputStream();

        System.out.println("input:");
        String input = ob.nextLine();
        while(!input.equals("n")){
            byte[] data = SerializationUtils.serialize(input);
            //byte[] data = input.getBytes();
            System.out.println("El arreglo tiene largo: " + data.length);
            System.out.println("el Array: " + Arrays.toString(data));
            outputStream.write(data);
            outputStream.flush();

            System.out.println("input:");
            input = ob.nextLine();
        }
    }


}
