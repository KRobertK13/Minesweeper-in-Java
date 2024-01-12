package aknakeresoframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class Palya extends PaintedPanel {
	private Timer time;
	private JLabel ora;
	private int i = 0;
	
	private JButton smiley;
	private JLabel zaszlo;
	private int szamlalo = 0;
	private boolean visszaallitando = false;
	int m;
	private int width;
	private int heigth;
	private int aknaszam;
	private Mezo[][] tabla;
	
	JMenu mainmenu, pause;
	
	private AknakeresoFrame af;
	
	//Szabványos pálya generálás
	public Palya(AknakeresoFrame frame, int mode) {
		af = frame;
		m = mode;
		this.setLayout(null);
		if(mode == 0) { width = 9; heigth = 9; aknaszam = 10; af.reSize(385, 465); mezoGen(9,9,10);}
		else if(mode == 1) { width = 16; heigth = 16; aknaszam = 40; af.reSize(595, 675); mezoGen(16,16,40);}
		else if(mode == 2) { width = 30; heigth = 16; aknaszam = 99; af.reSize(1015, 676); mezoGen(16,30,99);}
		menuGen();
		dashboardGen();
		time = new Timer();
		time.scheduleAtFixedRate(new TimerTask(){public void run(){ i++; if(i<10)ora.setText(new String("00"+Integer.toString(i))); else if (i < 100) ora.setText("0"+Integer.toString(i)); else ora.setText(Integer.toString(i)); if(i >=999) cancel();}}, 1000, 1000);
	}
	//egyedi pálya generálás
	public Palya(AknakeresoFrame frame, int w, int h, int mi) {
		af = frame;
		m = 3;
		width = w;
		heigth = h;
		aknaszam = mi;
		this.setLayout(null);
		af.reSize(w*30+115, 195+h*30); 
		dashboardGen();
		mezoGen(h,w, mi);
		menuGen();
		time = new Timer();
		time.scheduleAtFixedRate(new TimerTask(){public void run(){ i++; if(i<10)ora.setText(new String("00"+Integer.toString(i))); else if (i < 100) ora.setText("0"+Integer.toString(i)); else ora.setText(Integer.toString(i)); if(i >= 999) cancel();}}, 1000, 1000);
	}
	//Betöltött pálya adatai alapján generálható.
	public Palya(Kiirhato k, AknakeresoFrame frame) {
		af = frame;
		m = k.getMode();
		tabla = k.getTabla().clone();
		width = tabla.length;
		heigth = tabla[0].length;
		aknaszam = k.getAknaszam();
		this.setLayout(null);
		af.reSize(width*30+115, 195+heigth*30);
		menuGen();
		dashboardGen();
		af = frame;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < heigth; j++) { 
				tabla[i][j].setParent(this);
				tabla[i][j].setVisible(true);
				this.add(tabla[i][j]);
				if(!tabla[i][j].isClickable()) szamlalo++;
			}
		}
		zaszlo.setText(k.getZaszlo());
		i = Integer.parseInt(k.getIdo());
		ora.setText(k.getIdo());
		time = new Timer();
		time.scheduleAtFixedRate(new TimerTask(){public void run(){ i++; if(i<10)ora.setText(new String("00"+Integer.toString(i))); else if (i < 100) ora.setText("0"+Integer.toString(i)); else ora.setText(Integer.toString(i)); if(i >=999) cancel();}}, 1000, 1000);
	}
	
	//növeli a zászlószámlálót
	public void novelZ() {
		zaszlo.setText(Integer.toString((Integer.parseInt(zaszlo.getText())+1)));
	}
	//csökkenti a zászlószámlálót
	public void csokkentZ() {
		zaszlo.setText(Integer.toString((Integer.parseInt(zaszlo.getText())-1)));
	}
	//a szamlalo nevű változót növeli, ha eg mező meghívja. Ha a játék nyertesnek számít, (mert a feloldott mezők száma
	//annyi, mint az összes mező - aknás mező) megállítjuk a játékot.
	public void feloldSzamlalo() {
		szamlalo++;
		if(szamlalo == tabla.length*tabla[0].length-aknaszam) {
			//ha a játék nyertes, akkor a mezőket kezelhetetlenné tesszük, a játéknak vége
		for(int i = 0; i < tabla.length; i++) 
	   		for(int j = 0; j < tabla[i].length; j++) 
		   		tabla[i][j].setClickable(false);
		//a smiley gomb megkapja a nyertes textúrát
		ImageIcon image = new ImageIcon("img\\t17.png");
		smiley.setIcon(image);
		//kezeljük a számlálót
		visszaallitando = false;
		time.cancel();
			//ha szabványos a pálya, megadhatjuk a nevet.
			if(m < 3) {
				String name = JOptionPane.showInputDialog(this,"Enter Name");
				if(name != null) {
					//ez a függvény kezeli a dicsőségtáblát
					LB_kezelo(name);
				}
			}
	   	}
	}
	//A leaderboardot kezeli
	private void LB_kezelo(String nev) {
		//Ha hosszabb, mint 15 karakter, akkor csonkol.
		if(nev.length() > 15) {
			nev = nev.substring(0, 15);
		}
		//Beolvassuk az eredeti táblát.
		String[] adat = new String[9];
		File wd = new File(System.getProperty("user.dir")+"\\lb.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(wd));
			int i = 0;
			String buff;
			while((buff =br.readLine())!=null) adat[i++] = buff;
			br.close();
		}catch(Exception e){}
		//aktuálisan sikerült időértéket kiolvassuk a felületről
		int szam = Integer.parseInt(ora.getText());
		for(int i = 0; i <3; i++) {
			//megnézzük, hogy a tábla adott pozíciójába betehetjük-e az új értéket
			String[] s = adat[m*3+i].split(" ");
			if(szam < Integer.parseInt(s[s.length-1]) || (s[0] == "Anonymous" && s.length == 2)) {
				//Rendezzük a táblát
				for(int j = 2; j > i; j--) {
					adat[j] = adat[j-1];
				}
				//beszúrjuk a megfelelő helyre az új értéket.
				adat[m*3+i] = nev+" "+ ora.getText();
				break;
			}
		}
		//visszaírjuk a változást
		try {
			FileWriter fw = new FileWriter(wd);
			fw.write(adat[0]+ "\n"+adat[1]+ "\n"+adat[2]+ "\n"+adat[3]+ "\n"+adat[4]+ "\n"+adat[5]+ "\n"+adat[6]+ "\n"+adat[7]+ "\n"+adat[8]);
			fw.close();
		}catch(Exception e) {}
	}
	//Vesztes játék esetén a smiley megkapja a vesztes textúrát és megállítjuk a számlálót
	public void smileyVeszit() {
		ImageIcon image = new ImageIcon("img\\t16.png");
		smiley.setIcon(image);
		visszaallitando = false;
		time.cancel();
	}
	//megkapja a smiley a lenyomás textúrát
	public void smileyKlikkelve() {
		ImageIcon image = new ImageIcon("img\\t15.png");
		smiley.setIcon(image);
		visszaallitando = true;
	}
	//ha felengedjük a smiley-t visszaállítja, ha a játék közben nem lett elvesztítve/megnyerve
	public void smileyFelenged() {
		if(visszaallitando) {
		ImageIcon image = new ImageIcon("img\\t14.png");
		smiley.setIcon(image);
		}
	}
	//generál egy menüsort
	private void menuGen() {
		JMenuBar mb = new JMenuBar();
		mb.setLocation(0, 0);
		mb.setSize(width*30+115, 30);
		mainmenu = new JMenu("Menu");
		pause = new JMenu("Pause");
		mb.add(mainmenu);
		mainmenu.addMenuListener(new MenuGombFigyelo());
		mb.add(pause);
		pause.addMenuListener(new  PauseGombFigyelo());
		JMenuItem c = new JMenuItem("Continue");
		c.addActionListener(new MenuItemFigyelo());
		JMenuItem s = new JMenuItem("Save Game");
		s.addActionListener(new MenuItemFigyelo());
		JMenuItem h = new JMenuItem("Help");
		h.addActionListener(new MenuItemFigyelo());
		pause.add(c);
		pause.add(s);
		pause.add(h);
		this.add(mb);
	}
	//a műszerfalat generálja
	private void dashboardGen() {
		ora = new JLabel("000");
		ora.setForeground(Color.WHITE);
		ora.setFont(new Font("Verdana", Font.BOLD, 18));
		ora.setSize(100, 50);
		ora.setBackground(new Color(0,0,0,0));
		ora.setLocation(width*30+15, 55);
		this.add(ora);
		
		zaszlo = new JLabel(Integer.toString(aknaszam), SwingConstants.LEFT);
		zaszlo.setSize(50,50);
		zaszlo.setForeground(Color.WHITE);
		zaszlo.setFont(new Font("Verdana", Font.BOLD, 18));
		zaszlo.setLocation(50, 55);

		smiley = new JButton();
		smiley.setSize(50,50);
		smiley.setBorder(null);
		smiley.setFocusable(false);
		smiley.setLocation((width*30+115)/2-25, 55);
		ImageIcon image = new ImageIcon("img\\t14.png");
		smiley.setIcon(image);
		
		this.add(smiley);
		smiley.addActionListener(new SmileyFigyelo());
		this.add(zaszlo);
	}
	//a pálya mezőit generálja
	private void mezoGen(int h, int w, int a) {
		//elkészítjük a teljes mátrixot
		tabla = new Mezo[w][h];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				tabla[i][j] = new Mezo(i, j, tabla, this, smiley);
			}
		}
		//sorsolunk random pozíciókba aknákat
		int x, y;
		int aknaszam = 0;
		Random r = new Random();
		do {
			x = r.nextInt(0, w);
			y = r.nextInt(0,h);
			if(!tabla[x][y].isAknaE()) {
				tabla[x][y].setAknaE(true);
				aknaszam++;
			}
		} while(aknaszam != a);
	}

	//tesztelés végett léteznek
	public JMenu MM() {
		return mainmenu;
	}
	public JMenu PM() {
		return pause;
	}

	public Mezo getMezo(int s, int o) {
		return tabla[s][o];
	}
	public JButton getS() {
		return smiley;
	}
	public int getIdo() {
		return Integer.parseInt(ora.getText());
	}
	class SmileyFigyelo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	//töröljük a mezőket
        	for(int i = 0; i < width; i++) 
    			for(int j = 0; j < heigth; j++) 
    				remove(tabla[i][j]);
        	tabla = null;
        	//reseteljük a számláló értékét és a zászlókat
        	ora.setText("000");
        	zaszlo.setText(Integer.toString(aknaszam));
    		//generálunk új pályát
        	mezoGen(heigth, width, aknaszam);
    		//visszaállítjuk a pálya alpváltozóit
        	i = 0;
    		szamlalo = 0;
    		visszaallitando = true;
    		smileyFelenged();
    		visszaallitando = false;
    		//elindítjuk a számlálót
    		try{time.cancel();} catch(Exception ex) {}
    		time = new Timer();
			time.scheduleAtFixedRate(new TimerTask(){public void run(){ i++; if(i<10)ora.setText(new String("00"+Integer.toString(i))); else if (i < 100) ora.setText("0"+Integer.toString(i)); else ora.setText(Integer.toString(i)); if(i >=999) cancel();}}, 1000, 1000);
    		//frissítjük a felületet
			revalidate();
    		repaint();
        }
    }

	class MenuGombFigyelo implements MenuListener{
		@Override
        public void menuSelected(MenuEvent e) {
            af.openMenu();

        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
	class PauseGombFigyelo implements MenuListener{
		@Override
        public void menuSelected(MenuEvent e) {
			//elrejti a mezőket és megállítja a timert
			for(int i = 0; i < width; i++) 
    			for(int j = 0; j < heigth; j++) 
    			tabla[i][j].setVisible(false);
			time.cancel();
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
	class MenuItemFigyelo implements ActionListener{
		@Override
        public void actionPerformed(ActionEvent e) {
			JMenuItem  k = (JMenuItem)e.getSource();
			if(k.getText() == "Continue") {
				//láthatóvá tesszük a mezőket és megjelenítjük a timert
				for(int i = 0; i < width; i++) 
	    			for(int j = 0; j < heigth; j++) 
	    			tabla[i][j].setVisible(true);
				time.cancel();
				time = new Timer();
				time.scheduleAtFixedRate(new TimerTask(){public void run(){ i++; if(i<10)ora.setText(new String("00"+Integer.toString(i))); else if (i < 100) ora.setText("0"+Integer.toString(i)); else ora.setText(Integer.toString(i)); if(i >=999) cancel();}}, 1000, 1000);
			}
			else if(k.getText() == "Save Game") {
				try {
					//A mezők Palya változóját null-ra állítjuk. Fontos!
					for(int i = 0; i < width; i++) 
		    			for(int j = 0; j < heigth; j++) 
		    			tabla[i][j].setParent(null);
					//Az objektum létrehozása és kiírás
					Kiirhato kiir = new Kiirhato(tabla, ora.getText(), zaszlo.getText(), aknaszam, m);
					JFileChooser f = new JFileChooser();
           			f.showOpenDialog(k);
           			File file = f.getSelectedFile();
					FileOutputStream fs = new FileOutputStream(file);
					ObjectOutputStream out = new ObjectOutputStream(fs);
					out.writeObject(kiir);
					out.close();
					af.openMenu();
					}
					catch(IOException ex) {JOptionPane.showMessageDialog(af,ex.toString()); }
			}
			else if(k.getText() == "Help") {
				JOptionPane.showMessageDialog(af, "-Locate all the mines as quickly as possible without uncovering any of them.\r\n"
						+ "-If you uncover a mine, you lose the game.\r\n"
						+ "\r\n"
						+ "About Minesweeper\r\n"
						+ "Version 1.0\r\n"
						+ "Made by Kovacs Robert Kristof", "Minesweeper overview \r\n", JOptionPane.INFORMATION_MESSAGE);
			}
		}
    }
}
