package grupp.lab4;

import java.io.*;


public class SudokuFileIO {


    /**
     * to store the game in serialized form.
     */
    //TODO:
    public static void serializeToFile(File file, Bord sudokuBord) throws IOException {
//ORIGINAL
 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        try {
            out.writeObject(sudokuBord);

        } catch (IOException IOEx) {
            throw new IOException("Something went wrong when writing to fille");
        }finally {
            out.close();
        }
        //TODO: close file


    }

    /**
     * Call this method at startup of the application, to deserialize the users and
     * from file the specified file.
     */


    //TODO: Kolla om alla catch-block verkligen behövs och hantera exceptions
    public static Bord deSerializeFromFile(File file) throws IOException, NullPointerException {

        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream(file));
            Bord bord = (Bord) in.readObject();
            return bord;
        }catch (IOException e) {
            throw new IOException(); //throws exeption to calling method
        }
//        } catch (EOFException exception) {
//        return null;
//

//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);}
         catch (NullPointerException exception) {
           // exception.printStackTrace(); //printstacktrace används För felsökning
             throw exception;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
                if (in != null)
                    in.close();
        }

    }


    private SudokuFileIO() {
    }
}

