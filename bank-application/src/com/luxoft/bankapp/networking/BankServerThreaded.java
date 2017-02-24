package com.luxoft.bankapp.networking;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded implements Runnable {
	static final int POOL_SIZE = 10;
	static final int SERVERPORT = 2005;
	protected ServerSocket serverSocket = null;
	protected Thread runningThread = null;
	Socket normalSocketForServer;
	private static boolean running;
	private static AtomicInteger numberOfClientsWaiting = new AtomicInteger(0);

	public static AtomicInteger getNumberOfClientsWaiting() {
		return numberOfClientsWaiting;
	}

	Socket connection = null;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	DataInputStream dis;
	DataOutputStream dos;
	String message;

	public boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean value) {
		running = value;
	}

	@Override
	public void run() {
		BankServerMonitor notifier = new BankServerMonitor();
		Thread t = new Thread(notifier);
		t.setDaemon(true);

		this.runningThread = Thread.currentThread();
		ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

		try {
			serverSocket = new ServerSocket(SERVERPORT);
			System.out.println("Multithreaded server started");
			BankServer.initialise();
			setRunning(true);

			while (running) {
				normalSocketForServer = null;
				try {
					normalSocketForServer = serverSocket.accept();
				} catch (IOException ioe) {
				}
				numberOfClientsWaiting.incrementAndGet();
				t.start();
				pool.execute(new ServerThread(normalSocketForServer));
			}
			pool.shutdown();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void main(String... a) {
		System.out.println(Runtime.getRuntime().availableProcessors() + " processors.");
		new BankServerThreaded().run();
	}
}
