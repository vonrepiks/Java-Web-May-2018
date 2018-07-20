package tubes.repositories;

import tubes.models.Tube;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TubeRepository {
    private List<Tube> tubes;

    public TubeRepository() {
        this.tubes = new ArrayList<>();
        this.seedTubes();
    }

    private void seedTubes() {
        this.tubes.add(new Tube("Feel it Steel", "Some cool new Song…", 5, "Prakash"));
        this.tubes.add(new Tube("Despacito", "No words … Just … No!", 250, "Stamat"));
        this.tubes.add(new Tube("Gospodari Na Efira – ep. 25", "Mnogo smqh imashe tuka…", 3, "Trichko"));
    }

    public List<Tube> getTubes() {
        return Collections.unmodifiableList(this.tubes);
    }

    public void addTube(Tube tube) {
        this.tubes.add(tube);
    }
}
