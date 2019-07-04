package thefirstchange.example.com.communicationtext.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import thefirstchange.example.com.communicationtext.R;

/*
    各种dialog
 */
public class MyDialog {

    //正在加载的dialog
    private static Dialog BottomLoadingDialog;
    //正在加载
    public static void showBottomLoadingDialog(Context context) {
        if(BottomLoadingDialog!=null&&BottomLoadingDialog.isShowing()){
            BottomLoadingDialog.dismiss();
        }
        BottomLoadingDialog = new Dialog(context,R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.sr_loading_dialog, null);
//        dialog = new Dialog(context, R.style.BottomDialog);
        BottomLoadingDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
//        params.width = 300;
//        params.height= 400;
        params.bottomMargin = DensityUtiltwo.dp2px(context, 8f);
        contentView.setLayoutParams(params);
        BottomLoadingDialog.setCanceledOnTouchOutside(false);
        BottomLoadingDialog.getWindow().setGravity(Gravity.CENTER);
        BottomLoadingDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        BottomLoadingDialog.show();
        ProgressBar progressBar = (ProgressBar)contentView.findViewById(R.id.sr_loading_loading);
        TextView sr_loading_tv = (TextView)contentView.findViewById(R.id.sr_loading_tv) ;

    }

    public static void dismissBottomLoadingDialog(){
        if(BottomLoadingDialog!=null&&BottomLoadingDialog.isShowing()){
            BottomLoadingDialog.dismiss();
        }
    }

}
