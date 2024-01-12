package aknakeresoframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class Mezo extends JButton implements Serializable{
	private int PozX;
	private int PozY;
	private boolean clickable = true;
	private boolean akna_e;
	private boolean felfedve = false;
	private int state = 0;
	Mezo[][] t;
	Palya p;
	JButton S;
	public Mezo(int Pozx, int Pozy, Mezo[][] tabla, Palya parent, JButton smiley) {
		PozX = Pozx;
		PozY = Pozy;
		t = tabla;
		p = parent;
		S = smiley;
		setSize(30,30);
		setBorder(null);
		setFocusable(false);
		setLocation(50+ PozX*30, 130+PozY*30);
		ImageIcon image = new ImageIcon("img\\t11.png");
		setIcon(image);
		this.addActionListener(new GombFigyelo());
		this.addMouseListener(new GombNyomasFigyelo());
		p.add(this);
	}
	public boolean isClickable() {
		return clickable;
	}
	public void setAknaE(boolean b) {
		akna_e = b;
	}
	public boolean isAknaE() {
		return akna_e;
	}
	
	public void setClickable(boolean b) {
		clickable = b;
		
	}
	public void setParent(Palya pa) {
		p = pa;
		setBorder(null);
		setFocusable(false);
		this.addActionListener(new GombFigyelo());
		this.addMouseListener(new GombNyomasFigyelo());
	}
	//1. Ha a gomb kattintható és nem zászló fedi, valamint nincs felfedve már, akkor összes szomszédját megkérdezve (isAknaE()) kiszámoljuk, 
	//hogy hány akna van a szomszédjában és eszerint kap a Mezo új textúrát. 
	//2. A vezérlést kezelő változók értékeiről is gondoskodik.
	//3. Amennyiben 0 akna van a szomszédságában, meghívja a felfedezo()-t. 
	
	public void felfed() {
		//1.
		if(state != 1){
		if(clickable) {
		clickable = false;
		if(!felfedve) {
			if(akna_e) {
				ImageIcon image = new ImageIcon("img\\t10.png");
				setIcon(image);
			}	
			else {
				int counter=0;
				if(PozX == 0) {
				if(PozY == 0) {
					if(t[PozX][PozY+1].isAknaE()) counter++;
					if(t[PozX+1][PozY].isAknaE()) counter++;
					if(t[PozX+1][PozY+1].isAknaE()) counter++;
				}
				else if(PozY == t[0].length-1){
					if(t[PozX][PozY-1].isAknaE()) counter++;
					if(t[PozX+1][PozY].isAknaE()) counter++;
					if(t[PozX+1][PozY-1].isAknaE()) counter++;
				}
				else {
					if(t[PozX][PozY-1].isAknaE()) counter++;
					if(t[PozX][PozY+1].isAknaE()) counter++;
					if(t[PozX+1][PozY-1].isAknaE()) counter++;
					if(t[PozX+1][PozY].isAknaE()) counter++;
					if(t[PozX+1][PozY+1].isAknaE()) counter++;
				}
				}
				else if(PozY == 0) {
				if(PozX == t.length-1) {
					if(t[PozX][PozY+1].isAknaE()) counter++;
					if(t[PozX-1][PozY].isAknaE()) counter++;
					if(t[PozX-1][PozY+1].isAknaE()) counter++;
				}
				else {
					if(t[PozX-1][PozY].isAknaE()) counter++;
					if(t[PozX-1][PozY+1].isAknaE()) counter++;
					if(t[PozX+1][PozY+1].isAknaE()) counter++;
					if(t[PozX+1][PozY].isAknaE()) counter++;
					if(t[PozX][PozY+1].isAknaE()) counter++;
				}
				}
				else if(PozY == t[0].length-1) {
				if(PozX == t.length-1) {
					if(t[PozX-1][PozY].isAknaE()) counter++;
					if(t[PozX-1][PozY-1].isAknaE()) counter++;
					if(t[PozX][PozY-1].isAknaE()) counter++;
				}
				else {
					if(t[PozX-1][PozY].isAknaE()) counter++;
					if(t[PozX-1][PozY-1].isAknaE()) counter++;
					if(t[PozX+1][PozY-1].isAknaE()) counter++;
					if(t[PozX+1][PozY].isAknaE()) counter++;
					if(t[PozX][PozY-1].isAknaE()) counter++;
				}
				}
				else if(PozX == t.length-1) {
				if(t[PozX-1][PozY+1].isAknaE()) counter++;
				if(t[PozX-1][PozY].isAknaE()) counter++;
				if(t[PozX-1][PozY-1].isAknaE()) counter++;
				if(t[PozX][PozY+1].isAknaE()) counter++;
				if(t[PozX][PozY-1].isAknaE()) counter++;
				}
				else {
				if(t[PozX-1][PozY+1].isAknaE()) counter++;
				if(t[PozX-1][PozY].isAknaE()) counter++;
				if(t[PozX-1][PozY-1].isAknaE()) counter++;
				if(t[PozX+1][PozY+1].isAknaE()) counter++;
				if(t[PozX+1][PozY].isAknaE()) counter++;
				if(t[PozX+1][PozY-1].isAknaE()) counter++;
				if(t[PozX][PozY+1].isAknaE()) counter++;
				if(t[PozX][PozY-1].isAknaE()) counter++;
				}
				//2.
				setIcon(new ImageIcon("img\\t0"+Integer.toString(counter)+".png"));
				p.feloldSzamlalo();
				felfedve = true;
				//3.
				if(counter == 0) felfedezo();
			}
		}
		}
	}
	}
	//meghívja minden szomszédos mező felfed függvényét.
	private void felfedezo() {
	if (PozY > 0)
            t[PozX][PozY-1].felfed();
    if (PozX > 0)
            t[PozX-1][PozY].felfed();
    if (PozY < t[0].length-1)
            t[PozX][PozY+1].felfed();
    if (PozX < t.length-1)
            t[PozX+1][PozY].felfed();
    if (PozX > 0 && PozY > 0)
            t[PozX-1][PozY-1].felfed();
    if (PozX < t.length-1 && PozY > 0)
        	 t[PozX+1][PozY-1].felfed();
    if (PozX > 0 && PozY < t[0].length-1)
        	t[PozX-1][PozY+1].felfed();
    if (PozX < t.length-1 && PozY < t[0].length-1)
    	t[PozX+1][PozY+1].felfed();
	}
	
	class GombFigyelo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	//ha kattintható és nem zászló
           if(clickable && state != 1) {
        	   //ha akna, akkor kattinthatatlaná teszünk mindent, felfedünk minden mezőt, ami akna és a smileyVeszit() függvényt hívjuk
        	   //ellenkező esetben csak felfed() hívása történik.
        	   if(akna_e) {
        	   		for(int i = 0; i < t.length; i++) {
        		   		for(int j = 0; j < t[i].length; j++) {
        			   		if(t[i][j].isAknaE()) {t[i][j].state = 0; t[i][j].felfed();}
        			   		t[i][j].setClickable(false);
        		   		}
        	   		}
        	   		
        	   		p.smileyVeszit();
           		}
        	   else {
        		   felfed();
        		   
        	   }
           }
        }
    }
	class GombNyomasFigyelo implements MouseListener{
        public void actionPerformed(ActionEvent e) {
           
        }

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//itt kezeljük a jobb klikkes jelölőket
			if(e.getButton() == MouseEvent.BUTTON3) {
				if(clickable) {
				if(state == 1) p.novelZ();
				else if(state == 0) p.csokkentZ();
				if(state < 2) state++;
				else state = 0;
				setIcon(new ImageIcon("img\\t1"+Integer.toString((state+1))+".png"));
				}
			}
			else{
	           if(clickable)
	        	   p.smileyKlikkelve();
	        }
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			p.smileyFelenged();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {	
		}
    }
}
