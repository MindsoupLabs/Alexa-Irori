package net.mindsoup.irori.repositories;

import net.mindsoup.irori.models.IroriAlias;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Valentijn on 23-7-2017
 */
public interface AliasRepository  extends CrudRepository<IroriAlias, Long> {
	IroriAlias findByAlias(@Param("alias") String alias);
}
