import FileReaderAndWriterHelper.FileFinder;
import FileReaderAndWriterHelper.HelperForWorkingWithFile;
import MySQLHelper.MySqlDAO;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.util.UUID.randomUUID;

class FileLoader {
    private final static String INSERT_CLIENT = "insert into client (ClientID, Name, Phone, Status, Surname) values ('%s', '%s', '%s', '10', '%s')";
    private final static String INSERT_ACCOUNT = "insert into account (`accountID`, `Status`, `clientID`) values ('%s', '%s', (select ClientID from client where ClientID = '%s'))";
    private final static String SELECT_FROM_CLEINT = "select ClientID from task.client where ClientID = '%s'";
    private Registration reg = new Registration();
    private String incorrectHeader = "";
    private BufferedReader bufferedReader = HelperForWorkingWithFile.createReader(reg.path + reg.name);
    private BufferedWriter bufferedWriter = HelperForWorkingWithFile.createWriter(reg.path + reg.respName);

    private final static MySqlDAO mySQL = new MySqlDAO();

    void registrationClient() throws IOException {
        processFile();
        bufferedWriter.close();
    }

    private void processFile() {
        FileFinder fileFinder = new FileFinder();
        fileFinder.findFileInDirectory(reg.path, reg.name);

        String line;
        String[] header = null;
        boolean isFirstLine = true;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    header = splitHeader(line);
                    if (!validateHeader(header)){
                        bufferedWriter.write("Incorrect Header: " + incorrectHeader);
                        return;
                    }
                    isFirstLine = false;
                } else {
                    createClientAndAccountInDataBase(splitBody(line, header));
                }
            }
        } catch (IOException e) {
            System.out.println("something was wrong");
            e.printStackTrace();
        }
    }

    private void createClientAndAccountInDataBase(HashMap keyValuePair) throws IOException {
        if (checkExistingClient(keyValuePair)){
            mySQL.execute(String.format(INSERT_CLIENT, keyValuePair.get("clientID"), keyValuePair.get("name"), keyValuePair.get("phone"), keyValuePair.get("surname")));
            mySQL.execute(String.format(INSERT_ACCOUNT, randomUUID(), 10, keyValuePair.get("clientID")));
            bufferedWriter.write("Client [" + keyValuePair.get("clientID") + "] was correctly created");
            System.out.println("Client [" + keyValuePair.get("clientID") + "] was correctly created");
        }
        else {
            //TODO: do this point
        }
    }

    private HashMap splitBody(String line, String[] header) {
        String[] bodyElements = line.split(";");
        HashMap keyValuePair = new HashMap();
        for (int i = 0; i < bodyElements.length; i++) {
            keyValuePair.put(header[i], bodyElements[i]);
        }
        return keyValuePair;
    }

    private boolean checkExistingClient(HashMap keyValuePair) {
        ResultSet result = mySQL.select(String.format(SELECT_FROM_CLEINT, keyValuePair.get("clientID")));
        try {
            if (result.next()){
                String clientId = result.getString(1);
                if (clientId.equalsIgnoreCase(keyValuePair.get("clientID").toString())){
                    System.out.println("The client " + keyValuePair.get("clientID").toString() + " is already registered!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String[] splitHeader(String line){
        String[] header;
        header = line.split(";");
        return header;
    }

    private boolean validateHeader(String[] header) {
        for (String value : header) {
            if (!reg.compareElementInHeader(value)) {
                System.out.println("Header [" + value + "] not found");
                incorrectHeader = value;
                return false;
            }
        }
        return true;
    }

}
