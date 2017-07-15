package net.mindsoup.irori.services;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.models.IroriStats;

/**
 *
 */
public interface IroriService {

	IroriStats getStat(StatRequest statRequest);
}
