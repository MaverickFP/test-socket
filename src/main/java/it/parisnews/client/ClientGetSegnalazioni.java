package it.parisnews.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientGetSegnalazioni {

	public static void main(String[] args) {
		
		
		try {
			
			DatagramSocket socket = new DatagramSocket();
			//Stringa del tipo TipoOperazione#Messaggio
			//se 1 -> aggiungi Segnalazione
			//se 2 -> restituisci tutte le segnalazioni
			String request = "2#";
			byte[] requestMessage = request.getBytes();			
			DatagramPacket requestPacket = 	new DatagramPacket(requestMessage, requestMessage.length, InetAddress.getLocalHost(), 30000);
			System.out.println("Invio Richiesta per ricevere tutte le segnalazioni");
			//Invio richiesta
			socket.send(requestPacket);
			
			
			
			//ora devo catturare i dati trasmessi dal server
			System.out.println("Ricevo tutte le segnalazioni dal server");
			byte[] buffer = new byte[1000];
			DatagramPacket replyPacket = new DatagramPacket(buffer, buffer.length);
			socket.receive(replyPacket);
			String reply = new String( replyPacket.getData(), replyPacket.getOffset(),replyPacket.getLength() );
			System.out.println("Segnalazioni Totali: " + reply);
			
		} catch (SocketException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}

}
