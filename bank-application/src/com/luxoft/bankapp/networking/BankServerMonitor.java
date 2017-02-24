package com.luxoft.bankapp.networking;
import static java.lang.Thread.*;

public class BankServerMonitor implements Runnable{

    @Override
	public void run() {

		while (true) {
			synchronized (this) // this or monitor?
			{
				try {
					sleep(5000);
				} catch (InterruptedException ie) {
				}
				System.out
						.println("Number of clients waiting: " + BankServerThreaded.getNumberOfClientsWaiting().get());
			}
		} // end of while
	}
}
