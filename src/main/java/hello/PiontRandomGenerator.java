package hello;

import java.util.Random;

public class PiontRandomGenerator {

    Random random = new Random();

    public Measurement generate(int count)
    {
        Measurement measurement = new Measurement();
        String[] newXs = new String[count];
        String[] newYs = new String[count];
        String[] newBpm = new String[count];
        String[] newQrs = new String[count];

        for (int i = 0; i<count; i++){
                newXs[i] = random.nextInt(1024)+"";
                newYs[i] = random.nextInt(1024)+"";
                newBpm[i] = random.nextInt(1024)+"";
                newQrs[i] = random.nextInt(1024)+"";
        }
        measurement.x = newXs;
        measurement.t = newYs;
        measurement.name = "ACE_12231";
        measurement.date = "0000";
        measurement.bpm = newQrs;
        measurement.qrs = newBpm;
        return measurement;
    }

}
