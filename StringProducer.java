package com.abelski.networking.chat;

public interface StringProducer
{
	public void addConsumer(StringConsumer sc);
	
	public void removeConsumer(StringConsumer sc);
}
