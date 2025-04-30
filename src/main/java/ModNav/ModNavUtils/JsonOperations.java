package ModNav.ModNavUtils;

import ModNav.ModNavStructure.Path;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonOperations {
    private static final Gson gson = new Gson();

    public static String modNavPlaceArrayStringify(List<Path> map){
        return gson.toJson(map);
    }

    public static List<Path> modNavMapParse(String jsonString){
        Type t = new TypeToken<List<Path>>(){}.getType();
        return gson.fromJson(jsonString, t);
    }

    public static String modNavNameListStringify(List<String> nl){
        return gson.toJson(nl);
    }

    public static List<String> modNavNameListParse(String jsonString){
        Type t = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(jsonString, t);
    }
}
