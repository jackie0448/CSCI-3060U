/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase4;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author YouCun
 */
public class Phase4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String dirName= "";
        String outPutFile= "";
        String tixDirectory= "";
        String accDirectory= "";
        ArrayList<String> trancFileList = null;
        ArrayList<String> tixFileList = null;
        ArrayList<String> accFileList = null;


        Tickets tixObj= new Tickets();
        Accounts1 accObj = new Accounts1();
        Files1 filesObj= new Files1();
        // TODO code application logic here
        filesObj.mergeTransFiles(dirName, outPutFile);

        tixObj.parseFile(tixDirectory, tixFileList);
        tixObj.parseFile(accDirectory, accFileList);
        tixObj.parseFile(outPutFile, trancFileList);
        ArrayList<String> sessionTransList= null;
        //reading through each line of merged daily transaction file
        for( int i = 0; i < trancFileList.size(); i ++){

            String transCode= "";


           String entryInMergedFile = trancFileList.get(i); //entry of Transaction file from merged transaction file
           transCode= entryInMergedFile.substring(0,2);
            if ( transCode != "00"){
                sessionTransList.add(entryInMergedFile);
                // this IF statement puts all the daily transaction files of 1 session into a sessionTransList
            }

            else{
            String sessionUserName = accObj.getUserName(entryInMergedFile); //the username that ended the session

            for (int j = 0; j < sessionTransList.size(); j++){
                 String entryWithoutTransCode= "";
                 String buyerUserName= "";
                 String entryInSessionTransList= sessionTransList.get(j);
                 String transCodeInSession= entryInSessionTransList.substring(0,2);

                  switch(transCodeInSession){
               case "01": //create

                   entryWithoutTransCode= entryInSessionTransList.substring(2,31);
                   buyerUserName = accObj.getUserName(entryInSessionTransList);
                   boolean uniqueNameTrue = accObj.uniqueUserNameCheck(buyerUserName, accFileList); //checking if newly created username is correct
                   accObj.addNewUserToAccFile(uniqueNameTrue, entryWithoutTransCode, accFileList);
                   break;

               case "02": //delete
                   buyerUserName = accObj.getUserName(entryInSessionTransList);
                   accObj.deleteUser(buyerUserName, accFileList);
                   break;

               case "03": //sell
                   entryWithoutTransCode= entryInSessionTransList.substring(3,47); //getting the daily trans file of Sell without the ID code
                   tixFileList.add(entryWithoutTransCode); //adding a new event to the ticket file list
                   break;

               case "04": //buy
                   entryWithoutTransCode= entryInSessionTransList.substring(2,31);
                   int tixQtyNeeded = accObj.getTixNeeded(entryInSessionTransList); // gets the no. of tix that buyer want to buy

                   String correspondingEntryInAccFile = null;
                   correspondingEntryInAccFile= filesObj.getCorrespondingLineInFile(sessionUserName, accFileList); //getting the buyer info in acc file
                   float buyerCredit= accObj.getCreditFromAccFile(correspondingEntryInAccFile); //getting  money that buyer currently has
                   //the above implementation assumes the buyer acc is already created.

                   String eventSellerName= entryInSessionTransList.substring(3,36);
                   String lineInTixFile= filesObj.getCorrespondingLineInFile(eventSellerName, tixFileList);
                   boolean validityCheck = tixObj.checkBuyTixValid(tixQtyNeeded, buyerCredit,lineInTixFile);
                   if (validityCheck == true){
                       tixObj.editTixTextFile(validityCheck,tixQtyNeeded, eventSellerName,lineInTixFile, tixFileList);
                       // get the new credit value for buyer

                       float newBuyerCredit= accObj.creditForBuyTrans(validityCheck, buyerCredit, tixObj.getTixPrice(lineInTixFile), tixQtyNeeded);
                       String stringBuyerCredit= accObj.convertCreditFormat(Float.toString(newBuyerCredit));
                       String convertBuyerCredit= accObj.convertCreditFormat(stringBuyerCredit); //getting the buyer credit in the correct string format
                       accObj.editCreditInAccFile(accFileList,sessionUserName ,convertBuyerCredit);
                       //get the new credit value for seller
                       String sellerName= accObj.getSellerName(entryInSessionTransList); //getting the sellername
                       String sellerEntryInAccFile= filesObj.getCorrespondingLineInFile(sellerName, accFileList); //getting the seller entry in acc file
                       float sellerCredit = accObj.getCreditFromAccFile(sellerEntryInAccFile); //getting the credit that the seller has
                       float newSellerCredit= accObj.creditForSellTrans(validityCheck, sellerCredit, tixObj.getTixPrice(lineInTixFile), tixQtyNeeded);
                        String stringSellerCredit= accObj.convertCreditFormat(Float.toString(newSellerCredit));
                        String convertSellerCredit= accObj.convertCreditFormat(stringSellerCredit); //getting seller credit in correct stirng format
                        accObj.editCreditInAccFile(accFileList,sellerName, convertSellerCredit);
                   }


                   break;

               case "05": //refund
                   String sellerName= accObj.getSellerUserNameRefund(entryInSessionTransList);
                   String buyerName= accObj.getUserName(entryInSessionTransList);
                   float refundCredit= accObj.getRefundCredit(entryInSessionTransList);
                   accObj.appendRefundCredit(sellerName, buyerName, refundCredit, accFileList);
                   break;

               case "06": //add credit

                   String userNameAddCredit= accObj.getUserName(entryInSessionTransList);
                   float creditToAdd= accObj.getBuyerCreditFromTransFile(entryInSessionTransList);
                   String userNameEntryInAccFile= accObj.getCorrespondingLineInParseAccFile(userNameAddCredit, accFileList); //gets the user info in acc file
                   float userCurrentCredit= accObj.getCreditFromAccFile(userNameEntryInAccFile);
                   String userNewCredit= Float.toString(accObj.addCreditToUser(creditToAdd,userCurrentCredit));
                   String convertedUserCreditFormat= accObj.convertCreditFormat(userNewCredit);
                   accObj.editCreditInAccFile(accFileList, userNameAddCredit, convertedUserCreditFormat);
                   break;

           }

          }
           sessionTransList= null;

        }
        }
    }

}
