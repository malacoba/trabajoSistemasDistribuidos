package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Cliente {
	
	public static void main(String[]args) {
		try(Socket cliente = new Socket("localhost",8000)){
			ExecutorService ex = Executors.newFixedThreadPool(2);
			HiloEscritura he = new HiloEscritura(cliente);
			HiloLectura hl = new HiloLectura(cliente);
			ex.execute(he);
			ex.execute(hl);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
