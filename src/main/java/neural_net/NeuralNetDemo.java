package neural_net;

/** Demo of Neural Network basics:
 *  Nodes, connections between them, weights.
 */
class NeuralNetDemo
{
  public static void main(String[] args)
  {
    // Configure neural net
    // 1) Input layer
    final InputNode input1 = new InputNode("Has Battery", 1.0);  
    final InputNode input2 = new InputNode("Has Heart", 0.0);
    
    // Most "real" nets have MANY more nodes in each layer,
    // plus there tend to be intermediate layers...

    // 2) Output layer
    final Node output1 = new Node("IsRobot", input1, input2);  
    final Node output2 = new Node("IsPerson", input1, input2);

    // Initially, the network tends to compute nonsense
    System.out.println(input1);
    System.out.println(input2);
    System.out.println("1st attempt:");
    output1.update();
    output2.update();
    System.out.println(output1);
    System.out.println(output2);
    System.out.println("--> Wrong!");

    // Learn/update:
    // In here we 'teach' the network what weights will be correct.
    output1.setWeights(1.0, -1.0);
    output2.setWeights(-1.0, 1.0);
    // In real networks, the weights are automagically determined
    // somewhat like this:
    // while (overall output is wrong)
    // {
    //   if (output #i is very wrong)
    //     change all the weights for inputs of output #i a lot;
    //   else if (output #i is a little wrong)
    //     change all the weights for inputs of output #i a little;
    //   else if (output #i is correct)
    //     leave weights for inputs of output #i as they are;
    // }
    System.out.println("2nd attempt:");
    output1.update();
    output2.update();
    System.out.println(output1);
    System.out.println(output2);
    System.out.println("--> OK...");

    // Test...
    input1.setValue(0.0);
    input2.setValue(1.0);
    System.out.println("Test with different input");
    System.out.println(input1);
    System.out.println(input2);
    output1.update();
    output2.update();
    System.out.println(output1);
    System.out.println(output2);
    System.out.println("--> Correct!");
  }
}