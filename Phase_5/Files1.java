
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase4;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author YouCun
 */
public class Files1 {
    public void mergeTransFiles (String dirName, String outputFile) throws IOException{
        // this method merges all of the transaction files in the same directory into 1 file
        File dir = new File(dirName); //create instance of directory
        PrintWriter pw = new PrintWriter (outputFile); //create obj for printwriter for output file
        String[] fileNamesInDir = dir.list(); //putting names of all trans file into an arraylist

        for (String fileName : fileNamesInDir){
            File f = new File(dir,fileName);
            BufferedReader br = new BufferedReader(new FileReader(f)); // create object of BufferedReader

            // Read from current file
            String line = br.readLine();
            while (line != null) {
                    // write to the output file
                pw.println(line);
                line = br.readLine();
              }
            pw.flush();
        }
        System.out.println("Reading from all files" +
        " in directory " + dir.getName() + " Completed");

    }

    public String getCorrespondingLineInFile(String userName, ArrayList<String> parseList){
        //this method returns the entry in the parsed list, eg tix or accounts that belongs to the user.
           String lineInParseFile;
           for (int i = 0; i < parseList.size(); i ++){
               lineInParseFile= parseList.get(i);
               if ( lineInParseFile.contains(userName)){
                   return lineInParseFile;
               }

        }
           System.out.println(" No such entry in the list!");
           return null;
    }
}
