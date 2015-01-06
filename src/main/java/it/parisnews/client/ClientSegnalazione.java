package it.parisnews.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientSegnalazione {

	public static void main(String[] args) {
		
		
		try {
			
			DatagramSocket socket = new DatagramSocket();
			//Stringa del tipo TipoOperazione#Messaggio
			//se 1 -> aggiungi Segnalazione
			//se 2 -> restituisci tutte le segnalazioni
			String request = "1#Corso Matteotti::marciapiedi sbrecciato al civ 23::2013-06-11";
			byte[] requestMessage = request.getBytes();			
			DatagramPacket requestPacket = 	new DatagramPacket(requestMessage, requestMessage.length, InetAddress.getLocalHost(), 30000);
			socket.send(requestPacket);
			//Richiesta Inviata
			
		} catch (SocketException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}/*fine main*/

}
