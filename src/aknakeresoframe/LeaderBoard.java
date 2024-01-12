package aknakeresoframe;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;



public class LeaderBoard extends PaintedPanel{
	private AknakeresoFrame af;
	private JButton vissza;
	public LeaderBoard(AknakeresoFrame f) {
		String[] adat = new String[9];
		File wd = new File(System.getProperty("user.dir")+"\\lb.txt");
		af = f;
		
		//olvasás
		try {
			BufferedReader br = new BufferedReader(new FileReader(wd));
			int i = 0;
			String buff;
			while((buff =br.readLine())!=null) adat[i++] = buff;
			br.close();
		}catch(Exception e){}
		this.setLayout(null);
		feluletGeneral(adat);
	}
	//a konstruktor hívja. 
	private void feluletGeneral(String[] adat) {
		JLabel cim = new JLabel("Leaderboard");
		cim.setForeground(Color.WHITE);
		cim.setFont(new Font("Verdana", Font.BOLD, 30));
		cim.setLocation(10, 10);
		cim.setSize(300, 50);
		this.add(cim);
		JLabel k = new JLabel("Beginner");
		k.setForeground(Color.WHITE);
		k.setFont(new Font("Verdana", Font.BOLD, 20));
		k.setLocation(20, 70);
		k.setSize(200, 30);
		this.add(k);
		JLabel h = new JLabel("Intermediate");
		h.setForeground(Color.WHITE);
		h.setFont(new Font("Verdana", Font.BOLD, 20));
		h.setLocation(200, 70);
		h.setSize(200, 30);
		this.add(h);
		JLabel m = new JLabel("Expert");
		m.setForeground(Color.WHITE);
		m.setFont(new Font("Verdana", Font.BOLD, 20));
		m.setLocation(380, 70);
		m.setSize(200, 30);
		this.add(m);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				JLabel z = new JLabel(adat[i*3+j]);
				z.setForeground(Color.WHITE);
				z.setFont(new Font("Verdana", Font.PLAIN, 14));
				z.setLocation(30+(i*180), 90+j*30);
				z.setSize(200, 50);
				this.add(z);
			}
		}
		vissza = new JButton("Back");
		vissza.setBackground(Color.WHITE);
		vissza.setSize(230,30);
		vissza.setLocation(175, 210);
		vissza.setBorder(null);
		vissza.setFocusable(false);
		vissza.addActionListener(new VisszaGombFigyelo());
		this.add(vissza);
	}

	//csak tesztelés miatt létezik
	public JButton backB() {
		return vissza;
	}
	//Ez segíti a visszajutást a menübe
	class VisszaGombFigyelo implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			af.openMenu2();
		}
	}
}
