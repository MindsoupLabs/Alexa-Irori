package net.mindsoup.irori.repositories;

import net.mindsoup.irori.models.IroriObject;
import net.mindsoup.irori.models.IroriStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Valentijn on 15-7-2017.
 */
public interface StatRepository extends CrudRepository<IroriStat, Long> {

	IroriStat findByObjectAndStat(@Param("object") IroriObject object, @Param("stat") String stat);
}
