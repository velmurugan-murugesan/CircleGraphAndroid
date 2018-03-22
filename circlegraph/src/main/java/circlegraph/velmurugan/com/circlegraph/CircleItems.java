package circlegraph.velmurugan.com.circlegraph;

public class CircleItems {

  private String name;

  private int percentage;

  private int color;

  public CircleItems(int percentage) {
    this.name = null;
    this.color = 0;
    this.percentage = percentage;
  }

  public CircleItems(String name, int percentage) {
    this.name = name;
    this.percentage = percentage;
    this.color = 0;
  }

  public CircleItems(String name, int percentage, int color) {
    this.name = name;
    this.percentage = percentage;
    this.color = color;
  }

  public CircleItems(int percentage, int color) {
    this.name = null;
    this.percentage = percentage;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPercentage() {
    return percentage;
  }

  public void setPercentage(int percentage) {
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return "CircleItems{" +
        "name='" + name + '\'' +
        ", percentage=" + percentage +
        '}';
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }
}
