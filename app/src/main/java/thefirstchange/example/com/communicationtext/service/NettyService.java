package thefirstchange.example.com.communicationtext.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.netty.NettyListener;
import thefirstchange.example.com.communicationtext.netty.client.NettyClient;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;


public class NettyService extends Service implements NettyListener {
    // Vector<HireCarPosInfo> hireCarPosInfos=new Gson().fromJson(string, new TypeToken<Vector<HireCarPosInfo>>(){}.getType());

    private static String TAG = "NettyService";

    private static boolean isLook=false;

    private NetworkReceiver receiver;
    private static String sessionId = null;
    private LocalBroadcastManager localBroadcastManager;

    private ScheduledExecutorService mScheduledExecutorService;

    public static int connetstatuscode=0;

    public static NettyService nettyService;

    public static Timer loginTimer = null;


    public void shutdown() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        receiver = new NetworkReceiver();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        nettyService = this;

        connetstatuscode = NettyListener.STATUS_CONNECT_CLOSED;
        NettyClient.getInstance().setListener(this);
        NettyClient.getInstance().setService(this);
        connect();
        return START_STICKY;
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {		//连接状态监听
        //Timber.d("connect status:%d", statusCode);
        connetstatuscode = statusCode;
        //连接成功
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {

            //重新登陆   每隔10秒重新登陆   直到登陆成功
            if(Config.isLogined){
                if(loginTimer!=null){
                    loginTimer.cancel();
                }
                loginTimer = new Timer();
                loginTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SendToServer.login(ObjectService.personalInfo.getPhonenumber(),ObjectService.personalInfo.getPassword(),"MainUI");
                    }
                },1000,Config.loginIntervalTime);

            }


        //断线
        } else {                     //停止登陆
            if(loginTimer!=null){
                loginTimer.cancel();
            }
        }
    }

    public static boolean isconnect(){
        return NettyClient.getInstance().getConnectStatus();
    }


/*
    @Override
    public void onMessageResponse(ByteBuf byteBuf) {

        try {
            int sumlength = byteBuf.readInt();

            int nameSize = byteBuf.readInt();
            // System.out.println(nameSize);
            byte[] bs = new byte[nameSize];
            byteBuf.readBytes(bs);
//		    		 //String name = new String(byteBuf.readBytes(nameSize).array(), "UTF-8");
            String name = new String(bs, "UTF-8");
            // System.out.println(name);

            int fileSize = byteBuf.readInt();

            byte[] bs2 = new byte[fileSize];
            byteBuf.readBytes(bs2);
            String msgbody = new String(bs2, "UTF-8");

//            Log.e("ttasasasas",name);
//            Log.e("wewewe",msgbody);

            //心跳包服务器回执      什么也不做
            if(name.equals("ping")) {











/*
              //刷新动态消息的页面  就是请求新的动态消息      服务器收到返回动态消息的id
        } else if(name.equals("getnewDongtaiMsgIDsResult")) {
                Vector<Integer> dongtaiMsgIDs = new Gson().fromJson(msgbody, new TypeToken<Vector<Integer>>() {}.getType());
                Vector<Integer> need = new Vector<>();
                Vector<Integer> delete = new Vector<>();
                if (dongtaiMsgIDs != null) {
                    int minid = dongtaiMsgIDs.get(dongtaiMsgIDs.size() - 1);

                    //找出被动态消息主人删除的动态消息
                    for (int i = 0; i < StaticAllList.dongtaiMsgs.size() && i < 8; ++i) {
                        int id = StaticAllList.dongtaiMsgs.get(i).getMsgid();
                        if (id > minid) {
                            boolean flag = true;
                            for (int j = 0; j < dongtaiMsgIDs.size(); ++j) {
                                if (id == dongtaiMsgIDs.get(j)) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                delete.add(i);
                            }
                        }
                    }

                    //找出要请求的动态  即本地没有缓存的动态    只看前6条    每次刷新最多请求6条
                    for (int i = 0; i < dongtaiMsgIDs.size(); ++i) {
                        int id = dongtaiMsgIDs.get(i);
                        boolean flag = true;
                        for (int j = 0; j < StaticAllList.dongtaiMsgs.size() && j < 6; ++j) {
                            if (id == StaticAllList.dongtaiMsgs.get(j).getMsgid()) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            need.add(id);
                        }
                    }

                    for (int i = 0; i < delete.size(); ++i) {
                        int index = delete.get(i);
                        StaticAllList.dongtaiMsgs.remove(index);
                    }


                    DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getDTMsgById", need);
                    dongtaiUploadThread.start();

                    Set<Integer> set = new HashSet<>();    //本地没有缓存的动态消息的动态的内容
                    for(int j=0;j<need.size();++j){
                        int DTmsgid = need.get(j);

                        for(int i=0;i<StaticAllList.dongtaiMsgs.size();++i){
                            if(DTmsgid>StaticAllList.dongtaiMsgs.get(i).getMsgid()){
                                break;
                            }
                            if(DTmsgid==StaticAllList.dongtaiMsgs.get(i).getMsgid()){
                                int DTid = StaticAllList.dongtaiMsgs.get(i).getDongtaiid();
                                boolean flag = true;
                                for(int k=0;k<StaticAllList.dongtaisMsgDongtai.size();++k){
                                    if(DTid==StaticAllList.dongtaisMsgDongtai.get(k).getId()){
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    set.add(DTid);
                                }
                            }
                        }
                    }

                    Vector<Integer> vector = new Vector<>();
                   for(Integer integer:set){
                       vector.add(integer);
                   }

                    DongtaiUploadThread dongtaiUploadThread2 = new DongtaiUploadThread("getDTFirstImAndtextById", vector);
                    dongtaiUploadThread2.start();

                }


            //用户上拉刷新动态消息的页面  就是请求旧的动态消息      服务器收到返回动态消息的id    否则客户端超时  就提示刷新失败
        }else if(name.equals("getoldDongtaiMsgIDsResult")) {

                Vector<Integer> dongtaiIDs = new Gson().fromJson(msgbody, new TypeToken<Vector<Integer>>() {}.getType());
                Vector<Integer> need = new Vector<>();
                Vector<Integer> delete = new Vector<>();
                if (dongtaiIDs != null) {
                    int minid = dongtaiIDs.get(dongtaiIDs.size() - 1);

                    //找出被用户删除的动态
                    for (int i = 0; i < StaticAllList.dongtaiMsgs.size() && i < 8; ++i) {
                        int id = StaticAllList.dongtaiMsgs.get(i).getMsgid();
                        if (id > minid) {
                            boolean flag = true;
                            for (int j = 0; j < dongtaiIDs.size(); ++j) {
                                if (id == dongtaiIDs.get(j)) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                delete.add(i);
                            }
                        }
                    }

                    //找出要请求的动态  即本地没有缓存的动态
                    for (int i = 0; i < dongtaiIDs.size(); ++i) {
                        int id = dongtaiIDs.get(i);
                        boolean flag = true;
                        for (int j = 0; j < StaticAllList.dongtaiMsgs.size() && j < 6; ++j) {
                            if (id == StaticAllList.dongtaiMsgs.get(j).getMsgid()) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            need.add(id);
                        }
                    }

                    for (int i = 0; i < delete.size(); ++i) {
                        int index = delete.get(i);
                        StaticAllList.dongtaiMsgs.remove(index);
                    }


                    DongtaiUploadThread dongtaiUploadThread = new DongtaiUploadThread("getDTMsgById", need);
                    dongtaiUploadThread.start();

                    Set<Integer> set = new HashSet<>();    //本地没有缓存的动态消息的动态的内容
                    for(int j=0;j<need.size();++j){
                        int DTmsgid = need.get(j);

                        for(int i=0;i<StaticAllList.dongtaiMsgs.size();++i){
                            if(DTmsgid>StaticAllList.dongtaiMsgs.get(i).getMsgid()){
                                break;
                            }
                            if(DTmsgid==StaticAllList.dongtaiMsgs.get(i).getMsgid()){
                                int DTid = StaticAllList.dongtaiMsgs.get(i).getDongtaiid();
                                boolean flag = true;
                                for(int k=0;k<StaticAllList.dongtaisMsgDongtai.size();++k){
                                    if(DTid==StaticAllList.dongtaisMsgDongtai.get(k).getId()){
                                        flag = false;
                                    }
                                }
                                if(flag){
                                    set.add(DTid);
                                }
                            }
                        }
                    }

                    Vector<Integer> vector = new Vector<>();
                    for(Integer integer:set){
                        vector.add(integer);
                    }

                    DongtaiUploadThread dongtaiUploadThread2 = new DongtaiUploadThread("getDTFirstImAndtextById", vector);
                    dongtaiUploadThread2.start();

                }

                Intent intent2 = new Intent("thefirstchange.example.com.communicationtext.LIFESHOWNOTIFICATION");
                intent2.putExtra("type", "getoldDongtaiMsgIDsResult");
                sendCast(intent2);

                String type = "getoldDongtaiIDsResult";
                Intent intent = new Intent("thefirstchange.example.com.communicationtext.MAIN");
                intent.putExtra("rs", "ok");
                intent.putExtra("type", type);
                sendCast(intent);


                //用户刷新动态消息的页面  就是请求新的动态消息    返回动态消息
        }else if(name.equals("getDTMsgByIdResult")){

                JSONObject jsonObject = new JSONObject(msgbody);
                DongtaiMsg dongtai = new DongtaiMsg();
                int DTMsgId = jsonObject.getInt("DTMsgId");
                int DTId = jsonObject.getInt("DTId");
                String userid = jsonObject.getString("DTId");
                String username = jsonObject.getString("username");
//                String usericonpath = jsonObject.getString("usericonpath");
                String type = jsonObject.getString("type");
                dongtai.setMsgid(DTMsgId);
                dongtai.setDongtaiid(DTId);
                dongtai.setUserid(userid);
//                dongtai.setUsericon(usericonpath);
                dongtai.setUsername(username);
                dongtai.setType(type);

                if(type.equals("praise")){

                }else if(type.equals("transmit")){

                }else if(type.equals("todongtai")){
                    String content =  jsonObject.getString("content");
                    dongtai.setMsg(content);
                }else if(type.equals("tocomment")){
                    String content =  jsonObject.getString("content");
                    int beComId = jsonObject.getInt("beComId");
                    String beComUSerid = jsonObject.getString("beComUSerid");
                    String beComUsername = jsonObject.getString("beComUsername");

                    dongtai.setMsg(content); dongtai.setCommentid(beComId);
                    dongtai.setBecommenteduserid(beComUSerid);
                    dongtai.setBecommentedusername(beComUsername);

                }



            if(StaticAllList.dongtaiMsgs.size()==0){
                StaticAllList.dongtaiMsgs.add(dongtai);
            }else if(StaticAllList.dongtaiMsgs.get(0).getMsgid()<dongtai.getMsgid()){
                StaticAllList.dongtaiMsgs.add(0,dongtai);
            }else if(StaticAllList.dongtaiMsgs.get(0).getMsgid()>=dongtai.getMsgid()){
                int index = -1;
                boolean flag = false;
                for(int i=0;i<StaticAllList.dongtaiMsgs.size();++i){
                    if(StaticAllList.dongtaiMsgs.get(i).getMsgid()==dongtai.getMsgid()){
                        index = i;
                        flag = true;
                        break;
                    }
                    if(StaticAllList.dongtaiMsgs.get(i).getMsgid()<dongtai.getMsgid()){
                        index = i;
                    }
                }
                if(flag){
                    StaticAllList.dongtaiMsgs.remove(index);
                }
                if(index!=-1){
                    StaticAllList.dongtaiMsgs.add(index,dongtai);
                }else{
                    StaticAllList.dongtaiMsgs.add(dongtai);
                }
            }
            Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWNOTIFICATION");
            intent.putExtra("type","getDTMsgByIdResult");
            sendCast(intent);

            //用户刷新动态消息的页面        返回动态消息的发送者的头像
        }else if(name.startsWith("getDTMsgUserIcRs")){
                if (name.split(" ").length < 2) {
                    return;
                }

                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);
                int DTMsgId = jsonObject.getInt("DTMsgId");

                //从动态列表中找到对应的动态id   把图片路径保存进去
                String path  = Config.DtIcpath+"/"+UUID.randomUUID().toString()+".jpg";
                byte[] bs3 = Base64.decode(bs2,Base64.DEFAULT);
                OutputStream out = new FileOutputStream(path);
                InputStream is = new ByteArrayInputStream(bs3);
                byte[] buff = new byte[1024];
                int len = 0;
                while((len=is.read(buff))!=-1){
                    out.write(buff, 0, len);
                }
                is.close();
                out.close();
                for(int i=0;i<StaticAllList.dongtaiMsgs.size();++i){
                    if(StaticAllList.dongtaiMsgs.get(i).getMsgid()==DTMsgId){
                        StaticAllList.dongtaiMsgs.get(i).setUsericon(path);
                        break;
                    }
                }

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWNOTIFICATION");
                intent.putExtra("type","getDTMsgUserIcRs");
                sendCast(intent);
            }

            //用户刷新动态消息的页面        返回动态消息的动态的第一张图片和内容
        else if(name.startsWith("getDTFirstImAndtextById")) {
//                String json2 = "{\"type\":\"content\",\"dtid\":\""+dtid+"\",\"userid\":\""+userid+"\",\"username\":\""+username+"\",\"content\":\""+content+"\"}";
//                String jString = "{\"type\":\"image\",\"dtid\":\""+dongtai.getId()+"\"}";
                if (name.split(" ").length < 2) {
                    return;
                }
                String s = name;
                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);

                int dongtaiid = jsonObject.getInt("dtid");
                String type = jsonObject.getString("type");
                if(type.equals("content")){

                    String userid = jsonObject.getString("userid");
                    String username = jsonObject.getString("username");
                    String content = jsonObject.getString("content");
                    Dongtai dongtai = new Dongtai();
                    dongtai.setId(dongtaiid);
                    dongtai.setSdid(userid);
                    dongtai.setSdname(username);
                    dongtai.setContent(content);

                    StaticAllList.dongtaisMsgDongtai.add(dongtai);

                }else{
                    //从动态列表中找到对应的动态id   把图片路径保存进去
                    String path = Config.dongtaipath + "/" + UUID.randomUUID().toString() + ".jpg";
                    byte[] bs3 = Base64.decode(bs2, Base64.DEFAULT);
                    OutputStream out = new FileOutputStream(path);
                    InputStream is = new ByteArrayInputStream(bs3);
                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }
                    is.close();
                    out.close();
                    for (int i = 0; i < StaticAllList.dongtaisMsgDongtai.size(); ++i) {
                        if (StaticAllList.dongtaisMsgDongtai.get(i).getId() == dongtaiid) {
                            StaticAllList.dongtaisMsgDongtai.get(i).getImph().add(path);
                            break;
                        }
                    }
                }

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWNOTIFICATION");
                intent.putExtra("type","getDTFirstImAndtextById");
                sendCast(intent);

            }

            //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
            else if(name.startsWith("gNComByDTidRS")){
                if (name.split(" ").length < 2) {
                    return;
                }
                String s = name;
                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);

                int dongtaiid = jsonObject.getInt("dtid");

                final Vector<DTComRoot> dtComRoots = new Gson().fromJson(msgbody, new TypeToken<Vector<DTComRoot>>() {}.getType());
                StaticAllList.dTComRootMap.clear();
                StaticAllList.dTComRootMap.put(dongtaiid,dtComRoots);

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.COMMENTSHOW");
                intent.putExtra("type","gNComByDTidRS");
                sendCast(intent);

                new Thread(){
                    public void run(){
                        for(int i=0;i<dtComRoots.size();++i){
                            String uid = dtComRoots.get(i).getRootuid();
                            SendToServer.getRtComUIC(uid);
                        }
                    }
                }.start();


            }

            //进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
            else if(name.startsWith("getRtComUIC")){
                if (name.split(" ").length < 2) {
                    return;
                }
                String s = name;
                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);

                String ph = jsonObject.getString("ph");
                for(int i:StaticAllList.dTComRootMap.keySet()){
                    for(int j=0;j<StaticAllList.dTComRootMap.get(i).size();++j){
                        if(StaticAllList.dTComRootMap.get(i).get(j).getRootuid().equals(ph)){
                            StaticAllList.dTComRootMap.get(i).get(j).setRootuic(bs2);
                        }
                    }
                }

                Intent intent=new Intent("thefirstchange.example.com.communicationtext.COMMENTSHOW");
                intent.putExtra("type","getRtComUIC");
                sendCast(intent);

            }

            //进入某动态的所有评论界面    上拉刷新   返回根评论总共10条   大的评论下最多回执3条
            else if(name.startsWith("gOComByDTidRs")){
                if (name.split(" ").length < 2) {
                    return;
                }
                String s = name;
                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);

                int dongtaiid = jsonObject.getInt("dtid");

                final Vector<DTComRoot> dtComRoots = new Gson().fromJson(msgbody, new TypeToken<Vector<DTComRoot>>() {}.getType());

                if(!StaticAllList.dTComRootMap.containsKey(dongtaiid)){
                    StaticAllList.dTComRootMap.put(dongtaiid,dtComRoots);
                }else{
                    for(int k=0;k<dtComRoots.size();++k){
                        StaticAllList.dTComRootMap.get(dongtaiid).add(dtComRoots.get(k));
                    }

                }

                new Thread(){
                    public void run(){
                        for(int i=0;i<dtComRoots.size();++i){
                            String uid = dtComRoots.get(i).getRootuid();
                            SendToServer.getRtComUIC(uid);
                        }
                    }
                }.start();

            }


        //给动态点赞是否成功
        else if(name.equals("dongtaipraiseResult")){
            JSONObject jsonObject = new JSONObject(msgbody);
            String rs = jsonObject.getString("type");


            //给动态评论是否成功
        }else if(name.equals("dtComRs")){
            JSONObject jsonObject = new JSONObject(msgbody);
            String rs = jsonObject.getString("rs");
                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.COMMENTSHOW");
                intent2.putExtra("type","dtComRs");
                intent2.putExtra("rs",rs);
                sendCast(intent2);

            //给动态的评论 评论 是否成功
        }else if(name.equals("dongtaicommentCommentResult")){
            JSONObject jsonObject = new JSONObject(msgbody);
            String rs = jsonObject.getString("rs");
                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
                intent2.putExtra("type","dongtaicommentCommentResult");
                intent2.putExtra("rs",rs);
                sendCast(intent2);

            //拿到自己的动态消息  一般是刚上线的时候
        }else if(name.equals("getDongtaiMsgResult")){
            MyMessageQueue.dongtaiMsgNotRead =  new Gson().fromJson(msgbody, new TypeToken<Vector<DongtaiMsg>>(){}.getType());


            //某人给自己点赞
        }else if(name.equals("userPraiseForyou")){
//               JSONObject jsonObject = new JSONObject(msgbody);
//               String ph = jsonObject.getString("ph");
//                String icon = jsonObject.getString("usericonpath");
//                MyMessageQueue.dongtaiMsgNotRead.put(ph,icon);
//
//                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
//                intent2.putExtra("type","userPraiseForyou");
//                intent2.putExtra("ph",ph);
//                sendCast(intent2);

                //某人给自己的动态评论
            }else if(name.startsWith("UCmtFU")){
                if (name.split(" ").length < 2) {
                    return;
                }
                String s = name;
                String msgbody2 = name.split(" ")[1];
                JSONObject jsonObject = new JSONObject(msgbody2);

                String ph = jsonObject.getString("ph");
                int dtid = jsonObject.getInt("dtid");

                for(int i=0;i<StaticAllList.dongtais.size();++i){
                    if(StaticAllList.dongtais.get(i).getId()==dtid){
                        StaticAllList.dongtais.get(i).setComnum(StaticAllList.dongtais.get(i).getComnum()+1);
                        break;
                    }
                }

//                String icon = jsonObject.getString("usericonpath");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bs2,0,bs2.length);
                MyMessageQueue.dongtaiMsgNotRead.put(ph,bitmap);

                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
                intent2.putExtra("type","userCommentForyou");
                intent2.putExtra("ph",ph);
                sendCast(intent2);

                //某人给自己对某动态的评论评论
            } else if(name.equals("UCmtForYCmtOfDT")){
//                JSONObject jsonObject = new JSONObject(msgbody);
//                String ph = jsonObject.getString("ph");
//                String icon = jsonObject.getString("usericonpath");
//                MyMessageQueue.dongtaiMsgNotRead.put(ph,icon);
//
//                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
//                intent2.putExtra("type","UCmtForYCmtOfDT");
//                intent2.putExtra("ph",ph);
//                sendCast(intent2);

                //某人给自己的动态的某一条动态评论
            }else if(name.equals("UCmtForYDTCmt")){
//                JSONObject jsonObject = new JSONObject(msgbody);
//                String ph = jsonObject.getString("ph");
//                String icon = jsonObject.getString("usericonpath");
//                MyMessageQueue.dongtaiMsgNotRead.put(ph,icon);
//
//                Intent intent2=new Intent("thefirstchange.example.com.communicationtext.LIFESHOWFRAGMENT");
//                intent2.putExtra("type","UCmtForYDTCmt");
//                intent2.putExtra("ph",ph);
//                sendCast(intent2);


*/

/*
            }
                // 获取验证码   一个手机号一天最多3次   获取验证码的结果
            else if(name.equals("getPhonenumberCodeResult")){
                JSONObject jsonObject = new JSONObject(msgbody);
                String rs = jsonObject.getString("type");
                if(rs.equals("ok")){

                    String code = jsonObject.getString("code");


                    //该号码今天发送的验证码已经达到3次了
                }else if(rs.equals("TodayexceedThreeTimes")){

                }else {

                }

                //注册新用户的返回结果
            }else if(name.equals("registeruserResult")){
                JSONObject jsonObject = new JSONObject(msgbody);
                String rs = jsonObject.getString("type");
                if(rs.equals("ok")){

                    //验证码错误
                }else  if(rs.equals("codeerror")){

                    //其他错误
                }else{

                }


            }



        }catch(Exception a) {
            a.printStackTrace();
        }

    }*/



    public static class MainLookReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isLook){
                isLook=true;
            }else {
                isLook=false;
            }

        }
    }

    public  static  void connect(){
        if(!NettyClient.getInstance().isInitOK()){
            NettyClient.getInstance().setListener(nettyService);
            NettyClient.getInstance().setService(nettyService);
        }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器

                }
            }).start();


    }

    public  static  void disconnect(){
        if (NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().disconnect();//连接服务器
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        shutdown();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    //connect();
                }
            }
        }
    }

    public void sendCast(Intent intent){
        sendBroadcast(intent);
    }



}
