package grupp.lab4.model;

import grupp.lab4.model.Bord;

import java.io.*;


public class SudokuFileIO {


    /**
     * to store the game in serialized form.
     */
    //TODO:
    public static void serializeToFile(File file, Bord sudokuBord) throws IOException {
        //ORIGINAL
        //try wtih resources autoclosar outputstream
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));) {
            out.writeObject(sudokuBord);

        } catch (IOException IOEx) {
            throw new IOException("Something went wrong when writing to fille");
        }
        //TODO: close file


    }

    /**
     * Call this method at startup of the application, to deserialize the users and
     * from file the specified file.
     */


    //TODO: Kolla om alla catch-block verkligen behövs och hantera exceptions
    public static Bord deSerializeFromFile(File file) throws IOException, NullPointerException, ClassNotFoundException {

        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream(file));
            return (Bord) in.readObject();
        }catch (NullPointerException e) {
            throw new NullPointerException("Är det verkligen nödvändigt med en Alert här?"); //throws exeption to calling method

        }
         catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Could not read file, file corrupt or wrong type");
        }
        finally {
                if (in != null)
                    in.close();
        }

    }


    private SudokuFileIO() {
    }
}

