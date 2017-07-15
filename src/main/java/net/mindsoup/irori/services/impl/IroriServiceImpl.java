package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.models.IroriStats;
import net.mindsoup.irori.services.IroriService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IroriServiceImpl implements IroriService {

	@Override
	public IroriStats getStat(StatRequest statRequest) {
		IroriStats response = new IroriStats("buckler", "armor class", "1");
		return response;
	}
}
