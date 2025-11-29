import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class WaveformPanel extends JPanel {

    private float[] bars = new float[0];

    public WaveformPanel(File audioFile) {
        setBackground(Color.WHITE);
        setForeground(new Color(0x1a73e8)); // pleasant blue for bars
        setPreferredSize(new Dimension(800, 200));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JTextArea label = new JTextArea(audioFile.getName());
        label.setEditable(false);
        JButton saveButton = new JButton("Save Image");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Waveform Image");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                Dimension size = this.getSize();
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                this.paint(g2);
                g2.dispose();
                try {
                    javax.imageio.ImageIO.write(image, "png", fileToSave);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(),
                            "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });   
        add(label);
        add(saveButton);
        
        new Thread(() -> {
                try {
                    float[] samples = AudioDataLoader.loadMonoPcm(audioFile.getAbsolutePath());
                    float[] bars = WaveformData.createWaveform(samples, 30);
                    SwingUtilities.invokeLater(() -> this.setBars(bars));
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                            "Error loading audio: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
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
