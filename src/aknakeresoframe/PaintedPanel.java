package aknakeresoframe;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PaintedPanel extends JPanel {
	@Override
	 protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        int w = getWidth(), h = getHeight();
	        GradientPaint gp = new GradientPaint(0, 0, new Color(155, 7, 153), 330, 420, new Color(5,202, 211));
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, w, h); 
	    }
}
