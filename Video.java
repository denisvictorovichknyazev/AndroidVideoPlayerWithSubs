


public class Video {
    private String name;
    private String image;
    private int id;
    private String description;
    private  boolean free;
    private String[] qualities;
    private String teacher_name;
    private int teacherID;
    private String duration;

    public Video(int id, String name, String image, String description,boolean free, String[] qualities,String teacher_name,int teacherID, String duration) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.free = free;
        this.qualities = qualities;
        this.teacher_name = teacher_name;
        this.teacherID = teacherID;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isFree() {return free;}

    public void setFree(boolean free) {this.free = free;}

    public String[] getQualities() {
        return qualities;
    }

    public void setQualities(String[] qualities) {
        this.qualities = qualities;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
