package neural_net;

/** Demo of Neural Network that 'learns'.
 * 
 *  Ra
 */
class NeuralNetLearn
{
  public static void main(String[] args)
  {
    // Configure neural net
    // 1) Input layer
    final InputNode input1 = new InputNode("Has Battery", 1.0);  
    final InputNode input2 = new InputNode("Has Heart", 0.0);
    
    // 2) Output layer
    final Node output1 = new Node("IsRobot", input1, input2);  
    final Node output2 = new Node("IsPerson", input1, input2);

    // Initially, the network tends to compute nonsense
    System.out.println(input1);
    System.out.println(input2);

    // This is what the network _should_ compute
    final double goal1 = 1.0, goal2 = 0.0;
    
    // Keep 'learning' until the network finds the desired result
    for (int run=1; run<10; ++run)
    {
      System.out.println("Test run " + run + ":");
      output1.update();
      output2.update();
      System.out.println(output1);
      System.out.println(output2);

      double error1 = output1.getValue() - goal1;
      double error2 = output2.getValue() - goal2;
      if (Math.abs(error1) < 0.2  &&  Math.abs(error2) < 0.2)
      {
        System.out.println("--> Good enough!");
        break;
      }
      else
        System.out.println("--> Wrong!");

      output1.learnRandomly(error1);
      output2.learnRandomly(error2);
    }
  }
}