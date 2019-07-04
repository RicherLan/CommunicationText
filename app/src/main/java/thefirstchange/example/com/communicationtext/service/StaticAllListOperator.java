package thefirstchange.example.com.communicationtext.service;

import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.gson.User;

/*
    对StaticAllList中的对象    进行的操作
 */
public class StaticAllListOperator {

    /*
     查看某人的资料时   服务器返回的资料暂时保存在这
     存储达到150个时   就移除前30个
     */
    public static void add2UserTemps(User user){

        if(StaticAllList.usertemps.size()>=150){
            int sum = 0;
            for(String ph:StaticAllList.usertemps.keySet()){
                StaticAllList.usertemps.remove(ph);
                ++sum;
                if(sum>=30){
                    break;
                }
            }
        }

        if(user.getIcon()==null||user.getIcon().equals("")){
            if(StaticAllList.usertemps.containsKey(user.getPhonenumber())){
                user.setIcon(StaticAllList.usertemps.get(user.getPhonenumber()).getIcon());
            }
        }

        StaticAllList.usertemps.put(user.getPhonenumber(),user);

    }

    public static boolean isUserTempsContainPh(String ph){

        if(StaticAllList.usertemps!=null&&StaticAllList.usertemps.containsKey(ph)){
            return true;
        }
        return false;
    }

}
