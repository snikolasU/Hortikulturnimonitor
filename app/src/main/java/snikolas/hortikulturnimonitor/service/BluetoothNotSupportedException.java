package snikolas.hortikulturnimonitor.service;

public class BluetoothNotSupportedException extends RuntimeException
{

	public BluetoothNotSupportedException()
	{
		super();
	}
	
	public BluetoothNotSupportedException(String msg)
	{
		super(msg);
	}
}
