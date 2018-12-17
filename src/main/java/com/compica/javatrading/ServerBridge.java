package com.compica.javatrading;

import org.springframework.stereotype.Component;

import com.jfx.net.JFXServer;

@Component
public class ServerBridge {
	static { System.setProperty("nj4x_activation_key", "473514972"); }
	JFXServer jfxServer;

	ServerBridge() {
		jfxServer = JFXServer.getInstance();
		System.out.println("Waiting for connection at " + JFXServer.getInstance().getBindHost() + ":"
				+ JFXServer.getInstance().getBindPort());
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
