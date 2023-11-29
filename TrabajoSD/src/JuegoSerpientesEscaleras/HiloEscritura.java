package JuegoSerpientesEscaleras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HiloEscritura implements Runnable{
private Socket s;
	
	public HiloEscritura(Socket s) {
		this.s=s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			DataOutputStream dis = new DataOutputStream(s.getOutputStream());
			Scanner r = new Scanner(System.in);
			boolean seguirConectado=true;
			String m = "desconectar";
			while(seguirConectado) {
				String linea = r.nextLine();
				dis.writeBytes(linea + "\r\n");
				if(linea.contains(m)) {
					seguirConectado=false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
