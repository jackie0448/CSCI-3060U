/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;


public class Tickets {

    public void parseFile(String tixDirectory, ArrayList<String> updatedParseTixList) throws IOException{
        //this method parse the .output files in the directory and converts them into string arraylist
        BufferedReader reader;
        ArrayList<String> parseTixList= new ArrayList<String>();
        try {
            reader = new BufferedReader( new FileReader(tixDirectory));
            String line = reader.readLine();
            while( line!= null){
                parseTixList.add(line);
                line = reader.readLine();
            }
            reader.close();
            updatedParseTixList= parseTixList;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public boolean checkBuyTixValid(int tixQtyNeeded, float userCredit,String lineInTixParseFile){
        //this method checks if a buyer has enough credits to buy tix for an event
        boolean buyTrancValid = false;


            int tixQtyInTixFile = getQty(lineInTixParseFile);
            int tixPrice = getTixPrice(lineInTixParseFile);


                if ((tixQtyNeeded > tixQtyInTixFile) && (userCredit< (tixQtyNeeded*tixPrice)) ){
                    System.out.println("There is not enough tickets to sell to the customer");
                    //break; //maybe we should not use break?
                }
                else if ((tixQtyNeeded < tixQtyInTixFile) && (userCredit> (tixQtyNeeded*tixPrice))){
                    buyTrancValid= true;
            }


       return buyTrancValid;

    }
    public void editTixTextFile(boolean buyTranValid,int tixQtyNeeded, String eventSellerName,String lineInTixParseFile ,ArrayList<String> tixFileList){
        /*this method will create a new tix file entry with the correct tix qty ect, remove the corresponding entry and append
        the new entry to tixFile*/
        String newTixLineToRemove;
        if(buyTranValid == true){
                    String newTixQtyAvail= Integer.toString(getQty(lineInTixParseFile) - tixQtyNeeded); // getting the remaining tix avail after transaction
                    String newTixQtyAppend= "";
                    if ( newTixQtyAvail.length()< 3){
                        for ( int i = 0; i < (3-newTixQtyAvail.length()); i ++){
                            newTixQtyAppend+='0'; // returning the quantity in string format based on the format stated eg 003
                        }
                        newTixQtyAppend+=newTixQtyAvail;
               }else{
                        newTixQtyAppend+=newTixQtyAvail; // if the quantity is eg 123
                    }

                for(int i = 0; i < tixFileList.size(); i++){

               if ( tixFileList.get(i).contains(eventSellerName)){
                   newTixLineToRemove= tixFileList.get(i);

                   String x = tixFileList.get(i).substring(0, 35) + newTixQtyAppend + tixFileList.get(i).substring(38, 45);
                   tixFileList.remove(newTixLineToRemove); // cuz string cannot be replaced explicitly, so need to remove and add the one to replace
                   tixFileList.add(x);

               }


        }
    }
    }



    public void checkNegTix(ArrayList<String> updatedParseTixList){
        // this method checks that there is no negativetickets in the tixList
        for ( int i = 0; i < updatedParseTixList.size(); i++){
            String eventNameinTixFile= null;
            int tixStartIndex= 35;
            eventNameinTixFile = updatedParseTixList.get(i);
             int j = 3;
                while(j>0){

                    if (eventNameinTixFile.charAt(35) == '0'){
                        j--;
                        tixStartIndex++;
                    }
                    else{
                        j= 0; //might have error here
                    }
                }
             int tixQtyInFile = Integer.parseInt(eventNameinTixFile.substring(tixStartIndex,38));
             if (tixQtyInFile < 0){
                 System.out.println("error Message");
             }

          }
    }
    public int getQty(String lineInTixParseFile){
//returns the available tix qty for a event in the tix Transaction file
         int tixStartIndex= 35;
         int tixQtyInFile = 0;
            if (lineInTixParseFile != null){
                int j = 3;
                while(j>0){

                    if (lineInTixParseFile.charAt(35) == '0'){
                        j--;
                        tixStartIndex++;
                    }
                    else{
                        j= 0; //might have error here
                    }

                }
                tixQtyInFile = Integer.parseInt(lineInTixParseFile.substring(tixStartIndex,38));
                return tixQtyInFile;
              }
           System.out.println("no such event found");
          return tixQtyInFile;
       }
    public int getTixPrice (String lineInTixParseFile){
//returns the price per tix for a event in the tix Transaction file
        int priceStartIndex= 39;
         int tixPrice = 0;
                int k = 6;
                if(lineInTixParseFile != null){

                while(k>0){

                    if (lineInTixParseFile.charAt(39) == '0'){
                        k--;
                        priceStartIndex++;
                    }
                    else{
                        k= 0; //might have error here
                    }
                }
                tixPrice = Integer.parseInt(lineInTixParseFile.substring(priceStartIndex,44));
                return tixPrice;
              }
                else{
                    System.out.println("no such event found");
          }
        return tixPrice;
    }

    /*public String getCorrespondingLineInParseTixFile(String eventSellerName, ArrayList<String> updatedParseTixList){
           String lineInTixParseFile;
           for (int i = 0; i < updatedParseTixList.size(); i ++){
               lineInTixParseFile= updatedParseTixList.get(i);
               if ( lineInTixParseFile.contains(eventSellerName)){
                   return lineInTixParseFile;
               }

        }
           return null;
    }*/
}
