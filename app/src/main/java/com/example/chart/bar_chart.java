package com.example.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class bar_chart extends View implements Runnable {
    Context context;
    public bar_chart(Context context) {
        super(context);
        this.context=context;

        init();
        new Thread(this).start();
    }

    public bar_chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
        new Thread(this).start();
    }
    Paint mLine,mLineText;//粗線
    Paint mLine1;//粗線1(預設:紅)
    Paint mLine2;//粗線2(預設:藍)
    private void init(){
        mLine = new Paint();
        mLine.setColor(Color.GRAY);
        mLine.setStrokeWidth(2);
        mLineText = new Paint();
        mLineText.setColor(Color.WHITE);
        mLineText.setTextSize(20);
        mLine1 = new Paint();
        mLine1.setColor(Color.rgb(255, 128, 128));
        mLine1.setStrokeWidth(2);
        mLine1.setTextSize(20);
        mLine2 = new Paint();
        mLine2.setColor(Color.rgb(148, 255, 255));
        mLine2.setStrokeWidth(2);
        mLine2.setTextSize(20);

    }
    //---------------------------------------------

    /**
     * 設定粗線1顏色
     * @param r
     * @param g
     * @param b
     */
    public void setmLine1Color(int r,int g,int b){
        mLine1.setColor(Color.rgb(r, g, b));
    }
    /**
     * 設定粗線2顏色
     * @param r
     * @param g
     * @param b
     */
    public void setmLine2Color(int r,int g,int b){
        mLine2.setColor(Color.rgb(r, g, b));
    }
    //---------------------------------------------
    int[] Value1=new int[11];//資料1
    int[] Value2=new int[11];//資料2
    int ValueN=0;//資料總數
    int ValueMax=10;//資料最大數
    //---------------------------------------------
    int scale_spaceX=10;
    int Max_Y=100,Min_Y=0,scale_spaceY=10;
    /**
     * 設定X軸數值大小
     * @param ValueMax 資料最大數
     * @param scale_spaceX 刻度間距
     */
    public void setLabelX(int ValueMax,int scale_spaceX){
        this.ValueMax=ValueMax;
        this.scale_spaceX=scale_spaceX;
        Value1=new int[ValueMax+1];
        Value2=new int[ValueMax+1];
        this.ValueMax=ValueMax;
    }
    /**
     * 設定Y軸數值大小
     * @param Max_Y 最大數值
     * @param Min_Y 最小數值
     * @param scale_spaceY 刻度間距
     */
    public void setLabelY(int Max_Y,int Min_Y,int scale_spaceY){
        this.Max_Y=Max_Y;
        this.Min_Y=Min_Y;
        this.scale_spaceY=scale_spaceY;
    }
    //----------------------------------------------------------------
    String LabelText1="資料標籤1";
    /**
     * 設定資料標籤1
     * @param LabelText1
     */
    public void setLabelText1(String LabelText1){
        this.LabelText1=LabelText1;
    }
    //----------------------------------------------------------------

    /**
     * 加入資料
     * @param _Value1 資料1(預設:紅色)
     * @param _Value2 資料2(預設:藍色)
     */
    public void addValue(int _Value1,int _Value2){
        Value1[ValueN]=_Value1;
        Value2[ValueN]=_Value2;
        if (ValueN==ValueMax){
            addValue_(_Value1,_Value2);
        }else {
            ValueN++;
        }

    }
    /**
     * 當資料總數超過資料最大數後
     * 將每筆資料往前左移
     * 再加入最後一筆資料
     * @param _Value1 資料1(預設:紅色)
     * @param _Value2 資料2(預設:藍色)
     */
    public void addValue_(int _Value1,int _Value2){
        for (int a=0;a<ValueN;a++){
            Value1[a]=Value1[a+1];
            Value2[a]=Value2[a+1];
        }
        Value1[ValueN]=_Value1;
        Value2[ValueN]=_Value2;
    }
    //------------------------------------------------------

    boolean RandomData=false;

    /**
     * 設定是否要開啟隨機產生資料
     * @param RandomData
     */
    public void setGetRandomData(boolean RandomData){
        this.RandomData=RandomData;
    }

    boolean Circle=true;

    /**
     * 每筆資料是否開啟圓點標示
     * @param Circle
     */
    public void setCircle(boolean Circle){
        this.Circle= Circle;
    }

    //---------------------------------------------

    /**
     * 顯示座標軸
     * @param canvas
     */
    private void label(Canvas canvas){
        for (int a=0;a<=scale_spaceY;a++){
            //顯示左邊Y軸
            canvas.drawText(((Max_Y-Min_Y)-((a)*((Max_Y-Min_Y)/scale_spaceY))+Min_Y)+""
                    ,spaceX/3,spaceY+a*(chartH/scale_spaceY)
                    ,mLineText);
            //顯示右邊Y軸
            canvas.drawText(((Max_Y-Min_Y)-((a)*((Max_Y-Min_Y)/scale_spaceY))+Min_Y)+""
                    ,spaceX+chartW+spaceX/3,spaceY+a*(chartH/scale_spaceY)
                    ,mLineText);
        }
        //顯示底邊X軸
        for (int a=0;a<scale_spaceX;a++){
            canvas.drawText((a+1)*(ValueMax/scale_spaceX)+""
                    ,spaceX+(a+1)*(chartW/(scale_spaceX))-10
                    ,spaceY+chartH+spaceY/3,mLineText);
        }
    }

    /**
     * 顯示資料標籤
     * @param canvas
     */
    private void labelText(Canvas canvas){
        canvas.drawText("●",100,chartH+spaceY*1.8f,mLine1);
        canvas.drawText(LabelText1,150,chartH+spaceY*1.8f,mLineText);
    }

    /**
     * 顯示 資料座標及直線
     * @param canvas
     */
    private void displayValue(Canvas canvas){
        for (int a=0;a<ValueN;a++){
            int newX1=(a+1)*chartW/ValueMax-(chartW/ValueMax/2)+spaceX;
            int newY1=chartH-(Value1[a]*chartH/Max_Y)+spaceY;
            canvas.drawRect(newX1-chartW/ValueMax/3,newY1
                    ,newX1+chartW/ValueMax/3,chartH+spaceY,mLine1);
        }
    }
    //------------------------------------------------------
    int height=0,width=0;//獲得元件長高
    int spaceX=50;//設定左右空格(左右Y軸的空間)
    int spaceY=50;//設定上下空格(底部X軸+資料標籤的空間)
    int chartW=0,chartH=0;//設定折線圖長高
    /**
     * 顯示背景+格線
     * @param canvas
     */
    private void BackGround(Canvas canvas) {
        //設定背景(黑)
        canvas.drawColor(Color.BLACK);
        //獲取折線圖長度
        chartW=((width-spaceX*2)/scale_spaceX)*scale_spaceX;
        //獲取折線圖高度
        chartH=((height-spaceY*2)/scale_spaceY)*scale_spaceY;
        //繪製水平格線--------------------------------------
        for (int a=0;a<=scale_spaceY;a++) {
            canvas.drawLine(spaceX,spaceY + a * (chartH / scale_spaceY)
                    ,spaceX+chartW,spaceY + a * (chartH / scale_spaceY)
                    ,mLine);
        }
        //繪製垂直格線--------------------------------------
        for (int a=0;a<scale_spaceX+1;a++){
            canvas.drawLine(spaceX+(a)*(chartW/(scale_spaceX)),spaceY,
                    spaceX+(a)*(chartW/(scale_spaceX)),spaceY+chartH,mLine);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //獲取元件高度
        height = this.getHeight();
        //獲取元件寬度
        width = this.getWidth();
        BackGround(canvas);
        label(canvas);
        labelText(canvas);
        displayValue(canvas);
        //是否要產生隨機資料
        if (RandomData==true){
            addValue((int)(Math.random()*(Max_Y-Min_Y))+Min_Y
                    ,(int)(Math.random()*(Max_Y-Min_Y))+Min_Y);
        }
    }


    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
                postInvalidate();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // 使用postInvalidate可以直接在线程中更新界面
        }
    }

}
