package com.example.finalapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AsynchronousTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {

        boolean status = false;
        Socket sock = new Socket();

        try {

            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);

            status =  sock.isConnected();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return status;


    }





}



