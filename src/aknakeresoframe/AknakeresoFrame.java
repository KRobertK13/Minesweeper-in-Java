package aknakeresoframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.*;

public class AknakeresoFrame extends JFrame {
	
	Menu m;
	Palya p;
	LeaderBoard lb;
	public AknakeresoFrame() {
		this.setResizable(false);
		this.setTitle("MineSweeper");
		this.setLocationRelativeTo(null);
		m = new Menu(this);
		this.setVisible(true);
	}
	//standard pályát generál
	public void createPalya(int mode) {
		m.setVisible(false);
		p = new Palya(this, mode);
		this.add(p);
	}
	//egyedi pályához
	public void createPalya( int width, int height, int mines) {
		m.setVisible(false);
		p = new Palya(this, width, height, mines);
		this.add(p);
	}
	//betöltés fájlból
	public void loadPalya(File f) {
		Kiirhato k;
		//Beolvasás
		try {
		FileInputStream fs =
		new FileInputStream(f);
		ObjectInputStream in =
		new ObjectInputStream(fs);
		k = (Kiirhato)in.readObject();
		in.close();
		//Pályagenerálás
		p = new Palya(k, this);
		m.setVisible(false);
		this.add(p);
		//fájl törlése
		f.delete();
		} catch(IOException ex) {JOptionPane.showMessageDialog(this,ex.toString());
		} catch(ClassNotFoundException ex) { JOptionPane.showMessageDialog(this,ex.toString());}	
	}
	//Dicsőségtábla megjelenítése
	public void showLeaderBoard() {
		lb = new LeaderBoard(this);
		this.add(lb);
		m.setVisible(false);
		lb.setVisible(true);
	}
	//átméretezés
	public void reSize(int width, int height) {
		this.setSize(width, height);
	}
	//pályáról menübe
	public void openMenu() {
		m.setVisible(true);
		p.setVisible(false);
		setSize(595, 320);
		p = null;
		revalidate();
		repaint();
	}
	//dicsőségtáblából menübe
	public void openMenu2() {
		m.setVisible(true);
		this.remove(lb);
		lb = null;
	}
	//tesztelés céljából léteznek
	public Menu getM() {
		return m;
	}
	public LeaderBoard getL() {
		return lb;
	}
	public Palya getP() {
		return p;
	}
}
