package Factories;

import Model.Make;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */
public class MakeFactory {

    public static List<Make> GetMakes() {
        ArrayList<Make> makes = new ArrayList<>();

        makes.add(new Make("Yamaha", 0));
        makes.add(new Make("Honda", 1));
        makes.add(new Make("Ducati", 2));
        makes.add(new Make("Kawasaki", 3));
        makes.add(new Make("Harley-Davidson", 4));

        return makes;
    }
}
