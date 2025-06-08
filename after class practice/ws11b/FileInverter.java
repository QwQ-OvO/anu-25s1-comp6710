package ws11b;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileInverter {
    public static void main(String[] args) {
        List<Character> chars=new ArrayList<>();
        try(var reader=new FileReader(args[0])) {
            while(reader.ready()) {
                int next=reader.read();
                if(next>=0) {
                    chars.add((char)next);
                }
            }
            Thread.sleep(10000);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        chars=chars.reversed();
        try(var writer=new FileWriter(args[0])) {
            for(var character : chars) {
                writer.write(character);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
