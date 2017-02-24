package com.luxoft.bankapp.networking;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Set;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

public class BankClient {

	Socket requestSocket;
	String message;
	ObjectInputStream ois = null;
	static final String SERVER = "localhost";

	@SuppressWarnings("unchecked")
	void run() throws ClassNotFoundException, IOException {
		int choice = 0;
		Scanner scanner = new Scanner(System.in);
		String nameToSearchFor;
		try {
			// requestSocket = new Socket(SERVER, 2005);
			// System.out.println("Connected to localhost in port 2005");
			// out = new ObjectOutputStream(requestSocket.getOutputStream());
			// out.flush();
			// in = new ObjectInputStream(requestSocket.getInputStream());

			while (choice != 4) {

				requestSocket = null;
				requestSocket = new Socket(SERVER, 2005);
				System.out.println("Connected to localhost in port 2005");
				DataOutputStream dos = new DataOutputStream(requestSocket.getOutputStream());
				dos.flush();
				DataInputStream dis = new DataInputStream(requestSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(requestSocket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(requestSocket.getInputStream());

				System.out.println("1 - show statistics (= all the clients)");
				System.out.println("2 - find client and show info");
				System.out.println("3 - add client");
				System.out.println("4 - exit");
				System.out.print("Enter number: ");

				choice = scanner.nextInt();
				scanner.nextLine();
				switch (choice) {
				case 1:
					dos.writeInt(1);
					Set<Client> receivedSet = (Set<Client>) ois.readObject();
					for (Client k : receivedSet) {
						System.out.println(k);
					}
					break;

				case 2: // find client
					dos.writeInt(2);
					System.out.print("Enter name to search (case sensitive, salutation allowed):");
					nameToSearchFor = scanner.nextLine();
					dos.flush();
					dos.writeUTF(nameToSearchFor);
					Client cl;
					if ((cl = (Client) ois.readObject()) == null) {
						System.out.println("Client " + nameToSearchFor + " not found!");
					} else {
						System.out.println("" + cl);
					}
					break;
				case 3: // add client
					dos.writeInt(3);
					scanner.nextLine();
					System.out.println("Enter client's name:");
					String whichName = scanner.nextLine();
					System.out.println("Enter client's sex: \"1\" for male and \"2\" for female");
					int whichGender = scanner.nextInt();
					System.out.println("Enter initial Overdraft:");
					float whichOverdraft = scanner.nextFloat();
					scanner.nextLine(); // to discard end of line character from
										// last input!
					System.out.println("Enter your email:");
					String whichEmail = scanner.nextLine();

					String cityName;
					System.out.println("Enter your city:");
					cityName = scanner.nextLine();

					String whichPhoneNumber;
					do {
						System.out.println("Enter your phone number(numbers only!):");
						whichPhoneNumber = scanner.nextLine();
					}

					while (!whichPhoneNumber.matches("[0-9]*"));
					System.out.println("Phone number" + whichPhoneNumber + " ok!");

					cl = new Client(-1, whichName, (whichGender == 1) ? Gender.MALE : Gender.FEMALE, cityName,
							whichEmail, whichPhoneNumber);
					oos.writeObject(cl);
					String clientInfo = dis.readUTF();

					System.out.println(clientInfo);

					break;
				case 4:
					try {
						dos.writeInt(4);
						scanner.close();
						ois.close();
						oos.close();
						requestSocket.close();
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
					System.exit(0);

				}
			}
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} /*
			 * finally { // 4: Closing connection try { in.close(); out.close();
			 * //requestSocket.close(); } catch (IOException ioException) {
			 * ioException.printStackTrace(); } }
			 */
	}

	public static void main(final String args[]) throws ClassNotFoundException, IOException {
		BankClient client = new BankClient();
		client.run();
	}

}