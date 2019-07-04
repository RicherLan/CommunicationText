package thefirstchange.example.com.communicationtext.course.supercouesrdemo2.schedulearrangement;



import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.course.supercouesrdemo2.CourseAndScore;

/*
    解析值班数据
 */
public class ArrangementPrase  {

    /*
            将群号为groupid的值班信息从所有的值班信息中拿出来   并排序
     */
    public static Vector<ClientArrangement> arrangementPrase(int groupid){
        Vector<ClientArrangement> clientArrangements = new Vector<>();
        for(int i = 0; i< CourseAndScore.clientArrangements.size(); ++i){
            if(CourseAndScore.clientArrangements.get(i).getGroupid()==groupid){
                clientArrangements.add(CourseAndScore.clientArrangements.get(i));
            }
        }

        //排序     按照week升序排列  week相同按照day升序   day相同  按照节数section升序
        Collections.sort(clientArrangements, new Comparator<ClientArrangement>() {

            public int compare(ClientArrangement l, ClientArrangement r) {

                if(l.getWeek()>r.getWeek()) {
                    return 1;
                }else if(l.getWeek()==r.getWeek()){

                    if(l.getWay()>r.getWay()) {
                        return 1;
                    }else if(l.getWay()==r.getWay()){

                        if(l.getSection()>r.getSection()) {
                            return 1;
                        }else if(l.getSection()==r.getSection()){
                            return 0;
                        }else{
                            return -1;
                        }

                    }else{
                        return -1;
                    }

                }else{
                    return -1;
                }
            }

        });

        return clientArrangements;
    }

}
