package com.iancl.mywallet.utility;

import android.content.Context;

import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.view.CoinAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by iancl on 05/12/2017.
 */

public class FileUtility {

    public static Context context;

    /**
     * Method to get in List all the currencies in the file
     * @return A list of all the coins of the user
     */
    public static List<Coin> getCurrenciesFile()  {
        String fileContent = readFile( context.getResources().getString(R.string.FILE_DATA_CURRENCY));
        List<Coin> myList = new ArrayList<Coin>();

        if ( fileContent.length() > 0 ) {
            String[] tmpCurrencies = fileContent.split("-");
            String name, amount, imageURL;
            double price;
            // i is starting at one because the file begin with a - (the separator)
            for (int i =1; i < tmpCurrencies.length;i++) {

                String[] tmp = tmpCurrencies[i].split(":");
                name = tmp[0];
                amount = tmp[1];

                Coin coin = new Coin();
                // FILL the coin
                coin.setFullName(MainJSONParser.getCoinId(name));
                coin.setAmount(amount);
                coin.setName(name);
                URLUtility.fillDataCoin(coin); // This method fill the price and the id, of the coin.

                myList.add(coin);

            }
        }
        return myList;
    }



    /**
     * Read the file who contain the currencies of the user
     * @return
     */
    public static String readFile(String fileName) {

        String s = "";
        try {
            FileInputStream fileIn;
            fileIn = context.openFileInput(fileName);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[256];

            int charRead;
            String readString;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                readString = String.copyValueOf(inputBuffer,0,charRead);
                s +=readString;
            }
            InputRead.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * Delete  a currency of the user
     * @param coin the coin of the currency
     */
    public static void deleteCurrency(CoinAdapter.CoinViewHolder coin) {

        String fileContent = readFile( context.getResources().getString(R.string.FILE_DATA_CURRENCY));
        String[] tmp = fileContent.split("-");
        String newStr ="";
        String  coinToDell = coin.name.getText().toString()+":"+coin.amount.getText().toString();
        for (String c : tmp) {
            if ( !c.contains(coinToDell)) {
                newStr = newStr + "-" + c;
            }
        }
        newStr = newStr.substring(1,newStr.length());


        // add-write text into file
        addToFileData(newStr, context.MODE_PRIVATE, context.getResources().getString(R.string.FILE_DATA_CURRENCY));

    }


    /**
     * Write in the data file of the user
     * @param strWrite the string to write in the file
     */
    public static void addToFileData(String strWrite,int mode, String fileName )
    {

        // add-write text into file
        try {

            FileOutputStream fileout =context.openFileOutput(fileName, mode);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            if ( strWrite.charAt(0) != '-') {
                strWrite = "-".concat(strWrite);
            }

            outputWriter.write(strWrite);
            outputWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
