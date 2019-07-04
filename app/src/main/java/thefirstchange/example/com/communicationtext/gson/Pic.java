package thefirstchange.example.com.communicationtext.gson;

import java.util.ArrayList;

public class Pic {
    private static ArrayList<picEntities> arrayList=new ArrayList<>();
    public static ArrayList<picEntities> getArrayList(){
        return arrayList;
    }
    public static void add(picEntities mpicEntities){
        arrayList.add(0,mpicEntities);

    }
    public picEntities getPicEntities(int i){
        return arrayList.get(i);
    }
    public int getCount(){
        return arrayList.size();
    }

}
