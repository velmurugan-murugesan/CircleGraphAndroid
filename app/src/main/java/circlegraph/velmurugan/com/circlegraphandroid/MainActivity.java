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

    //circleGraphView.setStrokeSize(70);

    circleGraphView.setTextEnabled(true);

    circleGraphView.setTextColor(Color.WHITE);
    circleGraphView.setTextSize(40);

    circleGraphView.setCircleType(CircleType.HALF_CIRCLE);

    circleGraphView.setselectedItem(2);

    circleItemsList.clear();
    CircleItems circleItem = new CircleItems("Tamil",50);
    circleItemsList.add(circleItem);
    circleItem = new CircleItems("English",30);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("Hindi",40);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("Malayalam",30);
    circleItemsList.add(circleItem);

    circleItem = new CircleItems("Telugu",20);
    circleItemsList.add(circleItem);
    circleGraphView.addItems(circleItemsList);

    selectedItemTextView.setText(circleItemsList.get(circleGraphView.getSelectedItem()).getName());

  }

  @Override
  public void onItemSelectListener(CircleItems CircleItems) {
    selectedItemTextView.setText(CircleItems.getName());
    circleGraphView.setselectedItem(circleItemsList.indexOf(CircleItems));
  }
}