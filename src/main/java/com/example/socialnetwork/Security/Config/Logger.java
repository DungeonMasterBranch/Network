package com.example.socialnetwork.Security.Config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Logger {

    protected static List<Object> data = new ArrayList<Object>();
    private static final Date date = new Date();

    public static void writeToLogs(Map<String, Object> map) throws IOException {
        try {

            readFromLogs();

            if (Files.size(Paths.get("logger.json")) > 10000) {
                data = new ArrayList<Object>();
            }

            if ((new Date().getTime() - date.getTime()) / 1000 > 86400) {
                data = new ArrayList<Object>();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Writer writer = Files.newBufferedWriter(Paths.get("logger.json"));

            data.add(map);

            gson.toJson(data, writer);

            writer.close();

            data.clear();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void readFromLogs() {
        try {

            Reader reader = Files.newBufferedReader(Paths.get("logger.json"));

            data = new Gson().fromJson(reader, new TypeToken<List<Object>>() {}.getType());

            if (data == null) {
                data = new ArrayList<Object>();
            }

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
