package com.abelski.networking.chat;

import java.util.*;

public class MessageBoard implements StringConsumer, StringProducer
{

	private List<ConnectionProxy> proxies = new ArrayList<>();
	
	@Override
	public void addConsumer(StringConsumer sc) {
		
		proxies.add((ConnectionProxy) sc);
	}

	@Override
	public void removeConsumer(StringConsumer sc) {
		proxies.remove((ConnectionProxy) sc);
	}

	@Override
	public void consume(String str) {
		
		for(int i = 0; i < proxies.size(); ++i)
		{
			proxies.get(i).consume(str);
		}
	}
}