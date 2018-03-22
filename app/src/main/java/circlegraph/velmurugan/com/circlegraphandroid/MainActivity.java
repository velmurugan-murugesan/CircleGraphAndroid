package circlegraph.velmurugan.com.circlegraphandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import circlegraph.velmurugan.com.circlegraph.CircleGraphView;
import circlegraph.velmurugan.com.circlegraph.CircleItems;
import circlegraph.velmurugan.com.circlegraph.CircleType;
import circlegraph.velmurugan.com.circlegraph.GraphClickListener;

public class MainActivity extends AppCompatActivity implements GraphClickListener {

  CircleGraphView circleGraphView;

  List<CircleItems> circleItemsList;

  private TextView selectedItemTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    circleItemsList = new ArrayList<>();
    circleGraphView = (CircleGraphView) findViewById(R.id.graphView);
    selectedItemTextView =(TextView) findViewById(R.id.selectedItem);


    circleGraphView.setStrokeSize(50);

    circleGraphView.setTextEnabled(false);

    circleGraphView.setTextColor(Color.WHITE);
    circleGraphView.setTextSize(30);

    circleGraphView.setCircleType(CircleType.HALF_CIRCLE);

    circleGraphView.setselectedItem(2);

    circleItemsList.clear();
    CircleItems circleItem = new CircleItems("name 1",20, Color.RED);
    circleItemsList.add(circleItem);
    circleItem = new CircleItems("name 2",20,Color.BLUE);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 3",20);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 4",20);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 5",20);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 6",20);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 7",20);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("name 8",20);
    circleItemsList.add(circleItem);
    circleGraphView.addItems(circleItemsList);

    selectedItemTextView.setText(circleItemsList.get(circleGraphView.getSelectedItem()).getName());

  }

  private int getRamdomColor(){
    Random rnd = new Random();
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
  }

  @Override
  public void onItemSelectListener(CircleItems CircleItems) {
    selectedItemTextView.setText(CircleItems.getName());
    circleGraphView.setselectedItem(circleItemsList.indexOf(CircleItems));
  }
}