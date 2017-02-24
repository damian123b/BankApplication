package com.luxoft.bankapp.networking;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.luxoft.bankapp.userinteractiontools.BankCommander;

public class BankServerThreadedTest {
//@Test 
      public static void main(String...a) throws InterruptedException, ExecutionException {
    	  
    	  int counterOfFinishedThreads = 0;
       
      ThreadPoolExecutor ex = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
      Thread t = new Thread(new BankServerThreaded());
      System.out.println("Starting test...");
      t.start();
      try {t.join();}
      catch (InterruptedException e1) {e1.printStackTrace();}
      System.out.println("current client="+BankCommander.currentClient);
      float balanceOnStart = BankCommander.currentClient.getActiveAccount().getBalance();
      BankClientMock mock = new BankClientMock(BankCommander.currentClient);
      
//      for(int i=0; i < 1000; i++) {mock.start();}
//      for(int i=0; i < 1000; i++) {
//                  try {  mock.join();}
//                  catch (InterruptedException e) {e.printStackTrace();}
//                  }
//      float balanceOnExit = BankCommander.currentClient.getActiveAccount().getBalance();
//      System.out.println("On start = "+balanceOnStart+"; on exit = "+balanceOnExit);
      
      
      
      
     // Assert.assertTrue(balanceOnExit == balanceOnStart - 1000);
      List<Future <Long>> checkList = new ArrayList<>();
      for(int i=0; i < 1000; i++)
      {
         Future<Long> future = ex.submit(new BankClientMock(BankCommander.currentClient));
         checkList.add(future);
         if(future.isDone()) counterOfFinishedThreads++;
      }
      
      System.out.println("Number of thread finished: "+counterOfFinishedThreads);
      long sum = 0L;
		if (counterOfFinishedThreads == 1000) {

			for (Future<Long> threadWaited : checkList) {
				if (threadWaited.isDone()) {
					sum += threadWaited.get();
				}
			}
		}
        double averageTime = sum/1000.0;
        float balanceOnExit = BankCommander.currentClient.getActiveAccount().getBalance();
        System.out.println("On start = "+balanceOnStart+"; on exit = "+balanceOnExit);
        System.out.println("average work time="+averageTime);
       } // end of testuj method 
     
       }

//Executor ex = Executors.newFixedThreadPool(10);
//
//float balanceOnStart = BankCommander.currentClient.getActiveAccount().getBalance();
//            BankClientMock mock = new BankClientMock();
//Thread clientMockThread = null;
//
//for(int i=0; i < 1000; i++)
//{
//     clientMockThread = new Thread(mock);
//     clientMockThread.start();               
//}
//
//for(int i=0; i < 1000; i++)
//{
//     clientMockThread.join();
//}
//
//float balanceOnExit = BankCommander.currentClient.getActiveAccount().getBalance();
//}
