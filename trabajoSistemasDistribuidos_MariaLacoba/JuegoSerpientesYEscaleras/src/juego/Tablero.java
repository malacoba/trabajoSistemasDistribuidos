//María Lacoba Martinez
package juego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tablero implements Serializable{
	//Tablero tiene 6 atributos, la mesa, el tamaño, dos HashMaps con las serpientes y las 
	//escaleras, una lista de jugadores y un booleano para saber si el juego ha terminado o no
	private int[] mesa; 
	private static final int tam=100;
	private Map<Integer,Integer> serpientes;
	private Map<Integer,Integer> escaleras;
	private List<Jugador> jugadores;
	private boolean juegoTerminado;
	
	//constructor sin parametros, crea un nuevo tablero con las serpientes y escaleras
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
		this.mesa[pr1]=pr1;
		this.mesa[pr2]=pr2;
		this.mesa[pr3]=pr3;
		this.mesa[pr4]=pr4;
		this.mesa[pr5]=pr5;
		this.mesa[pr6]=pr6;
		this.mesa[cabeza1]=cabeza1;
		this.mesa[cabeza2]=cabeza2;
		this.mesa[cabeza3]=cabeza3;
		this.mesa[cabeza4]=cabeza4;
		this.mesa[cabeza5]=cabeza5;
		this.mesa[cabeza6]=cabeza6;
		
	}
	
	//funcion que devuelve un booleano para saber si una casilla es la cabeza de una serpiente
	
	public boolean isSerpiente(int casilla) {
		if(this.serpientes.containsKey(Integer.valueOf(casilla))) {
			return true;
		}else {
			return false;
		}
	}
	//funcion que devuelve un booleano para saber si una casilla es el principio de una escalera
	public boolean isEscalera(int casilla) {
		if(this.escaleras.containsKey(Integer.valueOf(casilla))) {
			return true;
		}else {
			return false;
		}
	}
	//funcion que devuelve la casilla con la cola de la serpiente
	public int devuelveCola(int cabeza) {
		return(this.serpientes.get(cabeza));
	}
	//funcion que devuelve la casilla con el final de la escalera
	public int devuelveFin(int principio) {
		return(this.escaleras.get(principio));
	}
	//funcion que añade un jugador a la lista de jugadores
	public void anyadirJugador(Jugador j) {
		this.jugadores.add(j);
	}
	//funcion que devuelve la lista de jugadores
	public List<Jugador> getListaJugadores() {
		return this.jugadores;
	}
	//funcion que devuelve el numero de jugadores que hay en la mesa
	public int getNumJugadores() {
		return this.jugadores.size();
	}
	//funcion que devuelve una string con el numero de jugadores, sus nombres y sus posiciones.
	
	public String toString() {
		int numJugadores = this.jugadores.size();
		String s = "En la mesa hay " + numJugadores + "\r\n";
		for(int i =0; i< numJugadores ;i++) {
			s+= "El jugador/a " + this.jugadores.get(i).getNombre() + " está en la posicion " + this.jugadores.get(i).getPosicion() + "\r\n"; 
		}
		return s;
	}
	//funcion que avanza al jugador j "n" casillas y devuelve la posición en la que se queda
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
	
   //funcion que devuelve cierto si el juego ha acabado y falso en caso contrario
	public boolean isJuegoTerminado() {
		return this.juegoTerminado;
	}
	
}
