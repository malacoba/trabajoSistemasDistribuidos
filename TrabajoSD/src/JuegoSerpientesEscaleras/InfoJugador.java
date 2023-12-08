 package JuegoSerpientesEscaleras;

import java.io.*;

public class InfoJugador {
	private static int turno =0;
	private String nombre;
	private ObjectOutputStream outSocket;
	private ObjectInputStream inSocket;
	
	public InfoJugador(String nombre,ObjectOutputStream outSocket,ObjectInputStream inSocket ) {
		this.turno=turno;
		this.outSocket=outSocket;
		this.inSocket=inSocket;
		this.nombre=nombre;
		turno++;
	}
	public String getNombre() {
		return this.nombre;
	}
	public int getTurno() {
		return this.turno;
	}
	public ObjectOutputStream getOutput() {
		return this.outSocket;
	}
	public ObjectInputStream getInput() {
		return this.inSocket;
	}

}
