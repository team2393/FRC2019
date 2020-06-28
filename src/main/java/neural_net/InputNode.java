package neural_net;

/** Input node to a neural network: Name, value */
class InputNode
{
  private final String name;
  protected double value;

  public InputNode(final String name, final double value)
  {
    this.name = name;
    this.value = value;
  }

  public void setValue(final double new_value)
  {
    value = new_value;
  }

  public double getValue()
  {
      return value;
  }

  @Override
  public String toString()
  {
      return name + " = " + value;
  }
}