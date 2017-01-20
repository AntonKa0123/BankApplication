package FileReaderAndWriterHelper;

import java.io.*;

/**
 * Created by дмитрий on 15.10.2016.
 */
public class HelperForWorkingWithFile {
     public static BufferedWriter createWriter(String filePathForWrite){
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(filePathForWrite);
            bufferedWriter = new BufferedWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedWriter;
    }

     public static BufferedReader createReader(String filePathForRead) {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filePathForRead);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        return new BufferedReader(new InputStreamReader(fstream));
    }
}
