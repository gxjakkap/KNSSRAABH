package ModNav.ModNavUtils;

import ModNav.ModNavStructure.Path;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ModNav.ModNavStructure.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonOperations {
    private static final Gson gson = new Gson();

    public static String modNavPlaceArrayStringify(List<Path> map){
        return gson.toJson(map);
    }

    public static List<Path> modNavMapParse(String jsonString, Map<String, Place> mp){
        Type t = new TypeToken<List<Path>>(){}.getType();
        List<Path> parsed = gson.fromJson(jsonString, t);
        List<Path> pth = new ArrayList<>();
        parsed.forEach(p -> {
            Place dest = mp.get(p.getDest().getId());
            pth.add(new Path(dest, p.getWeight()));
        });
        return pth;
    }

    public static String modNavNameListStringify(List<String> nl){
        return gson.toJson(nl);
    }

    public static List<String> modNavNameListParse(String jsonString){
        Type t = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(jsonString, t);
    }
}
