package thefirstchange.example.com.communicationtext.course.object;

/*
        值班表  某节课的值班学生的信息
 */
public class ListViewSchDutyUserInfo {

    private String ph;
    private String name;
    private String pos;             //职位
    private String corppart;       //部门

    public String getCorppart() {
        return corppart;
    }

    public void setCorppart(String corppart) {
        this.corppart = corppart;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
