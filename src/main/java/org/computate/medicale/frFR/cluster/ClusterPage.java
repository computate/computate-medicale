package org.computate.medicale.frFR.cluster;

/**
 * Traduire: false
 **/
public class ClusterPage extends ClusterPageGen<ClusterGenPage> {

	@Override public void htmlBodyClusterGenPage() {
		if(getClass().getSimpleName().equals("ClusterPage"))
			super.htmlBodyClusterGenPage();
	}

	@Override public void htmlScript() {
	}
}
