import java.util.ArrayList;
        import java.util.List;

/**
 * Created by дмитрий on 15.10.2016.
 */
abstract class InputFile {
    String path = "C:\\book\\";
    String name;
    String respName;
    List<String> header = new ArrayList<String>();

    boolean compareElementInHeader(String key) {
        return header.contains(key.toUpperCase());
    }
}
