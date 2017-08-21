package snikolas.hortikulturnimonitor.service;

public class BluetoothNotEnabledException extends RuntimeException
{

	public BluetoothNotEnabledException()
	{
		super();
	}
	
	public BluetoothNotEnabledException(String msg)
	{
		super(msg);
	}
	
}
