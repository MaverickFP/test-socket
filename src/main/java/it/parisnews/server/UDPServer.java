package it.parisnews.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

	public static void main(String[] args) {
		
		try {
			
			DatagramSocket socket = new DatagramSocket(30000);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024]; 
			
			System.out.println("Avvio Server");
			while(true){
				
				DatagramPacket receiving_packet = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receiving_packet);
				String request = new String(receiving_packet.getData(), receiving_packet.getOffset(), receiving_packet.getLength());
				analizzaRichiesta(request, receiving_packet, socket);
				
				
				
			}/*fine while*/
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*fine catch*/
		

	}/*fine main*/
	
	
	public static void analizzaRichiesta(String s, DatagramPacket receiving_packet, DatagramSocket socket){
		System.out.println("Analizzo Richiesta");
		System.out.println("Richiesta: " + s);
		String[] parts = s.split("#");
		String decisore = parts[0];
		
		if(decisore.equals("1")){
			String msg = parts[1];
			addSegnalazione(msg);
		}
			
		if(decisore.equals("2"))	
			getSegnalazioni(receiving_packet,socket);
	}
	
	
	
	public static void addSegnalazione(String s){
		System.out.println("Richiesta di tipo addSegnalazione");
		FileOutputStream file;
		try {
			
			file = new FileOutputStream("tests.txt", true);
			PrintStream scrittore = new PrintStream(file);
	        //scrittore.append(s);
			scrittore.println(s);
	        	       
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
	}/*fine addSegnalazione*/
	
	
	public static void getSegnalazioni(DatagramPacket receiving_packet, DatagramSocket socket){
		System.out.println("Richiesta di tipo getSegnalazioni");
		String output = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("tests.txt"));
			String linea = reader.readLine();
	        while(linea!=null) {
	        	output = output + linea + "\n";
	        	linea = reader.readLine();
	        }/*fine while lettura file*/
	        reader.close();
	        byte[] replyMessage = output.getBytes();
	        DatagramPacket replyPacket =  new DatagramPacket( replyMessage, replyMessage.length, receiving_packet.getAddress(),receiving_packet.getPort() );
	        socket.send(replyPacket);
		} catch (IOException e) {			
			e.printStackTrace();
		}
        

		
	}
	

}
