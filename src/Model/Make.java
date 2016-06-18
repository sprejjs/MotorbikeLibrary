package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */
public class Make {
    private String name;
    private List<Model> models;

    public Make(String name) {
        this.name = name;
        this.models = new ArrayList<>();
    }

    public void addModel(Model model) {
        this.models.add(model);
    }
}
