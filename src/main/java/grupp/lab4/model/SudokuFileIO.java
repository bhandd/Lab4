package grupp.lab4.model;

import java.io.*;


public class SudokuFileIO {


    /**
     * to store the game in serialized form.
     */

    public static void serializeToFile(File file, Bord sudokuBord) throws IOException {

        //try wtih resources (autoclose outputstream)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(sudokuBord);

        } catch (IOException IOEx) {
            throw new IOException("Something went wrong when writing to file");
        }
    }

    /**
     * Call this method at startup of the application, to deserialize the users and
     * from file the specified file.
     */


    //TODO: Kolla om alla catch-block verkligen behövs och hantera exceptions
    public static Bord deSerializeFromFile(File file) throws IOException, NullPointerException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Bord) in.readObject();
        } catch (EOFException exception) {
            throw new EOFException("Could not read file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read file, file corrupt or wrong type");
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong when loading file(not really necessary...)");
        }

    }


    private SudokuFileIO() {
    }
}

