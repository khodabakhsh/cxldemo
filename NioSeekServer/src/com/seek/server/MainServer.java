package com.seek.server;

import com.seek.server.network.SeekServer;

public class MainServer {

	public static void main(String... args) {
		new SeekServer().start();
	}
}
