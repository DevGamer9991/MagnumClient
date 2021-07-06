package net.geeky.magnumclient.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.lwjgl.system.CallbackI;

import org.json.simple.parser.JSONParser;

import java.io.*;

public class Storage {

    String file = String.format(MinecraftClient.getInstance().runDirectory + "/mods/magnumclient/file.json");

    Gson gs = new GsonBuilder().setPrettyPrinting().create();

    public static final Logger LOGGER = LogManager.getLogger("geeky");

    public void Store(String hack, boolean active){

        Gson gson = gs;

        try {
            FileWriter fwriter = new FileWriter(file);
            JsonWriter jwriter = gson.newJsonWriter(fwriter);

            jwriter.beginObject();
            jwriter.name(hack);
            jwriter.value(active);
            jwriter.endObject();
            jwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean Read(String hack) {
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));

            Boolean value = (Boolean) jsonObject.get(hack);

            return value;
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
