# CircleGraph For Android

CircleGraphAndroid library having two types of graph.

## **1.Full Circle Graph**

## ![](/screenshots/full_circle_graph.png)

## **2.Half Circle Graph**

![](/screenshots/half_circle.png)

## Including in your project

### **Step 1. **Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
    repositories {
        ...
            maven { url 'https://jitpack.io' }
        }
    }
```

### **Step 2. **Add the dependency

```
    compile 'com.github.velmurugan35:CircleGraphAndroid:-SNAPSHOT'
```

## **How to Use**

Define the VIew in your layout XML file.

```
<circlegraph.velmurugan.com.circlegraph.CircleGraphView
            android:id="@+id/graphView"
            android:layout_width="match_parent"
            android:layout_height="400dp"
            android:layout_margin="10dp"
            circlegraph:text_enabled="true"
            circlegraph:text_size="20sp"
            android:layout_gravity="center_horizontal"
            circlegraph:circle_type="half_circle"
            circlegraph:text_color="@android:color/holo_green_light"
            />
```

## 



