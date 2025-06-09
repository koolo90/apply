package com.brocode.apply.repositories;

import com.brocode.apply.buissness.model.Applier;
import org.springframework.data.repository.CrudRepository;

public interface ApplierRepository extends CrudRepository<Applier, String> {
    Applier findByUsername(String world);
}
