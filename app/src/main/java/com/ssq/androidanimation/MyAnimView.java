package com.ssq.androidanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

/**
 * Author : Mr.Shen
 * Date : 2019/9/25 17:39
 * Description : 自定义view
 */
public class MyAnimView extends View {


    public static final float RADIUS = 50f;//常亮
    private Point curPoint;//当前Point，记录当前动画的值（x,y坐标）
    private Paint mPaint;//画笔

    //将颜色设置为String类型，使用#RRGGBB格式表示颜色
    private String color;

    //get()方法
    public String getColor() {
        return color;
    }

    //set()方法
    public void setColor(String color) {
        mPaint.setColor(Color.parseColor(color));
        this.color = color;
        //改变画笔颜色后立即刷新视图，然后onDraw()方法就会调用
        invalidate();
    }

    //Java代码实例化View时调用
    public MyAnimView(Context context) {
        super(context);
    }

    //XML文件实例时调用
    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (curPoint == null) {//第一次绘制时
            //初始化坐标为(50f, 50f)
            curPoint = new Point(RADIUS, RADIUS);
            //画圆
            drawCircle(canvas);
            //开始动画
            startAnimation();
        } else {//非第一次绘制
            drawCircle(canvas);
        }
    }

    //在当前坐标处绘制一个半径为50f的圆
    private void drawCircle(Canvas canvas) {
        float x = curPoint.getX();
        float y = curPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        //设置 起始值&结束值
        Point startPoint = new Point(RADIUS, RADIUS);//起始为左上角(50f,50f)
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);//终点为右下角(屏幕宽度-50f,屏幕高度-50f)
        //设置插值器
        final ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.setInterpolator(new BounceInterpolator());
        //设置监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //每当Point的值有改变的时候，都会调用onAnimationUpdate()方法
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //更新curPoint，即更新当前坐标
                curPoint = (Point) animation.getAnimatedValue();
                // 刷新，重现调用onDraw()方法
                // 由于curPoint的值改变，那么绘制的位置也会改变，也就实现了一个从左上到右下的平移动画
                invalidate();
            }
        });
//        anim.setDuration(5000);
//        anim.start();
        //颜色过渡的代码逻辑放在startAnimation()方法中，本身就在MyAnimView中执行，因此传入 this 参数即可
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#FF0000", "#0000FF");
        //创建一个AnimatorSet，让两个动画同时播放，时长5s
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim).with(anim2);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}
