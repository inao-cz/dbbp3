package me.inao.dbbp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Getter
public class Config {
    private String apikey;
    private String status;
    private String prefix;

    @SerializedName("commands")
    @Expose
    private JsonObject commands;

    public void loadConfig(Main instance){
        Gson gson = new Gson();
        String[] files = {"config.local.json", "config.json"};
        try {
            BufferedReader reader = null;
            for (String file : files) {
                File f = new File(file);
                if (f.exists()) {
                    reader = new BufferedReader(new FileReader(f.getName()));
                    break;
                }
            }
            if (reader == null) {
                System.exit(-2);
            }
            instance.getStorageUnit().setConfig(gson.fromJson(reader, Config.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isCommandEnabled(String name){
        try{
            return commands.getAsJsonObject(name).get("enabled").getAsBoolean();
        }catch (Exception e){
            System.out.println(name);
            e.printStackTrace();
        }
        return false;
    }

    public int getCommandPerms(String name){
        try{
            return commands.getAsJsonObject(name).get("perms").getAsInt();
        }catch (Exception ignored){}
        return -1;
    }
}
