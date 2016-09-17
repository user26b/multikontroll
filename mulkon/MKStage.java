import java.util.ArrayList;

public class MKStage {
	ArrayList<MKFixture> allFixtures = new ArrayList<MKFixture>();

	public void add_fixture(MKFixture newfixture) {
		this.allFixtures.add(newfixture);
	}

	public void update_all() {
		for(MKFixture f : this.allFixtures) {
			f.send_values();
		} 
	}

	public MKFixture get_fixture(String searchname) {
		for(int i=0; i < this.allFixtures.size(); i++) {
			if(this.allFixtures.get(i).name.equals(searchname)) {
				return this.allFixtures.get(i);
			}
		}

		return this.allFixtures.get(0);
	}
}