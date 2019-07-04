package thefirstchange.example.com.communicationtext.util;

import thefirstchange.example.com.communicationtext.gson.MyFriend;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.gson.User;

public class MyObjectTools {

    //将MyFriend类型的对象转化为User类型的对象
    public static void MyFriendConvertToUser(MyFriend myFriend , User user){
        user.setType(myFriend.getType()); ;
        user.setPhonenumber(myFriend.getPhonenumber());;
        user.setNickname(myFriend.getNickname());;
        user.setPassword(myFriend.getPassword());;
        user.setIcon(myFriend.getIcon());;
        user.setQq(myFriend.getQq());;
        user.setWeixin(myFriend.getWeixin()); ;
        user.setAddress(myFriend.getAddress()); ;
        user.setSex(myFriend.getSex());;
        user.setSchoolname(myFriend.getSchoolname());  ;
        user.setDepartmentname(myFriend.getDepartmentname()); ;
        user.setMajorname(myFriend.getMajorname()); ;

        user.setCorporationname(myFriend.getCorporationname());;         //加入的部门   空格隔开
        user.setCorporationposition(myFriend.getCorporationposition()); ;     //在部门中的职位

        user.setBirthday(myFriend.getBirthday());;
        user.setRuxueyear(myFriend.getRuxueyear());;
        user.setFrom(myFriend.getFrom()); ;

        user.setIntroduce(myFriend.getIntroduce());;

    }


    //将User类型的对象转化为MyFriend类型的对象
    public static MyFriend UserConvertToMyFriend( User user){
        MyFriend myFriend = new MyFriend();

        myFriend.setAddress(user.getAddress());
        myFriend.setCorporationname(user.getCorporationname());
        myFriend.setCorporationposition(user.getCorporationposition());
        myFriend.setDepartmentname(user.getDepartmentname());
        myFriend.setFriendgroup(0);
        myFriend.setIcon(user.getIcon());
        myFriend.setMajorname(user.getMajorname());
        myFriend.setNickname(user.getNickname());
        myFriend. setPassword(user.getPassword());
        myFriend.setPhonenumber(user.getPhonenumber());
        myFriend.setQq(user.getQq());
        myFriend.setRemark(user.getNickname());
        myFriend.setSchoolname(user.getSchoolname());
        myFriend.setSex(user.getSex());
        myFriend.setWeixin(user.getWeixin());

        return myFriend;
    }


    public static User personalInfoConvertToUser(PersonalInfo personalInfo){
        User user = new User();
        user.setType(personalInfo.getType()); ;
        user.setPhonenumber(personalInfo.getPhonenumber());;
        user.setNickname(personalInfo.getNickname());;
        user.setPassword(personalInfo.getPassword());;
        user.setIcon(personalInfo.getIcon());;
        user.setQq(personalInfo.getQq());;
        user.setWeixin(personalInfo.getWeixin()); ;
        user.setAddress(personalInfo.getAddress()); ;
        user.setSex(personalInfo.getSex());;
        user.setSchoolname(personalInfo.getSchoolname());  ;
        user.setDepartmentname(personalInfo.getDepartmentname()); ;
        user.setMajorname(personalInfo.getMajorname()); ;

        user.setCorporationname(personalInfo.getCorporationname());;         //加入的部门   空格隔开
        user.setCorporationposition(personalInfo.getCorporationposition()); ;     //在部门中的职位

        user.setBirthday(personalInfo.getBirthday());;
        user.setRuxueyear(personalInfo.getRuxueyear());;
        user.setFrom(personalInfo.getFrom()); ;

        user.setIntroduce(personalInfo.getIntroduce());;

        return user;
    }
}
