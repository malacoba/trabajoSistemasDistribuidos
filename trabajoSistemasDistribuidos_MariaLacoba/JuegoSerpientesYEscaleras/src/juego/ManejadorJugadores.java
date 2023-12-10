//María Lacoba Martinez
package juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class ManejadorJugadores extends Thread{
	//La clase Manejador de Jugadores es como el "Atender Peticion" de las practicas 
	//tiene un tablero que es con el que se jugara y un listado con informacion de los 
	//jugadores para ir obteniendo sus outputs e inputs
	private List<InformacionJugador> listaJugadoresS;
	private Tablero t;
	
	//constructor con parametros, list es una lista de informacion de jugadores inicializada previamente
	public ManejadorJugadores(List<InformacionJugador> list) {
		this.listaJugadoresS=list;
		Tablero t = new Tablero();
		this.t=t;
		for(int i =0; i<listaJugadoresS.size();i++) {
			Jugador j = new Jugador(listaJugadoresS.get(i).getNombre(),i);
			t.anyadirJugador(j);
		}
	}
	//funcion run ya que la clase extiende de thread
	public void run() {
		jugar(this.t);
	}
	//funcion que va recorriendo la lista de jugadores con sus input y output para ir mandando a cada jugador,cuando
	//sea su turno,las información del juego
	public void jugar(Tablero t) {
		try {
			boolean isTerminado = t.isJuegoTerminado();
			while(!isTerminado) {
				for(int i =0; i < t.getNumJugadores(); i++) {
					Jugador j = t.getListaJugadores().get(i);
						DataOutputStream oos = listaJugadoresS.get(i).getOutput();
						DataInputStream ois = listaJugadoresS.get(i).getInput();
						oos.writeBytes("Es tu turno" + "\r\n");
						oos.writeBytes("Estas en la posición " +j.getPosicion()+"\r\n");
						oos.flush();
						int dado =0;
						while(dado !=1) {
							oos.writeBytes("Pulsa 1 para tirar del dado"+"\r\n");
							oos.flush();
							dado = Integer.valueOf(ois.readLine());
						}
						Random r = new Random();
						int d = r.nextInt(1,6);
						oos.writeBytes("Has obtenido un " + d + "\n");
						int pos = j.getPosicion() + d;
						if(t.isEscalera(pos)) {
							pos = t.devuelveFin(pos);
							oos.writeBytes("Has caido en una escalera, avanzas hasta la posición " + pos+ "\n");
							t.setPosicion(j, pos);
							oos.flush();
						}else if(t.isSerpiente(pos)) {
							pos= t.devuelveCola(pos);
							oos.writeBytes("oooh, has caido en una serpiente, vuelves a la posición " + pos+ "\n");
							j.setPosicion(pos);
							t.setPosicion(j, pos);
							oos.flush();
						}else {
							oos.writeBytes("Avanzas a la posición " + pos+ "\n");
							t.setPosicion(j, d);
							oos.flush();
						}
						if(pos==100) {
							oos.writeBytes("Juego terminado"+ "\n");
						}
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	
	}
}
