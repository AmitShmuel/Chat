package com.abelski.networking.chat;

public class ClientDescriptor implements StringConsumer, StringProducer
{
	
	private MessageBoard mb;
	
	public void removeFromMb(ConnectionProxy cp)
	{
		mb.removeConsumer(cp);
	}
	
	@Override
	public void addConsumer(StringConsumer sc) {
		mb = (MessageBoard) sc;
	}

	@Override
	public void removeConsumer(StringConsumer sc) {
		
	}

	@Override
	public void consume(String str) {
		
		mb.consume(str);
	}
}
