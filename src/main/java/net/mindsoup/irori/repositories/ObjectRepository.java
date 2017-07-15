package net.mindsoup.irori.repositories;

import net.mindsoup.irori.models.IroriObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Valentijn on 15-7-2017.
 */
public interface ObjectRepository extends CrudRepository<IroriObject, Long> {
	IroriObject findByName(@Param("name") String name);
}
