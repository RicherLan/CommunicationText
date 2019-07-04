package thefirstchange.example.com.communicationtext.service;

import android.graphics.Bitmap;

import thefirstchange.example.com.communicationtext.gson.GroupBasicInfo;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;

/*
    保存全局使用的  对象
 */
public class ObjectService {

    public static PersonalInfo personalInfo = null;
    public static Bitmap personalIcon = null;

    public static GroupBasicInfo groupBasicInfo = null;       //搜索群时  服务器发来的对象


}
