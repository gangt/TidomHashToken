package com.yunqukuailian.app.view.kview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangliang on 16/11/5.
 * QQ:1179980507
 */
public class KSimpleView extends GridChartKSimpleView {


    /**
     * 显示的OHLC数据起始位置
     */
    private int mDataStartIndext = 0;

    /**
     * 显示的OHLC数据个数
     */
    private int mShowDataNum;


    /**
     * 当前数据的最大最小值
     */
    private double mMaxPrice;
    private double mMinPrice;
    /**
     * 成交量最大值
     */
    private double mMaxVol;
    /**
     * 成交量最大值
     */
    private double mMinVol;

    //记录最小值
    private int minIndex;
    //记录最大值
    private int maxIndex;


    private boolean mShowMACD = false;
    private boolean mShowJKD = false;

    private boolean isTimeType = false;
    /**
     * 显示纬线数
     */
    private int latitudeNum = super.DEFAULT_UPER_LATITUDE_NUM;

    /**
     * 显示经线数
     */
    private int longtitudeNum = super.DEFAULT_LOGITUDE_NUM;

    /**
     * 显示的最小Candle数
     */
    private final static int MIN_CANDLE_NUM = 10;
    /**
     * 显示的最大Candle数
     */
    private final static int MAX_CANDLE_NUM = 480;

    /**
     * 默认显示的Candle数
     */
    private final static int DEFAULT_CANDLE_NUM = 80;

    /**
     * Candle宽度
     */
    private double mCandleWidth;
    /**
     * MA数据
     */
    private List<MALineEntity> MALineData;
    /**
     * OHLC数据
     */
    private List<MarketChartData> mOHLCData = new ArrayList<MarketChartData>();
    boolean isOpenAnimator = false;

    public void setOpenAnimator(boolean open) {
        isOpenAnimator = open;
    }

    /**
     * 默认五日均线颜色
     **/
    public static int kline5dayline = 0x535d66;

    public static int klineTimeline = 0x535d66;

    private boolean mHave5Line = true;

    /**
     * 默认十日均线颜色
     **/
    public static int kline10dayline = 0x535d66;
    private boolean mHave10Line = true;

    private float lineWidth = 1f;


    /**
     * 默认30日均线颜色
     **/
    public static int kline30dayline = 0x535d66;
    private boolean mHave30Line = true;


    public static int klineRed = 0xCD1A1E;
    public static int klineGreen = 0x7AA376;

    private boolean isUpFill = true;
    private boolean isDownFill = true;
    private int mUpColor = DEFAULT_RED;
    private int mDownColor = DEFAULT_GREEN;

    /**
     * 量能均线数组
     */
    private List<MALineEntity> MAVLineData;

    private Context mConext;
    //private MACDEntity mMACDData;
    private MACD mMACDData;
    private KDJ mKDJData;
    private Resources res;

    private boolean isArrow = false;

    public int mBubbleColor = DEFAULT_AXIS_TITLE_COLOR;


    private VelocityTracker mVelocityTracker;

    public KSimpleView(Context context) {
        super(context);
        mConext = context;
        res = mConext.getResources();
        init();
    }

    public KSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mConext = context;
        res = mConext.getResources();
        init();

    }

    public KSimpleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mConext = context;
        res = mConext.getResources();
        init();
    }


    private void init() {
        mShowDataNum = DEFAULT_CANDLE_NUM;
        mDataStartIndext = 0;
        mMaxPrice = 0;
        mMinPrice = 0;
        mShowMACD = true;
        mShowJKD = false;
        klineTimeline = res.getColor(R.color.kline5dayline);
        kline5dayline = res.getColor(R.color.kline5dayline);
        kline10dayline = res.getColor(R.color.kline10dayline);
        kline30dayline = res.getColor(R.color.kline30dayline);
        klineRed = res.getColor(R.color.klinered);
        klineGreen = res.getColor(R.color.klinegreen);

        mUpColor = klineRed;
        mDownColor = klineGreen;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        initShowDataNum();
        initAxisX();
        initAxisY();
        super.onDraw(canvas);
        if (isTimeType) {
            drawTime(canvas);
        } else {
            //上表灯笼
            drawUpperRegion(canvas);
            drawMA(canvas);
        }
    }

    private void setTimeMaxMinPrice() {

        if (mOHLCData == null || mOHLCData.size() <= 0 || mDataStartIndext < 0) {
            return;
        }
        mMinPrice = mOHLCData.get(mDataStartIndext).getHighPrice();
        mMaxPrice = mOHLCData.get(mDataStartIndext).getHighPrice();

        minIndex = mDataStartIndext;
        maxIndex = mDataStartIndext;

        for (int i = 0; i < mShowDataNum && mDataStartIndext + i < mOHLCData.size(); i++) {
            MarketChartData entity = mOHLCData.get(mDataStartIndext + i);

            if (mMinPrice > entity.getHighPrice()) {
                mMinPrice = entity.getHighPrice();
                minIndex = mDataStartIndext + i;
            }
            if (mMaxPrice < entity.getHighPrice()) {
                mMaxPrice = entity.getHighPrice();
                maxIndex = i + mDataStartIndext;
            }
        }
    }

    private void drawTime(Canvas canvas) {
        if (mOHLCData == null || mOHLCData.size() < 0)
            return;
        setTimeMaxMinPrice();
        float width = getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2;
        mCandleWidth = width / 10.0 * 10.0 / mShowDataNum;
        double rate = (getUperChartHeight()) / (mMaxPrice - mMinPrice);
        float startX = 0;
        float startY = 0;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(klineTimeline);
        paint.setTextSize(DEFAULT_AXIS_TITLE_SIZE);

        for (int i = 0; i < mShowDataNum
                && mDataStartIndext + i < mOHLCData.size(); i++) {
            if (i != 0) {

                canvas.drawLine(
                        startX,
                        startY > MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT ? MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT : startY,
                        (float) (super.getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2 - mCandleWidth * i - mCandleWidth * 0.5f),
                        (float) (((mMaxPrice - mOHLCData.get(mDataStartIndext + i).getHighPrice()) * rate) + Up_chart_margin + Up_title_height) > MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT ? MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT : (float) ((mMaxPrice - mOHLCData.get(mDataStartIndext + i).getHighPrice()) * rate) + Up_chart_margin + Up_title_height,
                        paint);


            }
            startX = (float) (super.getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2 - mCandleWidth * i - mCandleWidth * 0.5f);
            startY = (float) ((mMaxPrice - mOHLCData.get(mDataStartIndext + i).getHighPrice()) * rate) + Up_chart_margin + Up_title_height;

            Rect rect = new Rect();
            //画最大和最小值
            if (mDataStartIndext + i == maxIndex) {
                String price = mOHLCData.get(mDataStartIndext + i).getHighPrice() + "";
                paint.getTextBounds(price, 0, 1, rect);
                float w = paint.measureText(price);

                if ((startX - w) > 0) {
                    canvas.drawCircle(startX, startY, 10, paint);
                    canvas.drawText(mOHLCData.get(mDataStartIndext + i).getHighPrice() + "", startX - w, startY + 2 * DEFAULT_AXIS_TITLE_SIZE, paint);
                } else {
                    canvas.drawCircle(startX, startY, 10, paint);
                    canvas.drawText(mOHLCData.get(mDataStartIndext + i).getHighPrice() + "", startX + w, startY + 2 * DEFAULT_AXIS_TITLE_SIZE, paint);
                }
            }

            if (mDataStartIndext + i == minIndex) {
                String price = mOHLCData.get(mDataStartIndext + i).getHighPrice() + "";
                paint.getTextBounds(price, 0, 1, rect);
                float w = paint.measureText(price);
                if ((startX + w) > super.getWidth()) {
                    canvas.drawCircle(startX, startY, 10, paint);
                    canvas.drawText(mOHLCData.get(mDataStartIndext + i).getHighPrice() + "", startX - w, startY, paint);
                } else {
                    canvas.drawCircle(startX, startY, 10, paint);
                    canvas.drawText(mOHLCData.get(mDataStartIndext + i).getHighPrice() + "", startX, startY, paint);
                }

            }
        }

    }

    /**
     * ma5,ma10,ma30均线
     */

    public void drawMAText(List<MALineEntity> MALineData, Canvas canvas, int index, int k5, int k10, int k30) {
        String ma5text = "";
        String ma10text = "";
        String ma30text = "";
        if (mHave5Line) {
            ma5text = "MA5:" + new DecimalFormat("#.##").format(MALineData.get(0).getLineData().get(index));
        }
        if (mHave10Line) {
            ma10text = "MA10:" + new DecimalFormat("#.##").format(MALineData.get(1).getLineData().get(index));
        }
        if (mHave30Line) {
            ma30text = "MA30:" + new DecimalFormat("#.##").format(MALineData.get(2).getLineData().get(index));
        }

        Paint ma5 = new Paint();
        ma5.setColor(k5);
        ma5.setAntiAlias(true);
        ma5.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        canvas.drawText(ma5text, 2f, 2 * TITLE_HEIGHT - 2f, ma5);


        float tWidth = ma5.measureText(ma5text);
        Paint ma10 = new Paint();
        ma10.setColor(k10);
        ma10.setAntiAlias(true);
        ma10.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        canvas.drawText(ma10text, DisplayUtil.dip2px(getContext(), 10) + tWidth, 2 * TITLE_HEIGHT - 2f, ma10);


        float width = ma10.measureText(ma10text);
        Paint ma30 = new Paint();
        ma30.setColor(k30);
        ma30.setAntiAlias(true);
        ma30.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        canvas.drawText(ma30text, DisplayUtil.dip2px(getContext(), 20) + tWidth + width, 2 * TITLE_HEIGHT - 2f, ma30);
    }

    /**
     * ma5,ma10,ma30均线
     */

    public void drawVMAText(List<MALineEntity> VMALineData, Canvas canvas, int index, int k5, int k10) {
        String ma5text = "VMA5:" + new DecimalFormat("#.##").format(VMALineData.get(0).getLineData().get(index));
        String ma10text = "VMA10:" + new DecimalFormat("#.##").format(VMALineData.get(1).getLineData().get(index));
        Paint ma5 = new Paint();
        ma5.setColor(k5);
        ma5.setAntiAlias(true);
        ma5.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        canvas.drawText(ma5text, 2f, MIDDLE_CHART_TOP - 2f, ma5);


        float tWidth = ma5.measureText(ma5text);
        Paint ma10 = new Paint();
        ma10.setColor(k10);
        ma10.setAntiAlias(true);
        ma10.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        canvas.drawText(ma10text, DisplayUtil.dip2px(getContext(), 10) + tWidth, MIDDLE_CHART_TOP - 2f, ma10);

    }

    /*
     * 单点击事件
     */
    @Override
    protected void drawWithFingerClick(Canvas canvas) {
        if (mOHLCData == null || mOHLCData.size() <= 0) {
            return;
        }

        int index = getSelectedIndex();
        if (super.getTouchPoint() == null)
            return;

        float y = super.getTouchPoint().y;
        //纠正出界
        if (y < super.Up_title_height + Up_chart_margin) {
            y = super.Up_title_height + Up_chart_margin;
        }
        if (y > super.Up_title_height + Up_chart_margin + super.getUperChartHeight()) {
            y = super.Up_title_height + Up_chart_margin + super.getUperChartHeight();
        }
        if (super.getTouchPoint() != null && mOHLCData != null && mOHLCData.size() > 0) {
            setAxisXClickTitle(String.valueOf(mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getTime3()));

            float roate = 1 - (y - super.Up_title_height - Up_chart_margin) / super.getUperChartHeight();

            setAxisYClickTitle(roate * (mMaxPrice - mMinPrice) + mMinPrice + "");

            float startX = (float) (mCandleWidth * (index + 1) - mCandleWidth / 2);
            PointF piont = new PointF(startX, y);
            super.setTouchPoint(piont);
        }

        if (isShowTitle) {
            super.drawAlphaTopTextBox(res.getString(R.string.open) + deFormatNew(mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getOpenPrice() + "", 8) + res.getString(R.string.high) + deFormatNew(mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getHighPrice() + "", 8) +
                    res.getString(R.string.low) + deFormatNew(mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getLowPrice() + "", 8) + res.getString(R.string.close) + deFormatNew(mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getClosePrice() + "", 8) + res.getString(R.string.vol) + mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getVol(), canvas);
            drawMAText(MALineData, canvas, mShowDataNum - 1 - index + mDataStartIndext, kline5dayline, kline10dayline, kline30dayline);
            drawVMAText(MAVLineData, canvas, mShowDataNum - 1 - index + mDataStartIndext, kline5dayline, kline10dayline);
            //  drawAlphaMiddleTextBox(res.getString(R.string.vol) + mOHLCData.get(mShowDataNum - 1 - index + mDataStartIndext).getVol(), canvas);
            if (mMACDData != null && mShowMACD) {
                drawAlphaBottomTextBox("MACD(12,26,9)    DIF:" + new DecimalFormat("#.##").format(mMACDData.getDIF().get(mShowDataNum - 1 - index + mDataStartIndext)) + "    DEA:" + new DecimalFormat("#.##").format(mMACDData.getDEA().get(mShowDataNum - 1 - index + mDataStartIndext))
                        + "    MACD:" + new DecimalFormat("#.##").format(mMACDData.getBAR().get(mShowDataNum - 1 - index + mDataStartIndext)), canvas);
            }

            if (mKDJData != null && mShowJKD) {
                drawAlphaBottomTextBox("KDJ(9,3,3) K:" + new DecimalFormat("#.##").format(mKDJData.getK().get(mShowDataNum - 1 - index + mDataStartIndext)) + " D:" + new DecimalFormat("#.##").format(mKDJData.getD().get(mShowDataNum - 1 - index + mDataStartIndext)) + "J:" + new DecimalFormat("#.##").format(mKDJData.getJ().get(mShowDataNum - 1 - index + mDataStartIndext)), canvas);
            }
        }

        super.drawWithFingerClick(canvas);
    }

    public int getSelectedIndex() {
        if (null == super.getTouchPoint()) {
            return 0;
        }
        float graduate = Float.valueOf(super.getAxisXGraduate(super.getTouchPoint().x));
        int index = (int) Math.floor(graduate * mShowDataNum);

        if (index >= mShowDataNum) {
            index = mShowDataNum - 1;
        } else if (index < 0) {
            index = 0;
        }

        return index;
    }


    /**
     * 初始化X轴
     */
    protected void initAxisX() {
        if (mOHLCData == null || mOHLCData.size() <= 0) {
            return;
        }
        List<String> TitleX = new ArrayList<String>();

        if (null != mOHLCData) {
            int step = (int) Math.floor(mShowDataNum / longtitudeNum);

            for (int i = 0; i < longtitudeNum && mDataStartIndext + (i + 1) * step < mOHLCData.size(); i++) {
                String time = String.valueOf(mOHLCData.get((i + 1) * step + mDataStartIndext).getTime4());
                if (time.equals("00:00:00")) {
                    time = String.valueOf(mOHLCData.get((i + 1) * step + mDataStartIndext).getTime5());
                } else {
                    time = String.valueOf(mOHLCData.get((i + 1) * step + mDataStartIndext).getTime2());
                }
                TitleX.add(time);

            }
        }

        super.setAxisXTitles(TitleX);
    }

    /**
     * 初始化Y轴
     */
    protected void initAxisY() {
        if (mOHLCData == null || mOHLCData.size() <= 0) {
            return;
        }
        List<String> TitleY = new ArrayList<String>();
        float average = (float) ((mMaxPrice - mMinPrice) / getUperChartHeight()) / 10 * 10;
        average = average * (getUperChartHeight() / latitudeNum);
        average = Float.parseFloat(new DecimalFormat("0000.00").format(average));
        //处理所有Y刻度
        for (float i = 0; i <= latitudeNum; i++) {
            String value = Utils.numberFormatString(mMinPrice + i * average);
            TitleY.add(value);
        }
        //处理最大值
        String value = Utils.numberFormatString((mMaxPrice) / 10 * 10);

        TitleY.add(value);
        super.setAxisYTitles(TitleY);
    }

    public static String deFormatNew(String str, int type) {
        try {
            BigDecimal bigDecimal = new BigDecimal(str);
            String str_ws = "0.#";
            if (type == -1) {
                str_ws = "0.00";
            }
            for (int n = 1; type > 1 && n < type; n++) {
                str_ws = str_ws + "#";
            }
            DecimalFormat df_ls = new DecimalFormat(str_ws);
            str = df_ls.format(bigDecimal.setScale(type, BigDecimal.ROUND_FLOOR).doubleValue());
        } catch (Exception e) {
            str = "0.00";
        }
        return str;
    }


    private void drawUpperRegion(Canvas canvas) {
        if (mOHLCData == null || mOHLCData.size() <= 0) {
            return;
        }
        // 绘制蜡烛图
        Paint redPaint = new Paint();
        if (isUpFill) {
            redPaint.setStyle(Paint.Style.FILL);
        } else {
            redPaint.setStyle(Paint.Style.STROKE);
        }
        redPaint.setColor(mUpColor);
        Paint greenPaint = new Paint();
        greenPaint.setColor(mDownColor);
        if (isDownFill) {
            greenPaint.setStyle(Paint.Style.FILL);
        } else {
            greenPaint.setStyle(Paint.Style.STROKE);
        }
        // 绘最大最小值
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_AXIS_TITLE_SIZE);
        paint.setColor(super.getAxisColor());
        float width = getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2;
        mCandleWidth = width / 10.0 * 10.0 / mShowDataNum;
        double rate = (getUperChartHeight()) / (mMaxPrice - mMinPrice);
        for (int i = 0; i < mShowDataNum && mDataStartIndext + i < mOHLCData.size(); i++) {
            MarketChartData entity = mOHLCData.get(mDataStartIndext + i);
            float open = (float) ((mMaxPrice - entity.getOpenPrice()) * rate + Up_chart_margin + Up_title_height);
            float close = (float) ((mMaxPrice - entity.getClosePrice()) * rate + Up_chart_margin + Up_title_height);
            float high = (float) ((mMaxPrice - entity.getHighPrice()) * rate + Up_chart_margin + Up_title_height);
            float low = (float) ((mMaxPrice - entity.getLowPrice()) * rate + Up_chart_margin + Up_title_height);
            float left = (float) (width - mCandleWidth * (i + 1));
            float right = (float) (width - mCandleWidth * i);
            float startX = (float) (width - mCandleWidth * i - mCandleWidth / 2);
            Log.e("yongyirate", "i =" + i + " -open =" + open + " -close=" + close + " -high=" + high + " -low=" + low + " -left=" + left + " -right=" + right + " -startX=" + startX);
            if(mMaxPrice -mMinPrice ==0){
                canvas.drawLine(left, Up_chart_margin + Up_title_height, right, Up_chart_margin + Up_title_height, redPaint);
            }else {
                if (open < close) {
                    canvas.drawRect(left + 1, open, right - 1, close, greenPaint);
                    canvas.drawLine(startX, high, startX, open, greenPaint);
                    canvas.drawLine(startX, close, startX, low, greenPaint);
                } else if (open == close) {
                    canvas.drawRect(left + 1, close - 1, right - 1, open + 1, redPaint);
                    canvas.drawLine(startX, high, startX, low, redPaint);
                } else {
                    canvas.drawRect(left + 1, close, right - 1, open, redPaint);
                    canvas.drawLine(startX, high, startX, close, redPaint);
                    canvas.drawLine(startX, open, startX, low, redPaint);
                }
            }

        }


    }

    public void showSimpleView() {
        this.mShowMACD = false;
        this.mShowJKD = false;
        super.DEFAULT_LOWER_LATITUDE_NUM = 0;
        super.DEFAULT_MIDDLE_LATITUDE_NUM = 0;
        postInvalidate();
    }


    private void drawMA(Canvas canvas) {
        if (MALineData == null || MALineData.size() < 0)
            return;
        setMaxMinPrice();
        double rate = (getUperChartHeight()) / (mMaxPrice - mMinPrice);
        // 绘制上部曲线图及上部分MA值
        for (int j = 0; j < MALineData.size(); j++) {
            MALineEntity lineEntity = MALineData.get(j);

            float startX = 0;
            float startY = 0;
            Paint paint = new Paint();
            paint.setStrokeWidth(lineWidth);
            paint.setColor(lineEntity.getLineColor());
            paint.setTextSize(DEFAULT_AXIS_TITLE_SIZE);

            for (int i = 0; i < mShowDataNum
                    && mDataStartIndext + i < lineEntity.getLineData().size(); i++) {
                if (i != 0) {

                    canvas.drawLine(
                            startX,
                            startY > MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT ? MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT : startY,
                            (float) (super.getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2 - mCandleWidth * i - mCandleWidth * 0.5f),
                            (float) (((mMaxPrice - lineEntity.getLineData().get(mDataStartIndext + i)) * rate) + Up_chart_margin + Up_title_height) > MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT ? MIDDLE_CHART_TOP - 2 * TITLE_HEIGHT : (float) ((mMaxPrice - lineEntity.getLineData().get(mDataStartIndext + i)) * rate) + Up_chart_margin + Up_title_height,
                            paint);
                }
                startX = (float) (super.getWidth() - super.DEFAULT_AXIS_MARGIN_RIGHT - 2 - mCandleWidth * i - mCandleWidth * 0.5f);
                startY = (float) ((mMaxPrice - lineEntity.getLineData().get(mDataStartIndext + i)) * rate) + Up_chart_margin + Up_title_height;
            }
        }
    }

    private void initShowDataNum() {
        if (mOHLCData == null || mOHLCData.size() <= 0) {
            return;
        }
        if (mShowDataNum > mOHLCData.size()) {
            mShowDataNum = mOHLCData.size();
        }
        if (MIN_CANDLE_NUM > mOHLCData.size()) {
            mShowDataNum = mOHLCData.size();
        }
    }

    private void setCurrentData() {
        try {
            initShowDataNum();
            setMaxMinPrice();
        } catch (Exception e) {
        }
    }


    private void setMaxMinPrice() {
        if (mOHLCData == null || mOHLCData.size() <= 0 || mDataStartIndext < 0) {
            return;
        }
        mMinPrice = mOHLCData.get(mDataStartIndext).getLowPrice();
        mMaxPrice = mOHLCData.get(mDataStartIndext).getHighPrice();
        mMaxVol = mOHLCData.get(mDataStartIndext).getVol();
        mMinVol = mOHLCData.get(mDataStartIndext).getVol();
        minIndex = mDataStartIndext;
        maxIndex = mDataStartIndext;

        for (int i = 0; i < mShowDataNum && mDataStartIndext + i < mOHLCData.size(); i++) {
            MarketChartData entity = mOHLCData.get(mDataStartIndext + i);

            if (mMinPrice > entity.getLowPrice()) {
                mMinPrice = entity.getLowPrice();
                minIndex = mDataStartIndext + i;
            }
            if (mMaxPrice < entity.getHighPrice()) {
                mMaxPrice = entity.getHighPrice();
                maxIndex = i + mDataStartIndext;
            }


            if (mMaxVol < entity.getVol()) {
                mMaxVol = entity.getVol();
            }
            if (mMinVol > entity.getVol()) {
                mMinVol = entity.getVol();
            }

        }

    }

    public void setOHLCData(List<MarketChartData> OHLCData) {
        //分时，小时切换，重置  mDataStartIndext
        mDataStartIndext = 0;
        if (OHLCData == null || OHLCData.size() <= 0) {
            return;
        }

        if (null != mOHLCData) {
            mOHLCData.clear();
        }
        for (MarketChartData e : OHLCData) {
            addData(e);
        }
        initMALineData();
        initMAStickLineData();
        // mMACDData = new MACDEntity(mOHLCData);
        mMACDData = new MACD(mOHLCData);
        mKDJData = new KDJ(mOHLCData);
        setCurrentData();
        postInvalidate();
    }

    public void addData(MarketChartData entity) {
        if (null != entity) {
            if (null == mOHLCData || 0 == mOHLCData.size()) {
                mOHLCData = new ArrayList<>();
                this.mMinPrice = ((int) entity.getLowPrice()) / 10 * 10;
                this.mMaxPrice = ((int) entity.getHighPrice()) / 10 * 10;
            }

            this.mOHLCData.add(entity);

            if (this.mMinPrice > entity.getLowPrice()) {
                this.mMinPrice = ((int) entity.getLowPrice()) / 10 * 10;
            }

            if (this.mMaxPrice < entity.getHighPrice()) {
                this.mMaxPrice = 10 + ((int) entity.getHighPrice()) / 10 * 10;
            }

        }
    }

    //K线均线
    private void initMALineData() {
        MALineData = new ArrayList<>();
        if (mHave5Line) {
            MALineEntity MA5 = new MALineEntity();
            MA5.setTitle("MA5");
            MA5.setLineColor(kline5dayline);
            MA5.setLineData(initMA(mOHLCData, 5));
            MALineData.add(MA5);

        }
        if (mHave10Line) {
            MALineEntity MA10 = new MALineEntity();
            MA10.setTitle("MA10");
            MA10.setLineColor(kline10dayline);
            MA10.setLineData(initMA(mOHLCData, 10));
            MALineData.add(MA10);
        }
        if (mHave30Line) {
            MALineEntity MA30 = new MALineEntity();
            MA30.setTitle("MA30");
            MA30.setLineColor(kline30dayline);
            MA30.setLineData(initMA(mOHLCData, 30));
            MALineData.add(MA30);
        }

    }

    //量能线均线
    private void initMAStickLineData() {
        //以下计算VOL
        MAVLineData = new ArrayList<>();

        //计算5日均线
        MALineEntity VMA5 = new MALineEntity();
        VMA5.setTitle("MA5");
        VMA5.setLineColor(kline5dayline);
        VMA5.setLineData(initVMA(mOHLCData, 5));
        MAVLineData.add(VMA5);

        //计算10日均线
        MALineEntity VMA10 = new MALineEntity();
        VMA10.setTitle("MA10");
        VMA10.setLineColor(kline10dayline);
        VMA10.setLineData(initVMA(mOHLCData, 10));
        MAVLineData.add(VMA10);


    }

    /**
     * 初始化MA值，从数组的最后一个数据开始初始化
     *
     * @param entityList
     * @param days
     * @return
     */
    private List<Double> initMA(List<MarketChartData> entityList, int days) {
        if (days < 2 || entityList == null || entityList.size() <= 0) {
            return null;
        }
        List<Double> MAValues = new ArrayList<Double>();

        Double sum = 0.0;
        Double avg = 0.0;
        // java.text.DecimalFormat   df =new   java.text.DecimalFormat("#.00");
        for (int i = entityList.size() - 1; i >= 0; i--) {
            Double close = entityList.get(i).getClosePrice();
            if (entityList.size() - i < days) {
                sum = sum + close;
                int d = entityList.size() - i;
                avg = sum / d;
                //  avg=Double.parseDouble(df.format(avg));

            } else {
                sum = 0.0;
                for (int j = 0; j < days; j++) {
                    sum = sum + entityList.get(i + j).getClosePrice();
                }

                avg = sum / days;
                //  avg=Double.parseDouble(df.format(avg));

            }
            MAValues.add(avg);
        }

        List<Double> result = new ArrayList<Double>();
        for (int j = MAValues.size() - 1; j >= 0; j--) {
            result.add(MAValues.get(j));
        }
        return result;
    }

    private List<Double> initVMA(List<MarketChartData> entityList, int days) {

        if (days < 2 || entityList == null || entityList.size() <= 0) {
            return null;
        }
        List<Double> MAValues = new ArrayList<Double>();

        Double sum = 0.0;
        Double avg = 0.0;
        //  java.text.DecimalFormat   df =new   java.text.DecimalFormat("#.00");
        for (int i = entityList.size() - 1; i >= 0; i--) {
            Float close = entityList.get(i).getVol();
            if (entityList.size() - i < days) {
                sum = sum + close;
                int d = entityList.size() - i;
                avg = sum / d;
                //     avg=Double.parseDouble(df.format(avg));

            } else {
                sum = 0.0;
                for (int j = 0; j < days; j++) {
                    sum = sum + entityList.get(i + j).getVol();
                }

                avg = sum / days;
                //     avg=Double.parseDouble(df.format(avg));

            }
            MAValues.add(avg);
        }

        List<Double> result = new ArrayList<Double>();
        for (int j = MAValues.size() - 1; j >= 0; j--) {
            result.add(MAValues.get(j));
        }
        return result;
    }
}