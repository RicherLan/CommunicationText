package thefirstchange.example.com.communicationtext.group.util;

import java.util.Vector;

import thefirstchange.example.com.communicationtext.gson.UserInGroupInfo;

/*
    某群的成员  工具类
 */
public class UserInGroupInfoUtil {

    public static Vector<UserInGroupInfo>  sortUserInGroupInfo(Vector<UserInGroupInfo> users,String[] corpParts){

        Vector<UserInGroupInfo> temp = new Vector<>();
        Vector<UserInGroupInfo> delete = new Vector<>();
        for(UserInGroupInfo userInGroupInfo : users){
            if(userInGroupInfo.getCorpos()!=null&&userInGroupInfo.getCorpos().equals("官方账号")){
                temp.addElement(userInGroupInfo);
                delete.add(userInGroupInfo);
            }
        }

        for(UserInGroupInfo userInGroupInfo : delete){
            users.removeElement(userInGroupInfo);
        }
        if(users==null||users.size()==0){
            return temp;
        }
        delete = new Vector<>();
        for(UserInGroupInfo userInGroupInfo : users){
            if(userInGroupInfo.getCorpos()!=null&&userInGroupInfo.getCorpos().equals("主席")){
                temp.addElement(userInGroupInfo);
                delete.add(userInGroupInfo);
            }
        }

        for(UserInGroupInfo userInGroupInfo : delete){
            users.removeElement(userInGroupInfo);
        }

        if(users==null||users.size()==0){
            return temp;
        }
        delete = new Vector<>();

        //排序各个部门
        if(corpParts!=null&&corpParts.length!=0&&!corpParts[0].equals("")){
//            先添加各个部长
            for(String corpPart : corpParts){
                if(corpPart.equals("")){
                    continue;
                }

                for(UserInGroupInfo userInGroupInfo : users){
                    if(userInGroupInfo.getCorpos()!=null&&userInGroupInfo.getCorpos().equals("部长")&&userInGroupInfo.getCorppart()!=null&&userInGroupInfo.getCorppart().equals(corpPart)){
                        temp.addElement(userInGroupInfo);
                        delete.add(userInGroupInfo);
                    }
                }

                for(UserInGroupInfo userInGroupInfo : delete){
                    users.removeElement(userInGroupInfo);
                }
                if(users==null||users.size()==0){
                    return temp;
                }
                delete = new Vector<>();
            }

            //添加部门成员
            for(String corpPart : corpParts){
                if(corpPart.equals("")){
                    continue;
                }

                for(UserInGroupInfo userInGroupInfo : users){
                    if(userInGroupInfo.getCorppart()!=null&&userInGroupInfo.getCorppart().equals(corpPart)){
                        temp.addElement(userInGroupInfo);
                        delete.add(userInGroupInfo);
                    }
                }

                for(UserInGroupInfo userInGroupInfo : delete){
                    users.removeElement(userInGroupInfo);
                }
                if(users==null||users.size()==0){
                    return temp;
                }
                delete = new Vector<>();
            }
        }


        //最后添加干事

        for(UserInGroupInfo userInGroupInfo : users){
                temp.addElement(userInGroupInfo);
        }


        return temp;
    }


}
