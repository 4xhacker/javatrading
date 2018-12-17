package com.compica.javatrading;

import com.jfx.net.JFXServer;

public class ForexServer {
	static { System.setProperty("nj4x_activation_key", "473514972"); }

	public static void main(String[] args) {
		try {
			System.setProperty( "jfx_activation_key", "473514972" ); //not my real number... just to show...
			System.setProperty( "jfx_server_host", "127.0.0.1" );
			System.setProperty( "jfx_server_port", "7778" );
			
			JFXServer.getInstance();
			System.out.println( "Waiting for incoming connection at " + JFXServer.getInstance().getBindHost() + ":" + JFXServer.getInstance().getBindPort() );
			Thread.sleep( Integer.MAX_VALUE );
			
		} catch( Exception e ) {
			//e.printStackTrace();
		}

	}

}
