package com.abelski.networking.chat;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer
{
    private ClientGUI gui;
    private ClientDescriptor desc;
    private Socket socket;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    
    public ConnectionProxy(Socket socket) {
	
        this.socket = socket;
        OutputStream os = null;
        InputStream is = null;
        try {
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);
            is = socket.getInputStream();
            dis = new DataInputStream(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
    @Override
    public void addConsumer(StringConsumer sc) {
		
    	if(sc instanceof ClientGUI)
    	{
    		gui = (ClientGUI) sc;
    	}
    	else
    	{
    		desc = (ClientDescriptor) sc;
    	}
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
		
    }

    @Override
    public void consume(String str) {
	
        try {
            dos.writeUTF(str);
        } catch (IOException ex) {
        	desc.removeFromMb(this);
        }
    }
	
    @Override
    public void run() {
    	
        String s = null;
        while(true)
        {
            try 
            {
            	s = dis.readUTF();
            	
            	if(gui != null)
                {
            		gui.consume(s);
                }
                if(desc != null)
                {
                	desc.consume(s);
                }
            }
            catch(IOException e)
            {
            	if(dis != null)
            	{
            		try {
						dis.close();
					} catch (IOException e1) {e1.printStackTrace();}
            	}
            	if(dos != null)
            	{
            		try {
						dos.close();
					} catch (IOException e1) {e1.printStackTrace();}
            	}
            	if(socket != null)
            	{
            		try {
						socket.close();
					} catch (IOException e1) {e1.printStackTrace();}
            	}
            }
        }
    }
}