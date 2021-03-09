package hznu.linxin.cniaoshop_04.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;

import hznu.linxin.cniaoshop_04.R;


/**
 * @author: BacSon
 * data: 2021/3/5
 */
/**
 *  自定义ToolBar： 通过重写ToolBar构造方法
 */
public class CnToolbar extends Toolbar {

    private LayoutInflater mInflater;
    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private ImageButton mRightImageButton;

    public CnToolbar(Context context) {
        this(context, null);
    }

    public CnToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public CnToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        setContentInsetsRelative(10, 10);


        if (attrs != null) {
            // 定义自定义属性
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CnToolbar, defStyleAttr, 0);


            final Drawable rightIcon = a.getDrawable(R.styleable.CnToolbar_rightButtonIcon);
            // 根据属性是否有赋值来判断是否需要设置右边的按钮
            if (rightIcon != null) {
                //setNavigationIcon(navIcon);
                // 设置右边的按钮图标
                setRightButtonIcon(rightIcon);
            }

            // 根据属性是否为true来判断是否需要设置搜索框
            boolean isShowSearchView = a.getBoolean(R.styleable.CnToolbar_isShowSearchView, false);
            if (isShowSearchView) {
                // 显示搜索框和隐藏标题
                showSearchView();
                hideTitleView();

            }
            a.recycle();
        }
    }

    private void initView() {
        if(mView == null) {
            // 找到toolbar的布局文件
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);

            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightImageButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);
            // 定义Layout的布局 三个参数：宽、高、对齐方式
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            // 把自定义控件放入toolbar中
            addView(mView, lp);
        }

    }

    // 重写ToolBar的setTitle方法使得标题能够居中
    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            // 将隐藏的标题显示出来
            showTitleView();
        }
    }

    // 显示搜索框
    public  void showSearchView(){
        if(mSearchView !=null)
            mSearchView.setVisibility(VISIBLE);

    }
    // 隐藏搜索框
    public void hideSearchView(){
        if(mSearchView !=null)
            mSearchView.setVisibility(GONE);
    }
    // 显示标题
    public void showTitleView(){
        if(mTextTitle !=null)
            mTextTitle.setVisibility(VISIBLE);
    }

    // 隐藏标题
    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    // 设置右边按钮图标
    public void  setRightButtonIcon(Drawable icon){

        if(mRightImageButton !=null){
            mRightImageButton.setImageDrawable(icon);
            mRightImageButton.setVisibility(VISIBLE);
        }

    }

    // 右边按钮点击事件
    public  void setRightButtonOnClickListener(OnClickListener li){

        mRightImageButton.setOnClickListener(li);
    }
}
