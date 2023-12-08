package JuegoSerpientesEscaleras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Tablero implements Serializable{
	private int[] mesa; 
	private static final int tam=100;
	
	
	private Map<Integer,Integer> serpientes;
	private Map<Integer,Integer> escaleras;
	
	private List<Jugador> jugadores;
	
	private boolean juegoTerminado;
	
	public Tablero() {
		this.mesa = new int[tam];
		this.serpientes=new HashMap<Integer,Integer>();
		this.escaleras=new HashMap<Integer,Integer>();
	
		this.juegoTerminado = false;
		this.jugadores = new ArrayList<Jugador>();
		
		
		//creación de serpientes 
		int cabeza1 = 27;
		int cabeza2 = 43;
		int cabeza3 = 54;
		int cabeza4 = 76;
		int cabeza5 = 89;
		int cabeza6 = 99;
		int cola1 = 5;
		int cola2 = 18;
		int cola3 = 31;
		int cola4 = 58;
		int cola5 = 53;
		int cola6= 41;
		this.serpientes.put(cabeza1, cola1);
		this.serpientes.put(cabeza2, cola2);
		this.serpientes.put(cabeza3, cola3);
		this.serpientes.put(cabeza4, cola4);
		this.serpientes.put(cabeza5, cola5);
		this.serpientes.put(cabeza6, cola6);
		
		//creacion de escaleras
		int pr1=4;
		int pr2=33;
		int pr3= 42;
		int pr4= 50;
		int pr5= 62;
		int pr6=74;
		int fin1= 25;
		int fin2=49;
		int fin3=63;
		int fin4=69;
		int fin5=81;
		int fin6=92;
		this.escaleras.put(pr1, fin1);
		this.escaleras.put(pr2, fin2);
		this.escaleras.put(pr3, fin3);
		this.escaleras.put(pr4, fin4);
		this.escaleras.put(pr5, fin5);
		this.escaleras.put(pr6, fin6);
		
		
	}
	
	public boolean isSerpiente(int casilla) {
		if(this.serpientes.containsKey(casilla)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isEscalera(int casilla) {
		if(this.serpientes.containsKey(casilla)) {
			return true;
		}else {
			return false;
		}
	}
	
	public int devuelveCola(int cabeza) {
		return(this.serpientes.get(cabeza));
	}
	
	public int devuelveFin(int principio) {
		return(this.escaleras.get(principio));
	}
	
	public void anyadirJugador(Jugador j) {
		this.jugadores.add(j);
	}
	public List<Jugador> getListaJugadores() {
		return this.jugadores;
	}
	
	public String toString() {
		int numJugadores = this.jugadores.size();
		String s = "En la mesa hay " + numJugadores + "\r\n";
		for(int i =0; i< numJugadores ;i++) {
			s+= "El jugador/a " + this.jugadores.get(i).getNombre() + " está en la posicion " + this.jugadores.get(i).getPosicion() + "\r\n"; 
		}
		return s;
	}
	
	public int setPosicion(Jugador j, int n) {
		if(this.jugadores.contains(j)) {
			int pos = j.getPosicion();
			if(pos + n < 100) {
				mesa[pos] = mesa[pos+n];
				j.setPosicion(pos +n);
				return(j.getPosicion());
			}else if(j.getPosicion() + n ==100) {
				this.juegoTerminado=true;
				return(100);
			}else {
				mesa[pos] = mesa[100-n];
				j.setPosicion(100-n);
				return(j.getPosicion());
			}
		}else {
			return 0;
		}
		
	}
	
   
	public boolean isJuegoTerminado() {
		return this.juegoTerminado;
	}
	
}


