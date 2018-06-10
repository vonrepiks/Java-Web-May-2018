package org.softuni.fdmc.database.repositories;

import org.softuni.fdmc.database.entities.Cat;
import org.softuni.fdmc.database.entities.User;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CatRepository extends BaseRepository implements CrudRepository<Cat> {

    @Override
    public void create(Cat cat) throws RollbackException {
        super.executeAction(result -> {
            super.entityManager.persist(cat);
        });
    }

    @Override
    public List<Cat> findAll() {
        List<Cat> cats = (List<Cat>) super.executeAction(result -> {
            result.setResult(super.entityManager.createNativeQuery("SELECT * FROM cats", Cat.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toList()));

        }).getResult();

        return cats;
    }

    @Override
    public Cat findById(String id) {
        Cat cat = (Cat) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT c.id, c.name, c.breed, c.color, c.number_of_legs, c.user_id FROM cats AS c WHERE id = ?");

            nativeQuery.setParameter(1, id);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            Cat searchedCat = new Cat();
            searchedCat.setId((String) results[0]);
            searchedCat.setName((String) results[1]);
            searchedCat.setBreed((String) results[2]);
            searchedCat.setColor((String) results[3]);
            searchedCat.setNumberOfLegs((Integer) results[4]);
            String creatorId = (String) results[5];
            User creator = new User();
            creator.setId(creatorId);
            searchedCat.setCreator(creator);

            result.setResult(searchedCat);
        }).getResult();

        return cat;
    }

    @Override
    public void update(Cat cat) {
        super.executeAction(result -> {
            super.entityManager.merge(cat);
        });
    }

    public Cat findByName(String name) {
        Cat cat = (Cat) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT  c.id, c.name, c.breed, c.color, c.number_of_legs, c.views, c.user_id FROM cats AS c WHERE name = ?");

            nativeQuery.setParameter(1, name);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            Cat searchedCat = new Cat();
            searchedCat.setId((String) results[0]);
            searchedCat.setName((String) results[1]);
            searchedCat.setBreed((String) results[2]);
            searchedCat.setColor((String) results[3]);
            searchedCat.setNumberOfLegs((Integer) results[4]);
            searchedCat.setViews((Integer) results[5]);
            String creatorId = (String) results[6];
            User creator = new User();
            creator.setId(creatorId);
            searchedCat.setCreator(creator);

            result.setResult(searchedCat);
        }).getResult();

        return cat;
    }


    public List<Cat> findAllByCreatorId(String id) {
        List<Cat> cats = (List<Cat>) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT * FROM cats WHERE cats.user_id = ?", Cat.class);

            nativeQuery.setParameter(1, id);

            result.setResult(nativeQuery.getResultList().stream().collect(Collectors.toList()));
        }).getResult();

        return cats;
    }

    public List<Cat> findAllByCreatorIdSortedByViews(String id) {
        List<Cat> cats = (List<Cat>) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT * FROM cats WHERE cats.user_id = ?", Cat.class);

            nativeQuery.setParameter(1, id);

            List<Cat> allCatsByCreator = (List<Cat>) nativeQuery.getResultList().stream().collect(Collectors.toList());

            result.setResult(allCatsByCreator.stream().sorted(Comparator.comparing(Cat::getViews, Comparator.reverseOrder())).collect(Collectors.toList()));
        }).getResult();

        return cats;
    }

}
