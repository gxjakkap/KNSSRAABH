package ModNav.ModNavUtils;

import ModNav.ModNavStructure.ModNavSubject;
import ModNav.ModNavStructure.Place;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DBSubjectQueryResult {
    private Map<String, ModNavSubject> sm;

    public DBSubjectQueryResult(){
        this.sm = new HashMap<>();
    }

    public void addRow(String sub, String sn, Place p){
        ModNavSubject s = new ModNavSubject(p, sn, sub);
        this.sm.put(sub, s);
    }

    public Map<String, ModNavSubject> getSubjectMap(){
        return this.sm;
    }
}
