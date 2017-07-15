package net.mindsoup.irori.services;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.models.IroriStat;

/**
 *
 */
public interface IroriService {

	IroriStat getStat(StatRequest statRequest);
}
