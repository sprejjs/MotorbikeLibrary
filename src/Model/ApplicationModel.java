package Model;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */
public class ApplicationModel {
    private static HashMap<String, Make> makes;

    public static List<Make> GetMakes() {
        return new ArrayList<>(GetMakesAsHashMap().values());
    }

    public static boolean AddModel(String make, Model model) {
        Make makeToAddTo = GetMakesAsHashMap().get(make);

        return makeToAddTo != null && makeToAddTo.addModel(model);
    }

    private static HashMap<String, Make> GetMakesAsHashMap() {
        return makes;
    }

    public static void CreateModel() {
        makes = new HashMap<>();
        Make yamaha = new Make("Yamaha");
        yamaha.addModel(new Model("Crux", "Four-stroke", 250));
        yamaha.addModel(new Model("DT400", "Off road", 400));
        makes.put(yamaha.getName(), yamaha);

        Make honda = new Make("Honda");
        honda.addModel(new Model("XR250R", "Off road", 250));
        honda.addModel(new Model("XR650L", "Dual purpose", 650));
        makes.put(honda.getName(), honda);

        Make kawasaki = new Make("Kawasaki");
        kawasaki.addModel(new Model("Vulcan 1500 Drifter", "Cruiser", 1500));
        kawasaki.addModel(new Model("KE100", "Dual purpose", 100));
        kawasaki.addModel(new Model("KLX450R", "Off road", 450));
        makes.put(kawasaki.getName(), kawasaki);

        Make harleyDavidson = new Make("Harley-Davidson");
        harleyDavidson.addModel(new Model("FLT Tour Glide", "Touring", 1340));
        harleyDavidson.addModel(new Model("FLT Electra Glide", "Touring", 1340));
        harleyDavidson.addModel(new Model("WLA", "Military", 740));
        makes.put(harleyDavidson.getName(), harleyDavidson);
    }
}
