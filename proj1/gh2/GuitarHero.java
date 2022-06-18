package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final double CONCERT_BASE = 440.0 * Math.pow(2, -24.0 / 12.0);;
    public static final int Numbers = 37;
    public static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static void main(String[] args) {

        GuitarString[] string37 = new GuitarString[Numbers];
        for(int i = 0; i < Numbers; i++) {
            double frequency = CONCERT_BASE * Math.pow(2, i / 12.0);
            string37[i] = new GuitarString(frequency);
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) >= 0 && keyboard.indexOf(key) < Numbers) {
                    string37[keyboard.indexOf(key)].pluck();
                }
                System.out.println(key);
//                System.out.println(stringA.sample());

            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for(GuitarString guitarString : string37)
                sample += guitarString.sample();
            /* play the sample on standard audio */
            StdAudio.play(sample);


            /* advance the simulation of each guitar string by one step */
            for(GuitarString guitarString : string37)
                guitarString.tic();
        }
    }
}
