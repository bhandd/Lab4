package grupp.lab4;

import java.io.*;
import java.util.List;

public class SudokuFileIO {
//
//    public class ProjectsFileIO {
//
//        /**
//         * to store the game in serialized form.
//         *
//         */
//        //TODO: Ska vi ha en lista, string eller en intarray?
//        public static void serializeToFile(File file, List<project> data) throws IOException {
//
//            ObjectOutputStream out = null;
//            try{
//                out = new ObjectOutputStream(new FileOutputStream(file));
//                out.writeObject(data);
//            }
//            finally {
//                if(out != null){
//                    out.close();
//                    // ...
//                    // and then, make sure the file always get closed
//                }
//            }
//        }
//
//        /**
//         * Call this method at startup of the application, to deserialize the users and
//         * from file the specified file.
//         */
//        @SuppressWarnings("unchecked")
//        public static List<Project> deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
//
//            ObjectInputStream in = null;
//            try{
//                in = new ObjectInputStream(new FileInputStream(file));
//                List<Project> projects = (List<Project>) in.readObject();
//                return projects;
//            }finally {
//                if (in != null){
//                    in.close();
//                    // ...
//                    // and then, make sure the file always get closed
//                }
//            }
//            //   return null;
//        }
//
//        private ProjectsFileIO() {}
//    }
}
