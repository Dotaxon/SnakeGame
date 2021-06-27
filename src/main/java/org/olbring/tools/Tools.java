package org.olbring.tools;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class Tools {

    //bringt sekunden angabe in folgende struktur z.b. 74s -> 1min 14s
    public static String formatSecondsNicely(long secconds){
        double seccondTime = secconds%60; //Berechnet die überschüßigen Sekunden
        int minuteTime = (int) (secconds/60);

        return (minuteTime + "min " + seccondTime + "s");
    }

    //holt sich die alten Settings aus einer Datei nicht in der .jar
    public static int[] loadOldSettings(String path){
        int settingsIndex[] = {0,0,0,0} ; /*
                                              Bei allen außer Obstacle count wird der Index der ComboBox gespeichert
                                              Bei Obstacle count die Anzahl der Obstacles

                                             Index 0 -> WallCrashEnabeld
                                             Index 1 -> Difficulty
                                             Index 2 -> Gamemode
                                             Index 3 -> Obstacle count
                                            */


        try {
            BufferedReader reader = new BufferedReader(new BufferedReader(new FileReader(path)));
            String inputString = reader.readLine();

            String inputArray[];
            inputArray = inputString.split(";");

            for (int i = 0; i < settingsIndex.length && i < inputArray.length; i++) {
                settingsIndex[i] = Integer.parseInt(inputArray[i]);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return settingsIndex;
    }

    //speichert die alten Settings in einer Datei (nicht in der .jar) evtl wird eine neue datei erstellt
    //die strucktur vom Integer Array ist die Gleiche wie in Tools.loadOldSettings
    public static void saveOldSettings(String path, int[] indexes) throws IOException {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(path));




        }
        catch (IOException e) //Triggerd wenn keine Datei vorhanden ist
        {
            File file = new File(path);

            //erstelt die Datei
            try {
                if (file.createNewFile()) ;
            } catch (IOException ioException)
            {
                ioException.printStackTrace();
                System.out.println("File could not be created");
            }


            writer = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < indexes.length; i++) {
                writer.write(indexes[i]);
                writer.write(";");
                writer.flush();
            }

            return;
        }

        for (int i = 0; i < indexes.length; i++) {
            writer.write(String.valueOf(indexes[i]));
            writer.write(";");
            writer.flush();
        }

    }

    //lädt ein Icon (Resource) aus der .jar
    public static ImageIcon loadIcon(String path){
        ImageIcon icon = null;


        ClassLoader classLoader = Tools.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {
            icon = new ImageIcon(ImageIO.read(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }
}


