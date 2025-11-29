import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioDataLoader {

    public static float[] loadMonoPcm(String filePath) throws UnsupportedAudioFileException, IOException {
        File audioFile = new File(filePath);
        AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat baseFormat = ais.getFormat();

        // Convert to PCM_SIGNED if needed
        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,                        // bits per sample
                1,                         // channels (mono)
                2,                         // bytes per frame (16-bit mono)
                baseFormat.getSampleRate(),
                false                      // little endian
        );
        AudioInputStream din = AudioSystem.getAudioInputStream(targetFormat, ais);

        byte[] buffer = din.readAllBytes();

        int samplesCount = buffer.length / 2;
        float[] samples = new float[samplesCount];

        // Convert little-endian 16-bit to float [-1, 1]
        for (int i = 0; i < samplesCount; i++) {
            int low = buffer[2 * i] & 0xff;
            int high = buffer[2 * i + 1]; // signed
            int sample = (high << 8) | low;
            samples[i] = sample / 32768f;
        }

        return samples;
    }
}
