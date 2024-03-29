package thefirstchange.example.com.communicationtext.nice9layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import thefirstchange.example.com.communicationtext.R;


/**
 * Created by wxy on 2017/5/25.
 * 模仿nice首页列表 9种样式图片
 * 依赖淘宝vLayout开源控件 实现
 * 1
 * -------------------------
 * |                       |
 * |                       |
 * |           1           |
 * |                       |
 * |                       |
 * |                       |
 * -------------------------
 * <p>
 * 2
 * * -------------------------
 * |           |           |
 * |           |           |
 * |           |           |
 * |     1     |     2     |
 * |           |           |
 * |           |           |
 * |           |           |
 * -------------------------
 * 3
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * 4
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * 5
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * 6
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * |      |        |       |
 * |   4  |   5    |    6  |
 * |      |        |       |
 * -------------------------
 * <p>
 * 7
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   5  |     6  |    7  |
 * |      |        |       |
 * -------------------------
 * 8
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   6  |     7  |    8  |
 * |      |        |       |
 * -------------------------
 * 9
 * |-----------------------|
 * |      |        |       |
 * |   1  |     2  |    3  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   4  |     5  |    6  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   7  |     8  |    9  |
 * |      |        |       |
 * -------------------------
 */

public class ImageNice9Layout extends LinearLayout implements MyItemTouchCallback.OnDragListener {
    private RecyclerView mRecycler;
    private VirtualLayoutManager layoutManager;
    private List<LayoutHelper> helpers;
    private ItemTouchHelper itemTouchHelper;
    private GridLayoutHelper gridLayoutHelper;
    private OnePlusNLayoutHelper onePlusHelper;
    private ImageMulitVAdapter mMulitVAdapter;
    private boolean canDrag = false;
    private Context mContext;
    private boolean isShowTip = false;
    private int itemMargin = 10;

    public ImageNice9Layout(Context context) {
        this(context, null);
    }

    public ImageNice9Layout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageNice9Layout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageNice9Layout);
        final int N = typedArray.getIndexCount();//取得本集合里面总共有多少个属性
        for (int i = 0; i < N; i++) {//遍历这些属性，拿到对应的属性
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
        mMulitVAdapter = new ImageMulitVAdapter(layoutManager, mContext, canDrag, itemMargin);
    }

    private void initAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.ImageNice9Layout_nice9_candrag) {
            canDrag = typedArray.getBoolean(attr, false);
        }
        if (attr == R.styleable.ImageNice9Layout_nice9_itemMargin) {
            itemMargin = (int) typedArray.getDimension(attr, 5);
        }
        if (attr == R.styleable.ImageNice9Layout_nice9_tipText) {
           // setTipText(typedArray.getString(attr));
        }
        if (attr == R.styleable.ImageNice9Layout_nice9_tipColor) {
            //setTipColor(typedArray.getColor(attr, Color.parseColor("#ffffff")));
        }
        if (attr == R.styleable.ImageNice9Layout_nice9_tipBgColor) {
            //setTipBgColor(typedArray.getColor(attr, Color.parseColor("#40000000")));
        }
        if (attr == R.styleable.ImageNice9Layout_nice9_tipBgDrawable) {
            //setTipBgDrawable(typedArray.getDrawable(attr));
        }
    }

    /**
     * 提示文字背景
     **/
//    public void setTipBgDrawable(Drawable tipBgDrawable) {
//        mTip.setBackground(tipBgDrawable);
//    }

    /**
     * 提示文字颜色
     **/
//    public void setTipColor(int tipBgColor) {
//        mTip.setTextColor(tipBgColor);
//    }

    /**
     * 提示背景颜色
     **/
//    public void setTipBgColor(int tipBgColor) {
//        mTip.setBackgroundColor(tipBgColor);
//    }

    /**
     * 提示文字
     **/
//    public void setTipText(String string) {
//        mTip.setText(string);
//    }

//    public void setTipText(@StringRes int string) {
//        setTipText(getResources().getString(string));
//    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_imagemulit_layout, this);
        mRecycler = (RecyclerView) view.findViewById(R.id.drag_recycler);
       // mTip = (TextView) view.findViewById(R.id.drag_tip);
        layoutManager = new VirtualLayoutManager(mContext);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setNestedScrollingEnabled(false);
    }

    public void setItemDelegate(ItemDelegate itemDelegate) {
        mMulitVAdapter.setItemDelegate(itemDelegate);
    }

    /**
     * 设置是否可以拖拽
     **/
    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    //绑定数据，根据数据，先行计算recyclerview高度，固定高度，防止多重滑动时候冲突
    public void bindData(Vector<String> pictures) {
        if (pictures != null) {
            helpers = new LinkedList<>();
            gridLayoutHelper = new GridLayoutHelper(6);
            gridLayoutHelper.setGap(itemMargin);
            gridLayoutHelper.setHGap(itemMargin);
            onePlusHelper = new OnePlusNLayoutHelper(3);
           // mTip.setVisibility(canDrag ? VISIBLE : INVISIBLE);
            final int num = pictures.size();
            ViewGroup.LayoutParams layoutParams = mRecycler.getLayoutParams();
            int displayW = DisplayUtils.getDisplayWidth(mContext);
            layoutParams.width = displayW;
            int height;
            if (num == 1) {
                height = layoutParams.width;
            } else if (num == 2) {
                height = (int) (displayW * 0.5);
            } else if (num == 3) {
                height = (int) (displayW * 0.66) - itemMargin - itemMargin / 2;
            } else if (num == 4) {
                height = (int) (displayW * 0.5) + itemMargin + (int) (displayW * 0.33);
            } else if (num == 5) {
                height = (int) (displayW * 0.5) + itemMargin + (int) (displayW * 0.33);
            } else if (num == 6) {
                height = (int) (displayW * 0.66) + (int) (displayW * 0.33) - itemMargin / 2;
            } else if (num == 7 || num == 8) {
                height = (int) (displayW * 0.5) + 2 * itemMargin + (int) (displayW * 0.33) * 2;
            } else {
                height = (int) ((displayW * 0.33) * 3 + 3 * itemMargin - itemMargin / 2);
            }




//            final int urlListSize = pictures != null ? pictures.size() : 0;
//            int column = 3;
//            if (pictures.size() == 1) {
//                column = 1;
//            } else if (pictures.size() == 4) {
//                column = 2;
//            }
//            int row = 0;
//            if (pictures.size() > 6) {
//                row = 3;
//            } else if (pictures.size() > 3) {
//                row = 2;
//            } else if (pictures.size() > 0) {
//                row = 1;
//            }
//            DisplayMetrics mDisplayMetrics = getContext().getResources().getDisplayMetrics();
//            int  mSingleMaxSize = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 216, mDisplayMetrics) + 0.5f);
//            int  mSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mDisplayMetrics) + 0.5f);
//            final int imageSize = urlListSize == 1 ? mSingleMaxSize :
//                    (int) ((getWidth() * 1f - mSpace * (column - 1)) / column);
//            layoutParams.width=imageSize;
//            layoutParams.height=layoutParams.width;





            layoutParams.height = height;
            mRecycler.setLayoutParams(layoutParams);
            //根据数量和位置 设置span占比
            gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (num == 1) {
                        return 6;
                    } else if (num == 2) {
                        return 3;
                    } else if (num == 3) {
                        return 2;
                    } else if (num == 4) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 5) {
                        if (position == 0 || position == 1) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else if (num == 6) {
                        return 2;
                    } else if (num == 7) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 8) {
                        if (position == 0 || position == 1) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else {
                        return 2;
                    }
                }
//                public int getSpanSize(int position) {
//                    if (num == 1) {
//                        return 6;
//                    } else if (num == 2) {
//                        return 3;
//                    } else if (num == 3) {
//                        return 2;
//                    } else if (num == 4) {
//                        if (position == 0) {
//                            return 6;
//                        } else {
//                            return 2;
//                        }
//                    } else if (num == 5) {
//                        if (position == 0 || position == 1) {
//                            return 3;
//                        } else {
//                            return 2;
//                        }
//                    } else if (num == 6) {
//                        return 2;
//                    } else if (num == 7) {
//                        if (position == 0) {
//                            return 6;
//                        } else {
//                            return 2;
//                        }
//                    } else if (num == 8) {
//                        if (position == 0 || position == 1) {
//                            return 3;
//                        } else {
//                            return 2;
//                        }
//                    } else {
//                        return 2;
//                    }
//                }
            });
            helpers.clear();
            if (num == 3) {
                helpers.add(onePlusHelper);
                gridLayoutHelper.setItemCount(0);
            } else {
                if (num == 6) {
                    helpers.add(onePlusHelper);
                    gridLayoutHelper.setItemCount(3);
                } else {
                    gridLayoutHelper.setItemCount(num);
                }
                helpers.add(gridLayoutHelper);
            }
            layoutManager.setLayoutHelpers(helpers);
            mMulitVAdapter.bindData(pictures);
            mRecycler.setAdapter(mMulitVAdapter);
            if (canDrag) {
                if (!isShowTip && pictures.size() > 1) {
                    mRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          //  mTip.setVisibility(View.INVISIBLE);
                        }
                    }, 500);
                    isShowTip = true;
                }
                itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mMulitVAdapter).setOnDragListener(this));
                itemTouchHelper.attachToRecyclerView(mRecycler);
                mRecycler.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecycler) {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder vh) {
                    }

                    @Override
                    public void onLongClick(RecyclerView.ViewHolder vh) {
                        itemTouchHelper.startDrag(vh);
                    }
                });
            }
        }

    }

    /**
     * 获取更改后的图片列表
     **/
    public List<String> getAfterPicList() {
        return mMulitVAdapter.getPictures();
    }

    @Override
    public void onFinishDrag() {
        mMulitVAdapter.notifyDataSetChanged();
    }


    public interface ItemDelegate {
        void onItemClick(int position);
    }

}
