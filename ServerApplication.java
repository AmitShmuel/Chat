package com.abelski.networking.chat;

import java.io.IOException;
import java.net.*;

public class ServerApplication
{
	public static void main(String args[])
	{
		ServerSocket server = null;
		Socket socket = null;
		ClientDescriptor client = null;
		ConnectionProxy connection = null;
		MessageBoard mb = new MessageBoard();
		try
		{
			server = new ServerSocket(1300,5);
			while(true)
			{
				socket = server.accept();
				connection = new ConnectionProxy(socket);
				client = new ClientDescriptor();
				connection.addConsumer(client);
				client.addConsumer(mb);    
				mb.addConsumer(connection);
				connection.start();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(socket != null)
			{
				try{
					server.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
