import javax.swing.*;
import java.awt.*;

public class WaveformPanel extends JPanel {

    private float[] bars = new float[0];

    public WaveformPanel() {
        setBackground(Color.WHITE);
        setForeground(new Color(0x1a73e8)); // pleasant blue for bars
        setPreferredSize(new Dimension(800, 200));
    }
    public void setBars(float[] bars) {
        this.bars = bars;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        int barCount = bars.length;
        float barWidth = (float) width / barCount;

        // Optional smoothing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(java.awt.Color.BLACK);

        for (int i = 0; i < barCount; i++) {
            float value = bars[i];         
            int barHeight = (int) (value * height);

            int x = (int) (i * barWidth);
            int y = (height - barHeight) / 2; // center vertically

            int w = Math.max(1, (int) (barWidth * 0.8));

            //g2.fillRect(x, y, w, barHeight);
            g2.fillRoundRect(x, y, w, barHeight, w, w);
        }
        g2.dispose();
    }
}
