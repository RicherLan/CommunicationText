package thefirstchange.example.com.communicationtext.advice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.activity.PlusImageActivity;
import thefirstchange.example.com.communicationtext.config.MainConstant;
import thefirstchange.example.com.communicationtext.config.PictureSelectorConfig;

/**
 * 仿微信朋友圈添加图片的效果
 */
public class AdviceActivity extends BaseForCloseActivity {
    private ImageView finishSelf;
    private Button submitAdvice;



    private Context mContext;
    private GridView gridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.statustitle),false);

        mContext = this;
        gridView = findViewById(R.id.gridView);
        System.out.println(123456);
        initGridView();
        finishSelf=(ImageView)findViewById(R.id.finish_advice);
        finishSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submitAdvice=(Button)findViewById(R.id.submit_advice);
        submitAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdviceActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过4张，才能点击
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }



    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多4张
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
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
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
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }
}
