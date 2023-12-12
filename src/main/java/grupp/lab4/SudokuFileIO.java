package grupp.lab4;

import java.io.*;


public class SudokuFileIO {



        /**
         * to store the game in serialized form.
         *
         */
        //TODO:
        public static void serializeToFile(File file, Bord sudokuBord) throws IOException {

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(sudokuBord);
            } catch (IOException IOEx) {
                throw new IOException("Something went wrong when writing to fille");
            }
            // ...
            // and then, make sure the file always get closed
        }

        /**
         * Call this method at startup of the application, to deserialize the users and
         * from file the specified file.
         */


        //TODO: Kolla om alla catch-block verkligen behövs
        public static Bord deSerializeFromFile(File file) throws IOException {

            ObjectInputStream in = null;

            try{
                in = new ObjectInputStream(new FileInputStream(file));
                Bord bord = (Bord) in.readObject();
                return bord;
            } catch (EOFException exception){
                throw new EOFException("end of file");

            } catch (IOException exception) {
                throw new IOException("IOException");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException exception) {
                throw new NullPointerException("nullpointerEXCEPTION");
            }

            finally
            {
                try{
                    if(in != null)
                        in.close();
                }catch (Exception exception){}
            }
        }


        private SudokuFileIO() {}
    }

