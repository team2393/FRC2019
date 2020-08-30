package neural_net;

/** Another demo of Neural Network basics:
 *  Nodes, connections between them, weights.
 */
class NeuralNetDemo2
{
  public static void main(String[] args)
  {
    // Configure neural net
    // 1) Input layer
    final InputNode warm = new InputNode("Is Warm", 1.0);  
    final InputNode windy = new InputNode("Is Windy", 1.0);
    
    // 2) Output layer
    final Node summer_nice = new Node("Nice Summer Day", warm, windy);  
    final Node summer_storm = new Node("Summer Storm", warm, windy);  
    final Node winter_nice = new Node("Nice Winter Day", warm, windy);  
    final Node winter_storm = new Node("Winter Storm", warm, windy);
    final Node[] outputs = { summer_nice, summer_storm, winter_nice, winter_storm };

    // Initially, the network tends to compute nonsense
    System.out.println(warm);
    System.out.println(windy);
    System.out.println("1st attempt:");
    for (Node output : outputs)
      output.update();
    for (Node output : outputs)
      System.out.println(output);
    System.out.println("--> Wrong!");

    // Learn/update:
    // In here we 'teach' the network what weights will be correct.
    summer_nice.setWeights(1, -1);
    summer_storm.setWeights(1, 1);
    winter_nice.setWeights(-1, -1);  
    winter_storm.setWeights(-1, 1);
    // In real networks, the weights are automagically determined...
    System.out.println("2nd attempt:");
    for (Node output : outputs)
      output.update();
    for (Node output : outputs)
      System.out.println(output);
    System.out.println("--> OK...");

    // Test...
    System.out.println("Test with different input");
    warm.setValue(-1);
    windy.setValue(-1);
    System.out.println(warm);
    System.out.println(windy);
    for (Node output : outputs)
      output.update();
    for (Node output : outputs)
      System.out.println(output);
    System.out.println("--> Correct?");

    // Debatable:
    // This "works", but we use input values -1 to 1.
    // warm = 1   => It's warm
    // warm = -1  => It's cold
    // The rest of the code, specifically Node.update(),
    // treats any value below zero as zero,
    // i.e. doesn't really allow negative values.
    //
    // Should the input values thus be in the range 0..1?
    // warm = 1 means it's indeed warm,
    // and warm = 0 means it's cold?
    // That won't "work" with this network.
    // Warm = 0 mostly means "not warm",
    // and we would have to add another input node 'cold':
    // warm = 1, cold = 0  =>  It's warm
    // warm = 0, cold = 1  =>  It's cold
    // warm = 0, cold = 0  =>  We don't really know...
  }
}