package circlegraph.velmurugan.com.circlegraph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class CircleGraphView extends View {

  private String TAG = CircleGraphView.class.getSimpleName();

  private int canvasWidth;
  private  int canvasHeight;

  private RectF rectF;
  private Paint arcPaint;

  private List<CircleItems> CircleItems;
  private int totalValue;
  private Paint textPaint;

  private String circleType;

  private GraphClickListener graphClickListener;
  private int selectedItem;


  private int selectorBarSize = 20;

  public Paint getArcPaint() {
    return arcPaint;
  }

  public void setArcPaint(Paint arcPaint) {
    this.arcPaint = arcPaint;
  }


  //By default Text Not enabled
  boolean textEnabled = false;
  // Default Text Color
  private int textColor = Color.BLACK;
  //Default Text size
  private int textSize = 40;

  //default stroke size
  int strokeSize = 70;

  //Default Circle Start Angle
  private float startAngle = 180;

  private float circleRadius = 0;

  //default Padding
  int padding = 0;

  //default Circle Size
  int circleSize = 180;

  //half Circle Size
  int HALF_CIRCLE_SIZE = 180;

  // Default Full Circle Size
  int FULL_CIRCLE_SIZE = 360;

  //Code For Touch Listener
  private Map<String,List<PointF>> maps;
  PointF pointF = null;


  public CircleGraphView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    graphClickListener = (GraphClickListener) context;

    TypedArray typedArray  = context.obtainStyledAttributes(attrs, R.styleable.CircleGraphView);
    textColor = typedArray.getColor(R.styleable.CircleGraphView_text_color,Color.BLACK);
    textSize = (int) typedArray.getDimension(R.styleable.CircleGraphView_text_size,40);
    textEnabled = typedArray.getBoolean(R.styleable.CircleGraphView_text_enabled, true);
    strokeSize = typedArray.getInt(R.styleable.CircleGraphView_stroke_size, 70);
    circleType = typedArray.getString(R.styleable.CircleGraphView_circle_type);
    typedArray.recycle();

    rectF = new RectF();
    CircleItems = new ArrayList<>();
    maps = new HashMap<>();
  }

  private void setupStartAngle(){
    if(circleSize == 180){
      startAngle = HALF_CIRCLE_SIZE;
    } else {
      startAngle = FULL_CIRCLE_SIZE;
    }
  }

  public float getStartAngle() {
    return startAngle;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    //Get canvas Width and Height
    canvasWidth = MeasureSpec.getSize(widthMeasureSpec);
    canvasHeight = MeasureSpec.getSize(heightMeasureSpec);

    pointF = new PointF(canvasWidth/2 ,canvasWidth/2);
    setupArcPaint();
    setupStartAngle();
    setRectF();
  }

  private void setupArcPaint() {
    arcPaint = new Paint();
    arcPaint.setStyle(Style.STROKE);
    arcPaint.setAntiAlias(true);
    arcPaint.setStrokeWidth(getStrokeSize());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Small Sweep for white gaps
    for(int i = 0; i< CircleItems.size(); i++) {
      arcPaint.setColor(CircleItems.get(i).getColor());
      log("startAngle = "+startAngle);
      float sweepAngle = getSweepAngle(CircleItems.get(i).getPercentage());

      if(i == selectedItem){
        arcPaint.setStrokeWidth(getStrokeSize() + 30);
      } else {
        arcPaint.setStrokeWidth(getStrokeSize());
      }
      canvas.drawArc(getRectF(),startAngle,sweepAngle,false, arcPaint);

      if(textEnabled){
        addArcText(canvas,sweepAngle,i);
      }

      List<PointF> pointFList = new ArrayList<>();
      for (int j = (int)startAngle;j<startAngle + sweepAngle ;j++){
        pointFList.add(getArcByPoint(pointF,(getRectF().right - getRectF().left) / 2 ,j));
      }

      maps.put(CircleItems.get(i).getName(),pointFList);
      startAngle = startAngle + sweepAngle;

    }
  }

  public int getStrokeSize() {
    return strokeSize;
  }

  public void setStrokeSize(int strokeSize) {
    this.strokeSize = strokeSize;
    setRectF();
    setupStartAngle();
    setupArcPaint();
    invalidate();
  }


  public void addItems(List<CircleItems> CircleItems) {

    totalValue = 0;

    for (int i = 0; i < CircleItems.size() ; i++){
      int tempValue = 0;
      if (CircleItems.get(i).getColor() == 0) {
        CircleItems.get(i).setColor(getRamdomColor());
        CircleItems.set(i, CircleItems.get(i));
      }

      tempValue = CircleItems.get(i).getPercentage();
      totalValue = totalValue + tempValue;
    }
    if(textEnabled){
      setupTextPaint();
    }
    this.CircleItems = CircleItems;
    setupStartAngle();
    setRectF();
    invalidate();
  }

  private PointF getArcByPoint(@NotNull PointF center, float radius, float angleDegrees){

    return new PointF((float)(center.x + radius * cos(toRadians(angleDegrees))),
        (float)(center.y + radius * sin(toRadians(angleDegrees))));
  }

  public void setRectF() {

    if(strokeSize > 0){
      padding = strokeSize / 2;
    }

    if(circleRadius > 0){
      pointF = new PointF((float) ((circleRadius * 2) / 2) ,(float) ((circleRadius * 2) / 2));
      this.rectF.set(padding + selectorBarSize, padding + selectorBarSize, (circleRadius * 2) - (padding + selectorBarSize), (circleRadius * 2) - (padding + selectorBarSize));
    }else {
      pointF = new PointF((float) (canvasWidth/ 2) ,(float) (canvasWidth/ 2));
      this.rectF.set(padding + selectorBarSize, padding + selectorBarSize, canvasWidth - (padding + selectorBarSize), canvasWidth - (padding + selectorBarSize));
    }
  }

  public RectF getRectF() {
    return rectF;
  }

  private void log(String log){
    Log.d(TAG,log);
  }

  public int getViewPadding() {
    return padding;
  }

  public Paint getTextPaint() {
    return textPaint;
  }

  public void setTextColor(int textColor) {
    this.textColor = textColor;
  }

  public int getTextColor() {
    return textColor;
  }

  public void setTextSize(int textSize) {
    this.textSize = textSize;
  }

  public int getTextSize() {
    return textSize;
  }

  public void setTextEnabled(boolean textEnabled) {
    this.textEnabled = textEnabled;
  }

  public boolean isTextEnabled() {
    return textEnabled;
  }

  public void setCircleRadius(int circleRadius) {
    this.circleRadius = circleRadius;
  }

  public float getCircleRadius() {
    return circleRadius;
  }

  public void setCircleType(CircleType circleType) {
    if(circleType.equals(CircleType.HALF_CIRCLE)){
      this.circleSize = 180;
    }else {
      this.circleSize = 360;
    }
  }

  public String getCircleType() {
    return circleType.toString();
  }

  float getSweepAngle(int value){
    float currentInTotal = (float) value / (float) totalValue * 100 ;
    return (currentInTotal * circleSize) / 100;
  }

  public void setupTextPaint() {
    textPaint = new Paint();
    textPaint.setAntiAlias(true);
    textPaint.setStyle(Style.FILL_AND_STROKE);
    textPaint.setColor(getTextColor());
    textPaint.setTextSize(getTextSize());
    textPaint.setFakeBoldText(true);
    textPaint.setTextAlign(Align.CENTER);
  }

  private void addArcText(Canvas canvas, float sweepAngle, int i) {
    Paint textPaint = getTextPaint();
    Path path = new Path();
    path.arcTo(getRectF(), startAngle, sweepAngle);
    if(CircleItems.get(i).getName() != null){
      canvas.drawTextOnPath(CircleItems.get(i).getName(), path, 0, 0, textPaint);
    }
  }

  private int getRamdomColor(){
    Random rnd = new Random();
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    switch (event.getAction()){
      case MotionEvent.ACTION_DOWN:
        checkCliked(event.getX(),event.getY());
        break;
      case MotionEvent.ACTION_MOVE:

        break;
      case MotionEvent.ACTION_UP:
        break;

    }
    return true;
  }


  private void checkCliked(float x, float y){
    String cliked = null;
    for (Map.Entry<String,List<PointF>> entry : maps.entrySet()){
      List<PointF> pointFS = maps.get(entry.getKey());
      for (PointF pointF : pointFS){
        float x1 = pointF.x + (getStrokeSize() / 2);
        float x2 = pointF.x - (getStrokeSize() / 2);

        float y1 = pointF.y + (getStrokeSize() / 2);
        float y2 = pointF.y - (getStrokeSize() / 2);
        if((x > x2 && x < x1) &&( y > y2 && y < y1 )){
              cliked = entry.getKey();
        }
      }

    }

    if(cliked != null){
      for(CircleItems CircleItems : this.CircleItems){
        if(CircleItems.getName() != null && CircleItems.getName().equals(cliked)){
          graphClickListener.onItemSelectListener(CircleItems);
        }
      }
    }
  }

  public void setselectedItem(int selectedItem) {
    this.selectedItem = selectedItem;
    setupStartAngle();
    setRectF();
    invalidate();
  }

  public int getSelectedItem() {
    return selectedItem;
  }

}
