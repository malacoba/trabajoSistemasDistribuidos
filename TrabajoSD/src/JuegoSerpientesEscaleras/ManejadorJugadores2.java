package JuegoSerpientesEscaleras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManejadorJugadores2 extends Thread{
	private Socket s;
	private static List<Jugador> listaJugadores;
	private ObjectOutputStream outSocket;
	private ObjectInputStream inSocket;
	private boolean tableroCreado ;
	private Jugador jugador;
	private Tablero tablero;
	private int turnos=0;
	private static List<Tablero> tablerosCreados= new ArrayList<Tablero>();
	private static List<Socket> clientesConectados = new ArrayList<Socket>();
	
	public ManejadorJugadores2(Socket s) {
		this.s=s;
		this.listaJugadores = new ArrayList<Jugador>();
		this.tableroCreado=false;
		try {
			this.outSocket =new ObjectOutputStream(s.getOutputStream());
			this.inSocket= new ObjectInputStream(s.getInputStream());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		this.turnos++;
		this.clientesConectados.add(s);
	}
	public void run() {
		try {
			String linea = (String)inSocket.readObject();
			System.out.println(linea);
			Jugador j = (Jugador) inSocket.readObject();
			this.listaJugadores.add(j);
			String linea2 =(String) inSocket.readObject();
			Integer i = (Integer) inSocket.readObject();
			switch(i) {
			case 1: 
				Tablero tableroNuevo = new Tablero();
				tablerosCreados.add(tableroNuevo);
				tableroNuevo.anyadirJugador(j);
				outSocket.writeObject("¿Desea jugar solo?");
				outSocket.flush();
				String juegoSolo = (String) inSocket.readObject();
				this.tablero = tableroNuevo;
				jugarSolo(j);
				break;
			case 2: 
				outSocket.writeObject("Hay " + this.tablerosCreados.size() + " tableros disponibles");
				outSocket.flush();
				for(int m =0; m< this.tablerosCreados.size();m++) {
					outSocket.writeObject(this.tablerosCreados.get(m));
				}
				outSocket.flush();
				Integer numMesa =(Integer)inSocket.readObject();
				this.tablero = this.tablerosCreados.get(numMesa);
				this.tablero.anyadirJugador(j);
				j.setTurno(turnos);
				outSocket.writeObject("Tu turno es" + turnos);
				outSocket.writeObject("¿Desea comenzar la partida?");
				outSocket.flush();
				String comenzar = (String)inSocket.readObject();
				
				jugarVarios(this.tablero);
				
				
				break;
			case 3: 
				Tablero t = new Tablero();
				this.tablerosCreados.add(t);
				t.anyadirJugador(j);
				System.out.println(t.toString());
				outSocket.writeObject(t);
				outSocket.flush();
				boolean esperar = true;
				while(esperar) {
					if(t.getListaJugadores().size()<2) {
						
					}else {
						jugarVarios(t);
						esperar = false;
						outSocket.writeObject("EMPEZAR");
						
					}
				}
				
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void jugarSolo(Jugador j) {
		try {
			outSocket.writeObject("Ha iniciado una partida solo");
			outSocket.writeObject("Tu casilla inicial es" +j.getPosicion());
			outSocket.flush();
			boolean juegoT=this.tablero.isJuegoTerminado();
			while(!juegoT) {
				boolean dado=false;
				while(!dado) {
					outSocket.writeObject("Pulsa 1 para tirar del dado");
					outSocket.flush();
					Integer i = (Integer) inSocket.readObject();
					if(i==1) {
						dado =true;
					}
				}
				Random r = new Random();
				int num = r.nextInt(1, 6);
				outSocket.writeObject("Te ha salido un" + num);
				outSocket.flush();
				if(this.tablero.setPosicion(j, num) !=100) {
					outSocket.writeObject("Avanzas a la casilla " + this.tablero.setPosicion(j, num));
					outSocket.flush();
					
				}else {
					juegoT=true;
				}
				
			}
			outSocket.writeObject("Has terminado el juego");
			outSocket.writeObject("DESCONECTAR");
			outSocket.flush();
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void jugarVarios(Tablero t) {
		try {
			boolean juegoT= t.isJuegoTerminado();
			while(!juegoT) {
				for(Jugador j: t.getListaJugadores()){
					
					outSocket.writeObject("Turno del jugador "+j.getTurno());
					outSocket.writeObject("Estas en la posicion " + j.getPosicion());
					boolean dado=false;
					while(!dado) {
						outSocket.writeObject("Pulsa 1 para tirar del dado");
						outSocket.flush();
						Integer i;
						i = (Integer) inSocket.readObject();
						if(i==1) {
							dado =true;
						}
					}
					Random r = new Random();
					int num = r.nextInt(1, 6);
					outSocket.writeObject("Te ha salido un" + num);
					outSocket.flush();
					if(this.tablero.setPosicion(j, num) !=100) {
						outSocket.writeObject("Avanzas a la casilla " + this.tablero.setPosicion(j, num));
						outSocket.flush();
						
					}else {
						juegoT=true;
					}
				
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
