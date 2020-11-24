package com.example.finalproject.style;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;

import com.example.finalproject.R;


public class CircleProgressBar extends View {
    // 设置属性的默认值
    private int mInnerBackground = Color.parseColor("#FF8247");
    private int mOuterBackground = Color.parseColor("#8A2BE2");
    private int mRoundWidth = 10;
    private float mProgressTextSize = 30;
    private int mProgressTextColor = Color.parseColor("#8A2BE2");
    private Paint mInnerPaint, mOuterPaint, mTextPaint;
    private int mMax = 100;
    private int mProgress = 0;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取attr.xml文件中的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        // 注意，获取innerBackground属性不是R.styleable.innerBackground，而是R.styleable.CicleProgressBar_innerBackground
        mInnerBackground = array.getColor(R.styleable.CircleProgressBar_innerBackground, mInnerBackground);
        mOuterBackground = array.getColor(R.styleable.CircleProgressBar_outerBackground, mOuterBackground);
        mRoundWidth = (int) array.getDimension(R.styleable.CircleProgressBar_roundWidth, dip2px(10));
        mProgressTextSize = array.getDimensionPixelSize(R.styleable.CircleProgressBar_progressTextSize,
                sp2px(mProgressTextSize));
        mProgressTextColor = array.getColor(R.styleable.CircleProgressBar_progressTextColor, mProgressTextColor);

        array.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackground);
        mInnerPaint.setStrokeWidth(mRoundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackground);
        mOuterPaint.setStrokeWidth(mRoundWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);

    }
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 先画内圆
        int width = getWidth()/2;
        int radius = width;
        canvas.drawCircle(radius,radius,radius-mRoundWidth/2,mInnerPaint);
        //画圆弧
        @SuppressLint("DrawAllocation")
        RectF rectF=new RectF(mRoundWidth/2,mRoundWidth/2, getWidth()-mRoundWidth/2,getHeight()-mRoundWidth/2);
        //如果进度为0就不绘制
        if (mProgress == 0) {
            return;
        }
        float percent=(float)mProgress/mMax;
        canvas.drawArc(rectF,270,360*percent,false,mOuterPaint);

        // 画进度文字
        String text = ((int) (percent * 100)) + "%";
        @SuppressLint("DrawAllocation")
        Rect rect=new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),rect);
        float dx=getWidth()/2-rect.width()/2;
        @SuppressLint("DrawAllocation")
        Paint.FontMetricsInt fontMetricsInt=new Paint.FontMetricsInt();
        int dy=(fontMetricsInt.bottom - fontMetricsInt.top)/2-fontMetricsInt.bottom;
        float baseLine=getHeight()/2+dy;
        canvas.drawText(text,dx,baseLine,mTextPaint);

    }
    public synchronized void setMax(int max) {
        if (max < 0) {

        }
        this.mMax = max;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
        }
        this.mProgress = progress;
        // 刷新 invalidate
        invalidate();
    }
}

