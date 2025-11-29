import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.awt.*;

public class WaveformApp {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Waveform Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            WaveformPanel panel = new WaveformPanel();
            frame.add(panel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // load audio and compute bars in a background thread ideally
            new Thread(() -> {
                try {
                    float[] samples = AudioDataLoader.loadMonoPcm("fart-01.wav");
                    float[] bars = WaveformData.createWaveform(samples, 30);
                    SwingUtilities.invokeLater(() -> panel.setBars(bars));
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame,
                            "Error loading audio: " + e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        });
    }
}
