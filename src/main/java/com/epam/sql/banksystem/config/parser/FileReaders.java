package com.epam.sql.banksystem.config.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaders {
    public static String openFile(String pro) {
        String jSonText = "";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(pro)))) {
            jSonText = readFile(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jSonText;
    }

    private static String readFile(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
