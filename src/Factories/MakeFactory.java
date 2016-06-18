package Factories;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */
public class MakeFactory {
    private static List<Make> makes;

    public static List<Make> GetMakes() {

        if (makes == null) {
            GenerateInitialMakes();
        }

        return makes;
    }

    private static void GenerateInitialMakes() {
        makes = new ArrayList<>();
        Make yamaha = new Make("Yamaha");
        yamaha.addModel(new Model("Crux", "Four-stroke", 250));
        yamaha.addModel(new Model("DT400", "Off road", 400));
        makes.add(yamaha);

        Make honda = new Make("Honda");
        honda.addModel(new Model("XR250R", "Off road", 250));
        honda.addModel(new Model("XR650L", "Dual purpose", 650));
        makes.add(honda);

        Make kawasaki = new Make("Kawasaki");
        kawasaki.addModel(new Model("Vulcan 1500 Drifter", "Cruiser", 1500));
        kawasaki.addModel(new Model("KE100", "Dual purpose", 100));
        kawasaki.addModel(new Model("KLX450R", "Off road", 450));
        makes.add(kawasaki);

        Make harleyDavidson = new Make("Harley-Davidson");
        harleyDavidson.addModel(new Model("FLT Tour Glide", "Touring", 1340));
        harleyDavidson.addModel(new Model("FLT Electra Glide", "Touring", 1340));
        harleyDavidson.addModel(new Model("WLA", "Military", 740));
        makes.add(harleyDavidson);
    }
}
