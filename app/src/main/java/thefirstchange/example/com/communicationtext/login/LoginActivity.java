package thefirstchange.example.com.communicationtext.login;

import android.Manifest;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;
import thefirstchange.example.com.communicationtext.MainActivity;
import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.AlterIP;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.AlterPasswordActivity;
import thefirstchange.example.com.communicationtext.activity.myselfactivity.ForgetPasswordActivity;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.gson.PersonalInfo;
import thefirstchange.example.com.communicationtext.netty.client.NettyClient;
import thefirstchange.example.com.communicationtext.netty.commandToserver.SendToServer;
import thefirstchange.example.com.communicationtext.service.NettyService;
import thefirstchange.example.com.communicationtext.service.ObjectService;
import thefirstchange.example.com.communicationtext.usersignin.RegisterSchoolInfoActivity;
import thefirstchange.example.com.communicationtext.util.DensityUtiltwo;
import thefirstchange.example.com.communicationtext.util.MyTools;
import thefirstchange.example.com.communicationtext.util.NetworkUtils;

public class LoginActivity extends BaseForCloseActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{

	private Vector<String> user_icon_path = new Vector<>();
	private Vector<String> user_icon_name = new Vector<>();
	private String result = "";

	AnimatorSet set = new AnimatorSet();

	private CircleImageView user_image_login;
	private TextView mBtnLogin;
	private TextView failLogin;
	private ImageView finish_now;
	private EditText userAccount;
	private EditText userPassword;
	
	private View progress;
	
	private View mInputLayout;

	private float mWidth, mHeight;

	private LinearLayout mName, mPsw;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private CheckBox remberBox;
	private static final String MYACTION="thefirstchange.example.com.communicationtext.LOGIN_INFO";
	private String account;
	private String password;

	private VideoView mVideoView;

	private TextView signUser;
	private RelativeLayout progressLay;
	private TextView forgetPassword;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		Window window = getWindow();
//		//隐藏标题栏
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		//隐藏状态栏
//		//定义全屏参数
//		int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//		//设置当前窗体为全屏显示
//		window.setFlags(flag, flag);


		setContentView(R.layout.activity_login);
		requestPermissions();


		if(ObjectService.personalInfo==null){
			ObjectService.personalInfo = new PersonalInfo();
		}

		MyTools.getAllFileName(Config.usericonpath,user_icon_path,user_icon_name);
		initView();
		initVideoView();

		if(Config.isExitSoft){
			Intent intent = new Intent(this, NettyService.class);
			startService(intent);
		}


	}

	private void initView() {
		user_image_login = findViewById(R.id.user_image_login);
		progressLay=(RelativeLayout)findViewById(R.id.login_progress_lay);
		mVideoView=(VideoView)findViewById(R.id.video_login_view);
		userAccount=(EditText)findViewById(R.id.user_account);
		userPassword=(EditText)findViewById(R.id.user_passsword);
		pref= PreferenceManager.getDefaultSharedPreferences(this);
		//remberBox=(CheckBox)findViewById(R.id.remmber_passward);

		forgetPassword = findViewById(R.id.forgetPassword);
		forgetPassword.setOnClickListener(this);

		signUser=(TextView)findViewById(R.id.sign_in_new_user);
		signUser.setOnClickListener(this);
		boolean isRemember=pref.getBoolean("remember_password",false);
		if (isRemember){
			String account=pref.getString("account","");
			String password=pref.getString("password","");
			userAccount.setText(account);
			userPassword.setText(password);

			setUserImage(account);

//			remberBox.setChecked(true);
		}

		finish_now=(ImageView)findViewById(R.id.finish_now);
		failLogin=(TextView)findViewById(R.id.fail_login);
		mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
//		progress = findViewById(R.id.layout_progress);
		//mInputLayout = findViewById(R.id.input_layout);
//		mName = (LinearLayout) findViewById(R.id.input_layout_name);
//		mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);

		mBtnLogin.setOnClickListener(this);
		failLogin.setOnClickListener(this);
		finish_now.setOnClickListener(this);

		userAccount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String account = userAccount.getText().toString();
				setUserImage(account);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}


	private void initVideoView() {
		//设置屏幕常亮
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.mqr));
		//设置相关的监听
		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnCompletionListener(this);
	}

	private void setUserImage(String account){
		String path = "";
		boolean flag = false;
		for(int i=0;i<user_icon_name.size();++i){
			String p = user_icon_name.get(i);
			int index = p.lastIndexOf(".");
			String acount2 = p.substring(0,index);
			if(acount2.equals(account)){
				flag = true;
				path = user_icon_path.get(i);
				ObjectService.personalInfo.setPhonenumber(account);
				ObjectService.personalInfo.setIcon(path);
			}
		}

		if(flag){
			FileInputStream fis = null;
				try {
					fis = new FileInputStream(path);
					Bitmap bitmap  = BitmapFactory.decodeStream(fis);
					user_image_login.setImageBitmap(bitmap);
					ObjectService.personalIcon = bitmap;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
			}

		}else{
			user_image_login.setImageDrawable(getResources().getDrawable(R.drawable.user_image1));
			ObjectService.personalInfo.setPhonenumber(account);
			ObjectService.personalInfo.setIcon("");
			ObjectService.personalIcon = null;
		}
	}

	private void setUserImage2(String account){
		String path = "";
		boolean flag = false;
		for(int i=0;i<user_icon_name.size();++i){
			String p = user_icon_name.get(i);
			int index = p.lastIndexOf(".");
			String acount2 = p.substring(0,index);
			if(acount2.equals(account)){
				flag = true;
				path = user_icon_path.get(i);
				ObjectService.personalInfo.setIcon(path);
			}
		}

		if(flag){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(path);
				Bitmap bitmap  = BitmapFactory.decodeStream(fis);
				ObjectService.personalIcon = bitmap;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}else{
			ObjectService.personalInfo.setIcon("");
			ObjectService.personalIcon = null;
		}
	}

	@Override
	public void onClick(View v) {


		switch (v.getId()){
			case R.id.main_btn_login:
				if(!NetworkUtils.isConnected(LoginActivity.this)){
					Toast.makeText(LoginActivity.this, "当前网络不可用，请检查您的网络!", Toast.LENGTH_SHORT).show();
					return;
				}
				progressLay.setVisibility(View.VISIBLE);
				mBtnLogin.setVisibility(View.GONE);
//				mWidth = mBtnLogin.getMeasuredWidth();
//				mHeight = mBtnLogin.getMeasuredHeight();
				//mName.setVisibility(View.INVISIBLE);
				//mPsw.setVisibility(View.INVISIBLE);
				//nputAnimator(mInputLayout, mWidth, mHeight);
//				progress.setVisibility(View.VISIBLE);
				// progressAnimator(progress);
//				 mBtnLogin.setVisibility(View.INVISIBLE);
//				 userAccount.setEnabled(false);
//				 userPassword.setEnabled(false);
			//progress.setVisibility(View.VISIBLE);
			//mInputLayout.setVisibility(View.VISIBLE);

				 account=userAccount.getText().toString().trim();
				 password=userPassword.getText().toString().trim();

				 if(account.isEmpty()){
					 Toast.makeText(LoginActivity.this, "请输入账号!", Toast.LENGTH_SHORT).show();
					 userAccount.setText("");
					 userAccount.requestFocus();
					 mBtnLogin.setVisibility(View.VISIBLE);
					 progressLay.setVisibility(View.GONE);
					 return;
				  }
				 if(password.isEmpty()){
					 Toast.makeText(LoginActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
					 userPassword.setText("");
					 userPassword.requestFocus();
					 mBtnLogin.setVisibility(View.VISIBLE);
					 progressLay.setVisibility(View.GONE);
					 return;
				 }

				 if(!MyTools.stringIsNumber(account)){
					 Toast.makeText(LoginActivity.this, "账号格式不正确,请重新输入!", Toast.LENGTH_SHORT).show();
					 userAccount.setText("");
					 userAccount.requestFocus();
					 mBtnLogin.setVisibility(View.VISIBLE);
					 progressLay.setVisibility(View.GONE);
					 return;
				 }




				 		if(NettyClient.getInstance().isInitOK()&&!NettyClient.getInstance().getConnectStatus()){
							NettyClient.getInstance().connect();
						}else{
							ObjectService.personalInfo.setPhonenumber(account);
							SendToServer.login(account,password,"loginUI");
						}



				timer = new Timer();
				timer.schedule(new TimerTask(){

					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
								mBtnLogin.setVisibility(View.VISIBLE);
								progressLay.setVisibility(View.GONE);

							}
						});
					}
				}, 12000);

				break;
			case R.id.fail_login:
				break;
			case R.id.finish_now:
				finish();
				break;
			case R.id.sign_in_new_user:
				Intent intent=new Intent(LoginActivity.this, RegisterSchoolInfoActivity.class);
				startActivity(intent);
				break;

			case R.id.forgetPassword:           //忘记密码
				Intent intent2=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
				startActivity(intent2);
				break;

				default:
		}

	}

	Timer timer = new Timer();

	BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MYACTION.equals(intent.getAction())){
				String rs=intent.getStringExtra("loginRs");
				timer.cancel();
 				if (rs.equals("ok")){

					editor=pref.edit();
					editor.putBoolean("remember_password",true);
					editor.putBoolean("keep_login",true);
					editor.putString("account",account);
					editor.putString("password",password);
					editor.apply();
					ObjectService.personalInfo.setPhonenumber(account);
					ObjectService.personalInfo.setPassword(password);

					Intent intent2=new Intent(LoginActivity.this,MainActivity.class);
					intent2.putExtra("isneedlogin","no");
					startActivity(intent2);

					finish();

				}else if(rs.equals("erruname")){
					Toast.makeText(LoginActivity.this,"该账号不存在!",Toast.LENGTH_SHORT).show();
					mBtnLogin.setVisibility(View.VISIBLE);
					progressLay.setVisibility(View.GONE);
 				}else if(rs.equals("errpwd")){
					Toast.makeText(LoginActivity.this,"密码错误!",Toast.LENGTH_SHORT).show();
					mBtnLogin.setVisibility(View.VISIBLE);
					progressLay.setVisibility(View.GONE);
 				}else {
					Toast.makeText(LoginActivity.this,"服务器繁忙,请稍后再试!",Toast.LENGTH_SHORT).show();
					mBtnLogin.setVisibility(View.VISIBLE);
					progressLay.setVisibility(View.GONE);
				}


			}
		}
	};


	private void savePerIcon(Bitmap mBitmap) {

		String path = Config.usericonpath +"/"+ ObjectService.personalInfo.getPhonenumber()+".jpg";// sd路径
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		FileOutputStream b = null;
//        File file = new File(path);
//        file.mkdirs();// 创建文件夹
		String fileName = path;// 图片名字
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

			ObjectService.personalInfo.setIcon(path);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		SharedPreferences sh = getSharedPreferences( Config.sharedPreferences_personal_info,Context.MODE_PRIVATE);

		Gson gson = new Gson();
		String json = gson.toJson(ObjectService.personalInfo);
		SharedPreferences.Editor editor = sh.edit() ;
		editor.putString(Config.sharedPreferences_personal_info, json) ; //存入json串
		editor.commit() ; //提交


	}









	//播放准备
	@Override
	public void onPrepared(MediaPlayer mp) {
		//开始播放
		mVideoView.start();
	//	mHandler.postDelayed(runnable, TIME);
	}

	//播放结束
	@Override
	public void onCompletion(MediaPlayer mp) {
		//开始播放
		mVideoView.start();
	}


	protected void onResume(){
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MYACTION);
		registerReceiver(broadcastReceiver, filter);
	}

	protected void onDestroy(){
		super.onDestroy();

		unregisterReceiver(broadcastReceiver);


	}






	public void requestPermissions(){
		List<String> permissionList=new ArrayList<>();
		if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

		}
		if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.RECORD_AUDIO);
		}
		if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.CAMERA);

		}
		if (!permissionList.isEmpty()){
			String[] permissions=permissionList.toArray(new String[permissionList.size()]);
			ActivityCompat.requestPermissions(LoginActivity.this,permissions,1);
		}

	}

	public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
		switch (requestCode){
			case 1:
				if (grantResults.length>0){
					for (int result:grantResults){
						if (result!=PackageManager.PERMISSION_GRANTED){
							Toast.makeText(LoginActivity.this,"拒绝权限无法使用",Toast.LENGTH_SHORT).show();
							finish();
							stopService(new Intent(this, NettyService.class));

						}else {
							int i=0;
						}

					}


				}else {
					Toast.makeText(LoginActivity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
					finish();
					stopService(new Intent(this, NettyService.class));

				}
		}
	}




}
