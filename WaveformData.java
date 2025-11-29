public class WaveformData {

    public static float[] createWaveform(float[] samples, int barsCount) {
        int samplesPerBar = Math.max(1, samples.length / barsCount);
        float[] bars = new float[barsCount];

        for (int i = 0; i < barsCount; i++) {
            int start = i * samplesPerBar;
            int end = Math.min(samples.length, start + samplesPerBar);

            float max = 0f;
            for (int j = start; j < end; j++) {
                float v = Math.abs(samples[j]);
                if (v > max) max = v;
            }
            bars[i] = max; // store peak amplitude for that region
        }

        // normalize so tallest bar is 1.0
        float globalMax = 0f;
        for (float v : bars) {
            if (v > globalMax) globalMax = v;
        }
        if (globalMax > 0) {
            for (int i = 0; i < bars.length; i++) {
                bars[i] /= globalMax;
            }
        }

        return bars;
    }
}
