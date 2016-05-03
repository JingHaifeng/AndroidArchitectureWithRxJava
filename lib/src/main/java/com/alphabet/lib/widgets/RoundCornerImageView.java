package com.alphabet.lib.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alphabet.lib.R;


/**
 * @author: Alex Jing(alexjing2014@gmail.com)
 * @date: 2015-09-25
 * @time: 14:48
 */
public class RoundCornerImageView extends ImageView {

    private int mRadius = 0;
    private Paint mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint mZonePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private RectF mRectF = new RectF();
    private int mBackgroundColor;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundCornerImageView_roundCornerRadius, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.RoundCornerImageView_roundCornerBackgroundColor, Color.TRANSPARENT);
        typedArray.recycle();
        mZonePaint.setColor(mBackgroundColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRectF.set(0, 0, right - left, bottom - top);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(mRectF, mZonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mZonePaint);
        canvas.saveLayer(mRectF, mMaskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }
}
