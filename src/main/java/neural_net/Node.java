package neural_net;

/** Intermediate or output node of a neural network:
 *  N inputs, each with a weight.
 */
class Node extends InputNode
{
  private final InputNode[] inputs;
  private final double[] weights;

  /** Create node with name and inputs.
   *  Initial weights will all be 0.5.
   */
  public Node(final String name, final InputNode... inputs)
  {
    super(name, 0.0);
    this.inputs = inputs;
    weights = new double[inputs.length];
    for (int i=0;  i<weights.length;  ++i)
       weights[i] = 0.5;
  }

  /** Update one weight */
  public void setWeight(final int i, final double weight)
  {
    weights[i] = weight;
  }

  /** Update all weights */
  public void setWeights(final double... new_weights)
  {
    if (new_weights.length != weights.length)
      throw new IllegalArgumentException("Need " + weights.length + " weights");
      for (int i=0;  i<weights.length;  ++i)
        weights[i] = new_weights[i];
 }

  /** Compute value from weighted inputs */
  public void update()
  {
    // This is the key idea of a neural network:
    // Nodes simply sum up their weighted input values.
    double sum = 0.0;
    for (int i=0;  i<weights.length;  ++i)
      sum += inputs[i].value * weights[i];
    
    // If the result is positive, we use it.
    // Negative means: 0.0.
    if (sum >= 0)
      value = sum;
    else
      value = 0.0;
  }
}