package com.luxoft.bankapp.networking;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class ServerThread implements Runnable {

    Socket requestSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String message;
    static final String SERVER = "localhost";
    
                public ServerThread(Socket socket) {
                               requestSocket = socket;
                }
                @Override
                public void run()
                {   try {
                    ois = new ObjectInputStream(requestSocket.getInputStream());
                    oos = new ObjectOutputStream(requestSocket.getOutputStream());
                    System.out.println("What am I to do?");
                               int option = ois.readInt();
                               while((option=ois.readInt()) != 3 ) {
//                                           System.out.println("1 - get balance");
//                                           System.out.println("2 - withdraw");
//                                           System.out.println("3 - exit");                                                 
                   switch(option)  {
                   case 1:
                                 oos.writeFloat(showBalance());
                                 break;
                   case 2:
                	            System.out.println("Withdrawing in progress...");
                                withdrawMoney(1f); 
                                break;
                               
                   case 3:
                                  //finish ???
                                  return;
                                 
                     }//end of switch
                   BankServerThreaded.getNumberOfClientsWaiting().decrementAndGet();
                               } //end of while
                }   
                 catch(IOException ioe) {}
                 finally {
                                 try {                                    
                                       ois.close(); oos.close();}
                                 catch(IOException ioe)   {}
                                 }
                }
                
                public synchronized void withdrawMoney(float amount)  {
                               try   {BankCommander.currentClient.getActiveAccount().withdraw(amount);} 
                               catch (BankException e) {e.printStackTrace();}
                }
                public float showBalance() {
                                  float howMuchLeft=0.0f;
                                  if(BankCommander.currentClient != null)  {       
                                                  howMuchLeft = BankCommander.currentClient.getActiveAccount().getBalance();
                                  System.out.println("Balance for current client is " + howMuchLeft);
                                   return howMuchLeft;
                                  }
                                  return howMuchLeft;
                }
                public static void main(String...a) throws IOException,UnknownHostException {
                	Socket s = new Socket("localhost",2005);
                	ServerThread serverthread = new ServerThread(s);
                	serverthread.run();
                }
}
 

