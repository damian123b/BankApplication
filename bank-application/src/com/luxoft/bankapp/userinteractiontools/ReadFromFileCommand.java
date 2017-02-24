package com.luxoft.bankapp.userinteractiontools;

import java.io.IOException;

import com.luxoft.bankapp.service.BankFeedService;

public class ReadFromFileCommand implements Command{

	@Override
	public void execute() {
		BankFeedService bfs = new BankFeedService();
		try{
		bfs.loadFeed("C:/Users/damian/Desktop/DaneKlientow"); }
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void printCommandInfo() {
		System.out.println("read data from text file(s)");		
	}

}
