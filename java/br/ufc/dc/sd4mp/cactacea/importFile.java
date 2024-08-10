package br.ufc.dc.sd4mp.cactacea;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class importFile{

    private ArrayList<String[]> fullFile = new ArrayList<>();


    public importFile(String fileName, Context context){
        //	FileReader file;
        InputStream inputStream;
        AssetManager assetManager = context.getAssets();

        try{

            inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            // byte buffer into a string
            String text = new String(buffer);
            String [] lines = text.split("\n");

            Log.v("SQL REads", "create id = " + lines[0]);
            for ( String line : lines ){
                String [] partsLine = line.split(";");
                fullFile.add(partsLine);
            }

        }catch( IOException e){

            Log.e("Erro Criacao", "Arquivo nao encontrado");
        }
        catch(NullPointerException e ){
            System.out.println("Arquivo Acabou");
        }

    }

    public ArrayList<String[]> getFullFile() {
        return fullFile;
    }


}
