package ModNav.ModNavStructure;

public class ModNavSubject {
    private Place building;
    private String subjectName;
    private final String subjectID;

    public ModNavSubject(Place building, String subjectName, String subjectID){
        this.building = building;
        this.subjectName = subjectName;
        this.subjectID = subjectID;
    }

    public Place getBuilding(){
        return this.building;
    }

    public String getSubjectName(){
        return this.subjectName;
    }

    public String getSubjectID() {
        return this.subjectID;
    }

    public void setSubjectName(String newName){
        this.subjectName = newName;
    }

    public void setNewBuilding(Place p){
        this.building = p;
    }
}
