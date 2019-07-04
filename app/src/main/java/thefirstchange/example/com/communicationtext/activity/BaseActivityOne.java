package thefirstchange.example.com.communicationtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.CustomApplcation;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.widget.HeaderLayout;

public class BaseActivityOne extends BaseForCloseActivity{


    CustomApplcation mApplication;
    protected HeaderLayout mHeaderLayout;

    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        userManager = BmobUserManager.getInstance(this);
//        manager = BmobChatManager.getInstance(this);
        mApplication = CustomApplcation.getInstance();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
    }

    Toast mToast;

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }

    public void ShowToast(final int resId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mToast == null) {
                    mToast = Toast.makeText(BaseActivityOne.this.getApplicationContext(), resId,
                            Toast.LENGTH_LONG);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }
        });
    }

    /** ��Log
     * ShowLog
     * @return void
     * @throws
     */
    public void ShowLog(String msg){
        Log.i("life",msg);
    }

    /**
     * ֻ��title initTopBarLayoutByTitle
     * @Title: initTopBarLayoutByTitle
     * @throws
     */
    public void initTopBarForOnlyTitle(String titleName) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.DEFAULT_TITLE);
        mHeaderLayout.setDefaultTitle(titleName);
    }

    /**
     * ��ʼ��������-�����Ұ�ť
     * @return void
     * @throws
     */
    public void initTopBarForBoth(String titleName, int rightDrawableId,String text,
                                  HeaderLayout.onRightImageButtonClickListener listener) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.base_action_bar_back_bg_selector,
                new OnLeftButtonClickListener());
        mHeaderLayout.setTitleAndRightButton(titleName, rightDrawableId,text,
                listener);
    }

    public void initTopBarForBoth(String titleName, int rightDrawableId,
                                  HeaderLayout.onRightImageButtonClickListener listener) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.base_action_bar_back_bg_selector,
                new OnLeftButtonClickListener());
        mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
                listener);
    }

    /**
     * ֻ����߰�ť��Title initTopBarLayout
     *
     * @throws
     */
    public void initTopBarForLeft(String titleName) {
        mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
        mHeaderLayout.init(HeaderLayout.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mHeaderLayout.setTitleAndLeftImageButton(titleName,
                R.drawable.base_action_bar_back_bg_selector,
                new OnLeftButtonClickListener());
    }

    /** ��ʾ���ߵĶԻ���
     * showOfflineDialog
     * @return void
     * @throws
     */
//    public void showOfflineDialog(final Context context) {
//        DialogTips dialog = new DialogTips(this,"您的账号已在其他设备上登陆!", "请重新登陆");
//
//        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int userId) {
//                CustomApplcation.getInstance().logout();
//                startActivity(new Intent(context, LoginActivity.class));
//                finish();
//                dialogInterface.dismiss();
//            }
//        });
//        // ��ʾȷ�϶Ի���
//        dialog.show();
//        dialog = null;
//    }


    public class OnLeftButtonClickListener implements
            HeaderLayout.onLeftImageButtonClickListener {

        @Override
        public void onClick() {
            finish();
        }
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }
    /** ���ڵ�½�����Զ���½����µ��û����ϼ��������ϵļ�����
     * @Title: updateUserInfos
     * @Description: TODO
     * @param
     * @return void
     * @throws
     */
    public void updateUserInfos(){

//        updateUserLocation();
//
//        userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {
//
//            @Override
//            public void onError(int arg0, String arg1) {
//                // TODO Auto-generated method stub
//                if(arg0==BmobConfig.CODE_COMMON_NONE){
//                    ShowLog(arg1);
//                }else{
//                    ShowLog("查询好友列表失败"+arg1);
//                }
//            }
//
//            @Override
//            public void onSuccess(List<BmobChatUser> arg0) {
//                // TODO Auto-generated method stub
//                // ���浽application�з���Ƚ�
//                CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(arg0));
//            }
//        });
    }

    /** �����û��ľ�γ����Ϣ
     * @Title: uploadLocation
     * @Description: TODO
     * @param
     * @return void
     * @throws
     */
    public void updateUserLocation(){
//        if(CustomApplcation.lastPoint!=null){
//            String saveLatitude  = mApplication.getLatitude();
//            String saveLongtitude = mApplication.getLongtitude();
//            String newLat = String.valueOf(CustomApplcation.lastPoint.getLatitude());
//            String newLong = String.valueOf(CustomApplcation.lastPoint.getLongitude());
////			ShowLog("saveLatitude ="+saveLatitude+",saveLongtitude = "+saveLongtitude);
////			ShowLog("newLat ="+newLat+",newLong = "+newLong);
//            if(!saveLatitude.equals(newLat)|| !saveLongtitude.equals(newLong)){//ֻ��λ���б仯�͸��µ�ǰλ�ã��ﵽʵʱ���µ�Ŀ��
//                User u = (User) userManager.getCurrentUser(User.class);
//                final User user = new User();
//                user.setLocation(CustomApplcation.lastPoint);
//                user.setObjectId(u.getObjectId());
//                user.update(this,new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        // TODO Auto-generated method stub
//                        CustomApplcation.getInstance().setLatitude(String.valueOf(user.getLocation().getLatitude()));
//                        CustomApplcation.getInstance().setLongtitude(String.valueOf(user.getLocation().getLongitude()));
////						ShowLog("��γ�ȸ��³ɹ�");
//                    }
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        // TODO Auto-generated method stub
////						ShowLog("��γ�ȸ��� ʧ��:"+msg);
//                    }
//                });
//            }else{
////				ShowLog("�û�λ��δ�����仯");
//            }
//        }
    }
}
