package aknakeresoframe;

import java.io.Serializable;

public class Kiirhato implements Serializable {
	private Mezo[][] t;
	private String i;
	private String z;
	private int a;
	private int m;
	
	public Kiirhato(Mezo[][] tabla, String ido, String zaszlo, int aknaszam, int mode) {
		t=tabla;
		i = ido;
		z = zaszlo;
		a = aknaszam;
	}
	public Mezo[][] getTabla(){
		return t;
	}
	
	public String getIdo(){
		return i;
	}
	public String getZaszlo(){
		return z;
	}
	public int getAknaszam(){
		return a;
	}
	public int getMode(){
		return m;
	}
}
