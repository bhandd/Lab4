package grupp.lab4;

import java.io.*;
import java.util.List;

public class SudokuFileIO {



        /**
         * to store the game in serialized form.
         *
         */
        //TODO:
        public static void serializeToFile(File file, Bord sudokuBord) throws IOException {

            ObjectOutputStream out = null;
            try{
                out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(sudokuBord);
            }
            finally {
                if(out != null){
                    out.close();
                    // ...
                    // and then, make sure the file always get closed
                }
            }
        }

        /**
         * Call this method at startup of the application, to deserialize the users and
         * from file the specified file.
         */
        @SuppressWarnings("unchecked")
        public static Bord deSerializeFromFile(File file) throws IOException, ClassNotFoundException {

            ObjectInputStream in = null;
            try{
                in = new ObjectInputStream(new FileInputStream(file));
                Bord sudokuBord = (Bord) in.readObject();
                return sudokuBord;
            }finally {
                if (in != null){
                    in.close();

                }
            }
            //   return null;
        }

        private SudokuFileIO() {}
    }

