package com.luxoft.bankapp.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class BankClientMock /*extends Thread*/ implements Callable<Long> {

       Client client;
       
       public BankClientMock(Client clientParam)  {
       client = clientParam;         
       }
       public void run() {
         Socket socket = null;
         ObjectOutputStream oos = null;
             synchronized(this) 
             {    
               try {    
                   socket = new Socket("localhost",BankServerThreaded.SERVERPORT);
                   oos = new ObjectOutputStream(socket.getOutputStream());
                   if(BankCommander.currentClient != null)
                   {
                         oos.writeInt(2); // call withdrawMoney() from ServerThreaded
//                  try                     { client.getActiveAccount().withdraw(1f); } 
//                  catch (BankException e) { e.getMessage(); e.printStackTrace(); }
                   }
                   }
                catch (IOException e) {}
                finally {try {
                             oos.close(); socket.close();
                        } catch (IOException e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                        } catch (NullPointerException npe) {
                             npe.printStackTrace();
                        }
               }
             }
       }
       
       
       @Override
       public Long call() throws Exception {
         {   long startTime = System.currentTimeMillis();
               Socket socket = null;
               ObjectOutputStream oos = null;
                 synchronized(this) 
                 {    
                   try {     
                   socket = new Socket("localhost",BankServerThreaded.SERVERPORT);
                   oos = new ObjectOutputStream(socket.getOutputStream());
                   if(BankCommander.currentClient != null)
                   {
                         oos.writeInt(2); // call withdrawMoney() from ServerThreaded
//                      try                     { client.getActiveAccount().withdraw(1f); } 
//                      catch (BankException e) { e.getMessage(); e.printStackTrace(); }
                         return System.currentTimeMillis() - startTime;
                   }
                  
                       }
                    catch (IOException e) {}
                    finally {try {
                             oos.close(); socket.close();  return System.currentTimeMillis() - startTime;
                        } catch (IOException e) {
                             // TODO Auto-generated catch block
                             e.printStackTrace();
                        } catch (NullPointerException npe) {
                             npe.printStackTrace();
                        }
                   }
                 }
           }
            return null;
}

}
