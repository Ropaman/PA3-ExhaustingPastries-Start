/**
 * PA3Main.java, PA3-ExhaustingPastries assignment
 * 
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
public class PA3Main {

	public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(new File(args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String bugetstr = input.nextLine();
        Integer buget = Integer.parseInt(bugetstr);
        HashMap<String, Integer> nameandcost = getmap(input);
        ArrayList<String> sortedkeys = new ArrayList<String>(
                nameandcost.keySet());
        Collections.sort(sortedkeys);
        for (int i = 0; i < sortedkeys.size(); i++) {
            System.out.println(
                    sortedkeys.get(i) + " costs $"
                            + nameandcost.get(sortedkeys.get(i)));
        }
        System.out.println("");
        Integer uniquepastry = 0;
        Integer totalcost = 0;
        customer(nameandcost, buget, uniquepastry, totalcost, sortedkeys);
    }

    public static HashMap<String, Integer> getmap(Scanner input) {
        HashMap<String, Integer> nameandcost = new HashMap<String, Integer>();
        ArrayList<String> pastryname = new ArrayList<String>();
        ArrayList<String> priceperinch = new ArrayList<String>();
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split(":");
            pastryname.add(line[0]);
            priceperinch.add(line[1]);
        }
        Integer num = priceperinch.size();
        Integer[] cost1 = new Integer[priceperinch.size()];
        Integer[] cost = singlecost(priceperinch, cost1, num);
        for (int i = 0; i < pastryname.size(); i++) {
            nameandcost.put(pastryname.get(i), cost[i]);
        }
        return nameandcost;
    }

    public static Integer[] singlecost(ArrayList<String> priceperinch,Integer[] cost,Integer num) {
        
        if (priceperinch.size() == 0) {
            return cost;
        }
        String[] inchpricearray = priceperinch.get(0).split(" ");
        Integer maxcost = 0;
        for(int i =1;i<inchpricearray.length;i++) {
            Integer price = Integer.parseInt(inchpricearray[i]);
            Integer slice = (inchpricearray.length - 1) / i;
            if(price*slice>maxcost) {
                maxcost = price*slice;
            }
        }
        cost[num-priceperinch.size()]=maxcost;
        priceperinch.remove(priceperinch.get(0));
        return singlecost(priceperinch, cost, num);
    }

    public static void customer(HashMap<String, Integer> nameandcost,
            Integer buget, Integer uniquepastry, Integer totalcost,
            ArrayList<String> sortedkeys) {

        Integer[] costarray = new Integer[nameandcost.size()];
        for (int i = 0; i < nameandcost.size(); i++) {
            costarray[i] = nameandcost.get(sortedkeys.get(i));
        }
        if (costarray.length == 0) {
            System.out.println("");
            System.out.println(
                    "The max number of unique pastries that can be bought with $"
                            + buget + " is: " + uniquepastry);
            System.exit(0);
        }
        Arrays.sort(costarray);
        totalcost = totalcost + costarray[0];
        if (totalcost > buget) {
            System.out.println("");
            System.out.println(
                    "The max number of unique pastries that can be bought with $"
                            + buget + " is: " + uniquepastry);
            System.exit(0);
        }
        for (int i = 0; i < costarray.length; i++) {
            if (nameandcost.get(sortedkeys.get(i)) == costarray[0]) {
                System.out.println("Can buy " + sortedkeys.get(i) + " for $"
                        + nameandcost.get(sortedkeys.get(i)));
                nameandcost.remove(sortedkeys.get(i));
                sortedkeys.remove(i);
                uniquepastry = uniquepastry + 1;
                customer(nameandcost, buget, uniquepastry, totalcost,
                        sortedkeys);
            }
        }
    }
}
