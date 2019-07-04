package thefirstchange.example.com.communicationtext.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.config.MainConstant;
import thefirstchange.example.com.communicationtext.config.PictureSelectorConfig;
import thefirstchange.example.com.communicationtext.dongtai.Dongtai;
import thefirstchange.example.com.communicationtext.gson.StaticAllList;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.nice9layout.ImageNice9Layout;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.util.MyDialog;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class EditLifeShowActivity extends BaseForCloseActivity {

//    private static String MY = "thefirstchange.example.com.communicationtext.EDITLIFESHOW";
    private static final String TAG="EditLifeShowActivity";
    private Context context;
    private ImageNice9Layout imageNice9Layout;
    private Vector<String> mPicList=new Vector<>();
    int num = 0;
    List<String> picStrings = new ArrayList<>();
    String[] pices=new String[10];
    private ImageView addPic;
    private TextView finishSelf;
    private TextView publishShow;
    private TextView addPicDia;
    private EditText editShow;

    public static Timer addDongtaiTimer;
    public static EditLifeShowActivity editLifeShowActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_life_show);

        editLifeShowActivity = this;
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);
        context=this;
        addPicDia=(TextView)findViewById(R.id.add_pic_dialog);
        imageNice9Layout = (ImageNice9Layout) findViewById(R.id.image_nice9_layout);
        addPic=(ImageView)findViewById(R.id.add_pic);
        finishSelf=(TextView)findViewById(R.id.finish_edit_show);
        publishShow=(TextView)findViewById(R.id.publish_show);
        editShow=(EditText)findViewById(R.id.edit_life_show_edit);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        publishShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!NetworkUtils.isConnected(EditLifeShowActivity.this)){
                    Toast.makeText(EditLifeShowActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Pic.add(new picEntities(mPicList));
//                Log.e("fsdf",mPicList.get(0));
                long time = System.currentTimeMillis();
                StaticAllList.nowDongtai = new Dongtai();
                for (int i=0;i<mPicList.size();i++){
                    StaticAllList.nowDongtai.getImph().add(mPicList.get(i));
                }
                StaticAllList.nowDongtai.setComnum(0);
                StaticAllList.nowDongtai.setId(-1);
                StaticAllList.nowDongtai.setPranum(0);
                StaticAllList.nowDongtai.setSdid(ObjectService.personalInfo.getPhonenumber());

                StaticAllList.nowDongtai.setTime(time);
                StaticAllList.nowDongtai.setImnum(mPicList.size());
                StaticAllList.nowDongtai.setType("user");

                if (mPicList.size()<=0&&editShow.getText().toString().trim().isEmpty()){
                    Toast.makeText(EditLifeShowActivity.this,"无效文字和图片",Toast.LENGTH_SHORT).show();
                }else {

                    int index=-1;
                    for(int i=editShow.getText().toString().length()-1;i>=0;--i){
                        if(editShow.getText().toString().charAt(i)!=' '){
                            index=i+1;
                            break;
                        }
                    }
                    String dttext="";
                    if(index!=-1){
                        dttext = editShow.getText().toString().substring(0,index);
                    }
                    StaticAllList.nowDongtai.setContent(dttext);

                    StaticAllList.dongtaiatate="uploading";

                    MyDialog.showBottomLoadingDialog(EditLifeShowActivity.this);

                    SendToServer.addDongtai("user",dttext,mPicList.size(),time);

                    addDongtaiTimer = new Timer();
                    addDongtaiTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyDialog.dismissBottomLoadingDialog();
                                    Toast.makeText(EditLifeShowActivity.this,"服务器繁忙,请稍后重试",Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    },50000);
                }
//                editShow.setText("");
//                mPicList.clear();
            }
        });
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
            }
        });

//        Resources r =context.getResources();
//        Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + r.getResourcePackageName(R.drawable.user_image1) + "/"
//                + r.getResourceTypeName(R.drawable.user_image1) + "/"
//                + r.getResourceEntryName(R.drawable.user_image1));
//
//        String path=uri.getPath();
//        Log.e("fdfsdfsdf",path+"55555555555555555555555");
//        for (int i=0;i<9;i++){
//            pices[i]=path;
//
//        }
        imageNice9Layout.setItemDelegate(new ImageNice9Layout.ItemDelegate() {
            @Override
            public void onItemClick(int position) {
                viewPluImg(position);
//                if (position == num - 1) {
//                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
//                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
//                        //最多添加5张图片
//                        viewPluImg(position);
//                    } else {
//                        //添加凭证图片
//                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
//                    }
//                } else {
//                    viewPluImg(position);
//                }

            }
        });
        bindData();

    }

    private void bindData() {



//
        imageNice9Layout.bindData(mPicList);
    }

    public void onMinus(){
        num --;
        if (num <= 1){
            num = 1;
        }
        if (num >= 9) {
            addPic.setImageResource(R.drawable.ic_add_pic_full);
        }else {
            addPic.setImageResource(R.drawable.ic_add_pic);
        }
        int t=9-num;
        addPicDia.setText("剩余"+t+"张");
        addPic.setClickable(true);
        bindData();
    }

    public void onAdd(){
        num ++;
        if (num >= 9) {
            num = 9;
            addPic.setImageResource(R.drawable.ic_add_pic_full);
            addPicDia.setText("已选满");
            addPic.setClickable(false);
        }else {
            addPic.setImageResource(R.drawable.ic_add_pic);
            int t=9-num;
            addPicDia.setText("剩余"+t+"张");
            addPic.setClickable(true);
        }

        bindData();
    }

    private void viewPluImg(int position) {
        Intent intent = new Intent(context, PlusImageActivity.class);
        ArrayList<String> arrayList=new ArrayList<>();
       for (int i=0;i<mPicList.size();i++){
           arrayList.add(mPicList.get(i));
       }
        intent.putStringArrayListExtra(MainConstant.IMG_LIST,arrayList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getPath(); //s图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
               // mGridViewAddImgAdapter.notifyDataSetChanged();
                onAdd();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            //mGridViewAddImgAdapter.notifyDataSetChanged();
            num=toDeletePicList.size()-1;
            int t=9-num-1;

            addPicDia.setText("剩余"+t+"张");
            bindData();
        }
    }

//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//           if(intent.getAction().equals(MY)){
//
//               String type = intent.getStringExtra("type");
//               //动态的基本信息是否发表成功  收到该消息就是成功
//               if(type.equals("addDongtaiResult")){
//                    timer.cancel();
//                    String rs = intent.getStringExtra("rs");
//                    int dongtaiid = intent.getIntExtra("dongtaiid",-1);
//                    if(rs.equals("error")){
//                        Toast.makeText(EditLifeShowActivity.this, "服务器繁忙,请稍后重试", Toast.LENGTH_SHORT).show();
//                        StaticAllList.nowDongtai=null;
//                    }else {
//                        StaticAllList.nowDongtai.setId(dongtaiid);
//                    }
//               }else if(type.equals("adddongtaiimageResult")){
//                    timer.cancel();
//                   String rs = intent.getStringExtra("rs");
//                   int dongtaiid = intent.getIntExtra("dongtaiid",-1);
//                    if(rs.equals("error")){
//                        Toast.makeText(EditLifeShowActivity.this, "服务器繁忙,请稍后重试", Toast.LENGTH_SHORT).show();
//                        StaticAllList.nowDongtai=null;
//                    }
//               }
//
//
//           }
//
//        }
//    };
//
//    protected  void onDestroy(){
//        super.onDestroy();
//
//        unregisterReceiver(broadcastReceiver);
//
//    }
//
//    protected  void onResume() {
//
//        super.onResume();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MY);
//        registerReceiver(broadcastReceiver,intentFilter);
////        if(shouldRefresh){
//
////            shouldRefresh = false;
////        }
//    }


}
