package hello;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import ch.qos.logback.core.net.SyslogOutputStream;
import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ESPConnection {


    int dlugoscPomiaru=20;
    int stopper=0;
    int output=0;
    int alpha=1;
    int alphaScale=10;
    int lastOutput=0;
    int znacznik=0;
    public Measurement getData() {

            try {
                ServerSocket serverSocket = new ServerSocket(1224);
                Socket socket = serverSocket.accept();

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                Measurement measurement = new Measurement();

                String[] xArgumentsComplete = new String[0];
                String[] tArgumentsComplete = new String[0];
                List<String> qrsArgumentsComplete = new ArrayList<>();
                List<String> bpmArgumentsComplete = new ArrayList<>();


                int counter = 0;
                while (true) {
                    try {
                        String readLine = reader.readLine();
                        System.out.println(readLine);
                        // parsing file "JSONExample.json"
                        if (readLine.contains("\"dn\"")) {
                            Object obj = new JSONParser().parse(readLine);
                            JSONObject jo = (JSONObject) obj;
                            String[] xArguments = jo.get("x").toString().split(" ");
                            String[] yArguments = jo.get("t").toString().split(" ");
                            String[] BpmArguments = jo.get("BPM").toString().split(" ");
                            String[] QrsArguments = jo.get("QRS").toString().split(" ");

                            xArgumentsComplete = ArrayUtils.addAll(xArgumentsComplete, xArguments);
                            tArgumentsComplete = ArrayUtils.addAll(tArgumentsComplete, yArguments);
                            //bpmArgumentsComplete = ArrayUtils.addAll(bpmArgumentsComplete, BpmArguments);
                            //qrsArgumentsComplete = ArrayUtils.addAll(qrsArgumentsComplete, QrsArguments);

                            //======== Zmienne do wyykrywania
                            List<Integer> probki=new ArrayList<>();
                            List<Integer> czas=new ArrayList<>();
                            int srednia=0;

                            //===========================
                          Random r = new Random();

                            for (int i = 0; i < xArgumentsComplete.length; i++) {

                                //TODO filtrowany sygnał podać na główny wykres
                                output = (alpha * (Integer.valueOf(xArgumentsComplete[i])) + (alphaScale - alpha) * lastOutput) / alphaScale;

                               czas.add(output);
                                lastOutput = output;
                                probki.add(Integer.valueOf(xArgumentsComplete[i]));
                                //Pan-tompkins
//                                if (i > 3 && i<(xArgumentsComplete.length)-3) {
//                                    probki.add(-1 * (Integer.valueOf(xArgumentsComplete[i - 1])) + 1 * (Integer.valueOf(xArgumentsComplete[i + 1])));
//                                    //probki.add(Integer.valueOf(xArgumentsComplete[i])*Integer.valueOf(xArgumentsComplete[i]));
//
//                                }
//                                probki.add((probki.get(i) * probki.get(i)));
//                                // Warunek szukania maksimum dalej niż 400ms
//                                 if (stopper > 200) {
//                                        if (probki.get(i) > 3900000 || probki.get(i) < -3900000 ) {
//                                        znacznik++;
//                                        stopper=0;
//                                        }else{
//                                            stopper++;
//                                        }
//                                    }
//                                czas.add(znacznik);
                            }
                            for (int i = 0; i < xArgumentsComplete.length; i++){

                                qrsArgumentsComplete.add(String.valueOf(probki.get(i)));
                                bpmArgumentsComplete.add(String.valueOf(czas.get(i)));

                            }
                            String clientName = (String) jo.get("dn");
                            String date = (String) jo.get("date");
                            String t = (String) jo.get("t");
                            String x = (String) jo.get("x");
                            String BPM = (String) jo.get("BPM");
                            String QRS = (String) jo.get("QRS");
                            System.out.println("NazwaKlienta: " + clientName);
                            System.out.println("Data: " + date);
                            System.out.println("x: " + x );
                            System.out.println("t: " + t);
                            System.out.println("BPM: " + BPM);
                            System.out.println("QRS: " + QRS);
                            System.out.println(dlugoscPomiaru);
                            if (clientName.contains("ACC")) {
                                counter++;
                                if (counter > dlugoscPomiaru) {

                                    measurement.setName(clientName);
                                    measurement.setDate(date);
                                    measurement.setX(xArgumentsComplete);
                                    measurement.setT(tArgumentsComplete);
                                    measurement.setQrs(qrsArgumentsComplete.toArray());
                                    measurement.setBpm(bpmArgumentsComplete.toArray());

                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                        return null;
                    }
                }
                socket.close();
                serverSocket.close();
                return measurement;
                // }


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //return null;
}



}
