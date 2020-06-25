package org.computate.medicale.enUS.cluster;

/**
 **/
public class ClusterPage extends ClusterPageGen<ClusterGenPage> {

	@Override public void htmlBodyClusterGenPage() {
		if(getClass().getSimpleName().equals("ClusterPage"))
			super.htmlBodyClusterGenPage();
	}

	@Override public void htmlScript() {
	}
}
