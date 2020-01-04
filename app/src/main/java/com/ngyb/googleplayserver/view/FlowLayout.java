package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 22:47
 */
public class FlowLayout extends ViewGroup {
    private List<Line> lines = new ArrayList<>();
    private int horizontalSpace = 10;
    private int verticalSpace = 10;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int offsetTop = getPaddingTop();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.layout(paddingLeft, offsetTop);
            offsetTop += line.height + verticalSpace;
        }
    }

    public void setSpace(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        Line currentLine = null;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int maxLineWidth = width - getPaddingLeft() - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == GONE) {
                continue;
            }
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if (currentLine == null) {
                currentLine = new Line(maxLineWidth, horizontalSpace);
                lines.add(currentLine);
                currentLine.addView(view);
            } else {
                boolean canAdd = currentLine.canAdd(view);
                if (canAdd) {
                    currentLine.addView(view);
                } else {
                    currentLine = new Line(maxLineWidth, horizontalSpace);
                    lines.add(currentLine);
                    currentLine.addView(view);
                }
            }
        }
        float allHeight = 0;
        for (int i = 0; i < lines.size(); i++) {
            allHeight += lines.get(i).height;
            if (i != 0) {
                allHeight += verticalSpace;
            }
        }
        int measuredHeight = (int) (allHeight + getPaddingTop() + getPaddingBottom() + 0.5f);
        setMeasuredDimension(width, measuredHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    class Line {
        private List<View> viewsLine = new ArrayList<>();
        private float maxWidth;
        private float usedWidth;
        private float height;
        private float marginLeft;
        private float marginRight;
        private float marginTop;
        private float marginBottom;
        private float horizontalSpace;

        public Line(float maxWidth, float horizontalSpace) {
            this.maxWidth = maxWidth;
            this.horizontalSpace = horizontalSpace;
        }

        public void addView(View view) {
            int size = viewsLine.size();
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            if (size == 0) {
                if (viewWidth > maxWidth) {
                    usedWidth = maxWidth;
                } else {
                    usedWidth = viewWidth;
                }
                height = viewHeight;
            } else {
                usedWidth += viewWidth + horizontalSpace;
                height = height < viewHeight ? viewHeight : height;
            }
            viewsLine.add(view);
        }

        public void layout(int offsetLeft, int offsetTop) {
            int currentLeft = offsetLeft;
            int size = viewsLine.size();
            float extra = 0;
            float widthAvg = 0;
            if (usedWidth < maxWidth) {
                extra = maxWidth - usedWidth;
                widthAvg = extra / size;
            }
            for (int i = 0; i < size; i++) {
                View view = viewsLine.get(i);
                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                if (widthAvg != 0) {
                    int newWidth = (int) (width + widthAvg + 0.5f);
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
                    int heightMeaseSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                    view.measure(widthMeasureSpec, heightMeaseSpec);
                    width = view.getMeasuredWidth();
                    height = view.getMeasuredHeight();
                }
                int left = currentLeft;
                int top = (int) (offsetTop + (this.height - height) / 2 + 0.5f);
                int right = left + width;
                int bottom = top + height;
                view.layout(left, top, right, bottom);
                currentLeft += width + horizontalSpace;
            }
        }

        public boolean canAdd(View view) {
            int size = viewsLine.size();
            if (size == 0) {
                return true;
            }
            int viewWidth = view.getMeasuredWidth();
            float planWidth = usedWidth + horizontalSpace + viewWidth;
            return planWidth <= maxWidth;
        }
    }
}


