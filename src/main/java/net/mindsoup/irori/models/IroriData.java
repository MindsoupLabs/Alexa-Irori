package net.mindsoup.irori.models;

import java.util.List;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class IroriData {

	private IroriObject object;
	private List<IroriStat> stats;

	public IroriObject getObject() {
		return object;
	}

	public void setObject(IroriObject object) {
		this.object = object;
	}

	public List<IroriStat> getStats() {
		return stats;
	}

	public void setStats(List<IroriStat> stats) {
		this.stats = stats;
	}
}
