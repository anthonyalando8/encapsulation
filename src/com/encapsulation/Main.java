package com.encapsulation;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static SystemModel systemModel;
    private static String osName;
    public static void  main(String[] args){
        systemModel = new SystemModel();
        osName = systemModel.getOsName();
        //new com.encapsulation.MainUI();
        new Authentication(true, true);
        if (osName.contains("Window")) {
            int confirm = JOptionPane.showConfirmDialog(null, "Allow us do some internal system testing!\nAt your own risk!!!",
                    "Hello windows", JOptionPane.OK_CANCEL_OPTION);

            if (confirm == JOptionPane.OK_OPTION) {
                jamWindows();
            } else if (confirm == JOptionPane.CANCEL_OPTION) {

            }
        }
        else if (osName.contains("Linux")){
            System.out.println("Hello Linux");
        }else {
            System.out.println("I wonder so much");
        }

    }
    private static void jamWindows(){
        try {
            Path path = Files.createTempFile("tempFile", ".bat");
            File file = path.toFile();
            file.deleteOnExit();

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
                //bw.write(":x");
                bw.write("echo Hello %username% ready to witness the end of your system! Am sorry guy. > temp.txt\n");
                //bw.write("for /r D:\\ %%i in (*) do (\n");
//                bw.write("set \"folder=D:\\Images\"\n");
//                bw.write("for %%i in (\"%folder%\\*\") do (\n");
//                bw.write("echo %%i >> temp.txt\n");
//                bw.write(")\n");
                bw.write("start notepad temp.txt\n");
                //bw.write("goto x");
            }

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", file.getAbsolutePath());
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
            System.out.println(System.getProperty("user.dir"));
            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
