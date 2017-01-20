package FileReaderAndWriterHelper;

import java.io.File;

/**
 * Created by дмитрий on 15.10.2016.
 */
public class FileFinder {
    public void findFileInDirectory(String path, String find) {
        File f = new File(path);
        String[] list = f.list();
        if (list == null){
            System.out.println("Directory is empty!");
            return;
        }
        for (String file : list) {
            if (find.equals(file)) {
                System.out.println("File " + file + " was found in directory " + path);
                return;
            }
        }
    }
}