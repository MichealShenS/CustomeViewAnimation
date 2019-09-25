package com.ssq.androidanimation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

/**
 * Author : Mr.Shen
 * Date : 2019/9/25 17:30
 * Description : 自定义估值器
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        //始末值强转为Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        //通过fraction计算当前动画的坐标值x,y (fraction：表示动画完成度，据此计算当前动画的值)
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        //返回以上述x,y组装的新的Point对象
        Point point = new Point(x, y);
        return point;
    }

//    // 创建初始动画的对象  & 结束动画的对象
//    Point point1 = new Point(0, 0);
//    Point point2 = new Point(500, 500);
//
//    // 创建动画对象 & 设置参数
//    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), point1, point2);
//    anim.setDuration(3000);
//    anim.start();
}
