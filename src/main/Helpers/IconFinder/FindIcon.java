package main.Helpers.IconFinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindIcon {
    /**
     * @param iconName name of icon to search
     * @return true, the icon was successfully founded and added to the "recources" folder
     */
    static public boolean loadIconFromNet(String iconName) {
        Process p = null;
        try {
            String project_path =  new File(".").getCanonicalPath();
            String file_path = "/src/main/Helpers/IconFinder";
            String command = "python " + project_path + file_path + "/IconAPI.py";
            System.out.println(command);
            p = Runtime.getRuntime().exec(command + " " + iconName);
            long startTime = System.currentTimeMillis();
            while(p.isAlive() && System.currentTimeMillis() - startTime < 10000) {
                Thread.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (p.isAlive()) return false;
        if (p.exitValue() != 0) {
            System.out.println(p.exitValue());
            try (final BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
                String line;
                if ((line = b.readLine()) != null)
                    System.out.println(line);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
    /*
    /**
     * @param iconName array of names of icon to search
     * @return true, if all the icons were successfully founded and added to the "recources" folder
     */

    //public static void main(String[] args) { System.out.println(findIcon("apple")); }
}
