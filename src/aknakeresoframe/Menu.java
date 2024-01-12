package aknakeresoframe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Menu extends PaintedPanel {
	private JButton[] menugombok = new JButton[4];
	private JRadioButton[] modeSel = new JRadioButton[4];
	private JPanel mode;
	private JTextField[] adatok = new JTextField[3];
	private AknakeresoFrame af;
	public Menu(AknakeresoFrame frame) {
		af = frame;
		menuGeneralas();
		af.setSize(595, 320);
		af.add(this);
	}
	//legenerálja a menü teljes felületét
	private void menuGeneralas() {
		this.setLayout(null);
		JLabel cim = new JLabel("MineSweeper");
		cim.setForeground(Color.WHITE);
		cim.setFont(new Font("Verdana", Font.BOLD, 30));
		cim.setLocation(50, 10);
		cim.setSize(330, 50);
		this.add(cim);
		String[] szovegek = {"New Game", "Load Game", "Leaderboard", "Exit"};
		for(int i = 0; i < 4; i++) {
			menugombok[i] = new JButton(szovegek[i]);
			menugombok[i].setBackground(Color.WHITE);
			menugombok[i].setSize(230,30);
			menugombok[i].setLocation(50, 70+ i*50);
			menugombok[i].setBorder(null);
			menugombok[i].setFocusable(false);
			menugombok[i].addActionListener(new MenuFigyelo());
			this.add(menugombok[i]);
		}
		mode();
		this.add(mode);
	}
	
	//a menü legenerálásának a része, az átláthatóságot és tagoltságot segítni.
	private void mode() {
		mode = new JPanel();
		mode.setLayout(null);
		mode.setLocation(330,20);
		mode.setSize(200, 230);
		mode.setBackground(Color.WHITE);
		JLabel cim = new JLabel("Mode");
		cim.setFont(new Font("Verdana", Font.BOLD, 18));
		cim.setSize(180, 20);
		cim.setLocation(10, 10);
		
		mode.add(cim);
		
		ButtonGroup b = new ButtonGroup();
		modeSel[0] = new JRadioButton("Beginner");
		modeSel[1] = new JRadioButton("Intermediate");
		modeSel[2] = new JRadioButton("Expert");
		modeSel[3] = new JRadioButton("Custom");
		for(int i = 0; i < 4; i++) {
			modeSel[i].setSize(180, 20);
			modeSel[i].setLocation(10,35+i*25);
			modeSel[i].setBackground(Color.WHITE);
			modeSel[i].setFocusable(false);
			b.add(modeSel[i]);
			modeSel[i].addActionListener(new ModeSelectorFigyelo());
			mode.add(modeSel[i]);
		}
		modeSel[0].setSelected(true);
		String[] szovegek = {"Width", "Height", "Mines"};
		for(int i = 0; i < 3; i++) {
			JLabel c = new JLabel(szovegek[i]);
			c.setFont(new Font("Verdana", Font.PLAIN, 12));
			c.setSize(60,20);
			c.setLocation(35, 135+i*30);
			mode.add(c);
			adatok[i] = new JTextField(3);
			adatok[i].setSize(40,20);
			adatok[i].setLocation(90, 135+i*30);
			adatok[i].setEnabled(false);
			adatok[i].addKeyListener(new AdatokFigyelo());
			mode.add(adatok[i]);
		}
	}
	//csak teszteles miatt léteznek
	public JButton lbb() {
		return menugombok[2];
	}
	public JButton ngb() {
		return menugombok[0];
	}
	
	//A beviteli mezőkbe csak szám kerülhet!
	class AdatokFigyelo implements KeyListener{
		public void keyPressed(KeyEvent ke) {
            JTextField tf = (JTextField)ke.getSource();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
               tf.setEditable(true);
              
            } else {
               tf.setEditable(false);
               tf.setBackground(Color.WHITE);
            }
         }

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
    }	
	
	//Ha a kiválasztott mód nem az egyedi, akkor nem módosítható és üresnek kell mardadni
	class ModeSelectorFigyelo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	if(e.getSource().equals(modeSel[3])) {
        		adatok[0].setEnabled(true);
        		adatok[1].setEnabled(true);
        		adatok[2].setEnabled(true);
        	}
        	else {
        		//Kitöröljük a benne lévő adatot és átírhatatlanná tesszük.
        		adatok[0].setText("");
        		adatok[1].setText("");
        		adatok[2].setText("");
        		adatok[0].setEnabled(false);
        		adatok[1].setEnabled(false);
        		adatok[2].setEnabled(false);
        	}
        }
    }
	
	class MenuFigyelo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
           JButton b = (JButton)e.getSource();
           switch(b.getText()) {
           		case "New Game":{
           			//amennyiben valamelyik standard pálya van kiválasztva
           			for(int i = 0; i <3; i++) {
           				if(modeSel[i].isSelected()) { af.createPalya(i); return; }
           			}
           			//Amennyiben az egyedi pálya lehetőség van kiválasztva, kiolvassuk az adatokat
           			int w = Integer.parseInt(adatok[0].getText());
           			int h = Integer.parseInt(adatok[1].getText());
           			int m = Integer.parseInt(adatok[2].getText());
           			//Amennyiben nincs a szabályoknak megfelelő határok közt a megadott érték, határok közé szorítjuk azt
           			if(w <9) w = 9;
           			else if (w > 30) w = 30;
           			if(h <9) h = 9;
           			else if (h > 24) h = 24;
           			if(m < 10) m = 10;
           			else if (m > w*h-1) m = w*h-1;
           			af.createPalya(w, h, m);
           			break;
           		}
           		case "Load Game": {
           			try {
           			JFileChooser f = new JFileChooser();
           			f.showOpenDialog(b);
           			File file = f.getSelectedFile();
           			af.loadPalya(file);
           			}
           			catch(Exception ex) {}
           			break;
           		}
           		case "Leaderboard":{
           			af.showLeaderBoard();
           			break;
           		}
           		case "Exit": {
           			af.dispose();
           			break;
           		}
           		default: break;
           }  
        }
    }
}
