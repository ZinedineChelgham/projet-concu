package simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {

    static final int WIDTH = 100;
    static final int HEIGHT = 100;
    static final int nbPersons = 100;

    public void main(String... args) {
        try {
            String str = WIDTH + " " + HEIGHT;
            BufferedWriter writer = new BufferedWriter(new FileWriter("initialisation_file.txt"));
            writer.write(str);
            for(int i = 0; i<nbPersons; i++){
                str = "\n" + (int) (Math.random() * (WIDTH)) + " " + (int) (Math.random() * (HEIGHT)) + " " + (int) (Math.random()*(WIDTH)) + " " + (int) (Math.random() * (HEIGHT));
                writer.append(str);
            }
            writer.close();
        }
        catch(IOException e) {
            System.out.print(e);
        }
    }
}
