import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.awt.*;

public class WaveformApp {

    private WaveformPanel[] waves = new WaveformPanel[0];

    static void setupView() throws UnsupportedAudioFileException, IOException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Waveform Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JScrollPane scrollPane = new JScrollPane();
            JPanel bottompPanel = new JPanel();
            JPanel waveListPanel = new JPanel();
            waveListPanel.setLayout(new BoxLayout(waveListPanel, BoxLayout.Y_AXIS));
            scrollPane.setViewportView(waveListPanel);
            scrollPane.setPreferredSize(new Dimension(800, 250));

            JButton loadButton = new JButton("Add Audio");
            loadButton.addActionListener(e -> {
                JFrame loadFrame = new JFrame("Add Audio");
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter wavFilter = new FileNameExtensionFilter("WAV Audio Files", "wav");
                fileChooser.setFileFilter(wavFilter);
                int result = fileChooser.showOpenDialog(loadFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    WaveformPanel panel = new WaveformPanel(selectedFile);
                    panel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    waveListPanel.add(panel);
                    waveListPanel.revalidate();
                    waveListPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "No file selected.", "Load Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            bottompPanel.add(loadButton);
            frame.add(bottompPanel, BorderLayout.SOUTH);
            frame.add(scrollPane);

            
            
            
            frame.pack();
            frame.setSize(820, 350); // ensure window is large enough
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Load audio + compute bars in a background thread ideally
            
        });
    }

    public static void main(String[] args)  {
        
            
        try {
            setupView();
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            

    }
}
