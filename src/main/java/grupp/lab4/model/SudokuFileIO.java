package grupp.lab4.model;

import java.io.*;


public class SudokuFileIO {
    private SudokuFileIO() {
    }

    /**
     * Serializes the specified `Bord` object to the specified `File`.
     *
     * @param file The file to which the `Bord` object will be serialized.
     * @param sudokuBord The `Bord` object to be serialized.
     * @throws IOException Thrown if an error occurs during serialization.
     */

    public static void serializeToFile(File file, Bord sudokuBord) throws IOException {

        //try wtih resources (autoclose outputstream)
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(sudokuBord);
        } catch (IOException e) {
            throw new IOException("Something went wrong when writing to file");
         //   e.printStackTrace();
        }
    }



    /**
     * Deserializes the specified `File` to a `Bord` object.
     *
     * @param file The file from which the `Bord` object will be deserialized.
     * @return The deserialized `Bord` object.
     * @throws IOException Thrown if an error occurs while reading from the file.
     * @throws NullPointerException Thrown if the specified `file` is null.
     * @throws ClassNotFoundException Thrown if the class of the deserialized object cannot be found.
     */
    public static Bord deSerializeFromFile(File file) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Bord) in.readObject();
        } catch (EOFException exception) {
            throw new EOFException("Could not read file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not read file, RuntimeException");
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong when loading file(not really necessary...)");
        }catch (IOException e) {
            throw new RuntimeException("Could not read file, file corrupt or wrong type");
        }
    }
}

