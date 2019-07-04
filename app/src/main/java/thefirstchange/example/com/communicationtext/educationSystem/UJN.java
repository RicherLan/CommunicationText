package thefirstchange.example.com.communicationtext.educationSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UJN {


	public static Vector<String> schoolname = new Vector<>();

	public static Map<String,String> zhuxiaoquBuildingNum = new HashMap<>();

	public static Map<String,String> shungengBuildingNum = new HashMap<>();
	

	public static Map<String,String> mingshuiBuildingNum = new HashMap<>();
	

	public static Vector<String> getBuildings(String shoolname){
		Vector<String> strings = new Vector<>();

		if(shoolname.equals("主校区")){

			if(zhuxiaoquBuildingNum!=null){
				for(String str : zhuxiaoquBuildingNum.keySet()){
					strings.addElement(str);
				}
			}

		}else if(shoolname.equals("舜耕校区")){

			if(shungengBuildingNum!=null){
				for(String str : shungengBuildingNum.keySet()){
					strings.addElement(str);
				}
			}

		}else if(shoolname.equals("明水校区")){

			if(mingshuiBuildingNum!=null){
				for(String str : mingshuiBuildingNum.keySet()){
					strings.addElement(str);
				}
			}

		}
		return strings;
	}

	public static void init() {

		schoolname = new Vector<>();
		schoolname.addElement("主校区");
		schoolname.addElement("舜耕校区");
		schoolname.addElement("明水校区");

		zhuxiaoquBuildingNum = new HashMap<>();
		shungengBuildingNum = new HashMap<>();
		mingshuiBuildingNum = new HashMap<>();

		//初始化  主校区的  楼号
		zhuxiaoquBuildingNum.put("0020","0020");
		zhuxiaoquBuildingNum.put("辰星园","0009");
		zhuxiaoquBuildingNum.put("第八教学楼","0011");
		zhuxiaoquBuildingNum.put("第二教学楼","0007");
		zhuxiaoquBuildingNum.put("第七教学楼","0010");
		zhuxiaoquBuildingNum.put("第三教学楼","0003");
		zhuxiaoquBuildingNum.put("第十教学楼","0006");
		zhuxiaoquBuildingNum.put("第十一教学楼","0008");
		zhuxiaoquBuildingNum.put("第四教学楼","0002");
		zhuxiaoquBuildingNum.put("第五教学楼","0016");
		zhuxiaoquBuildingNum.put("第一教学楼","0004");
		zhuxiaoquBuildingNum.put("机械楼 ","0015");
		zhuxiaoquBuildingNum.put("梅花馆","0005");
		zhuxiaoquBuildingNum.put("特教楼","0013");
		zhuxiaoquBuildingNum.put("体育场","0021");
		zhuxiaoquBuildingNum.put("信息楼","0012");
		zhuxiaoquBuildingNum.put("逸夫楼","0014");
		zhuxiaoquBuildingNum.put("音乐楼","0022");
		
		//初始化舜耕校区  
		shungengBuildingNum.put("1001", "1001");
		shungengBuildingNum.put("1002", "1002");
		shungengBuildingNum.put("1003", "1003");
		shungengBuildingNum.put("1004", "1004");
		shungengBuildingNum.put("1005", "1005");
		shungengBuildingNum.put("1100", "1100");
		
		//初始化明水校区
		mingshuiBuildingNum.put("3001","3001");
		mingshuiBuildingNum.put("3002","3002");
		mingshuiBuildingNum.put("3003","3003");
		
	}

	public static String getBuildingCode(String schoolname,String buildingname){


		if(schoolname.equals("主校区")){
			return zhuxiaoquBuildingNum.get(buildingname);
		}else if(schoolname.equals("舜耕校区")){
			return shungengBuildingNum.get(buildingname);
		}
		return mingshuiBuildingNum.get(buildingname);
	}

}
