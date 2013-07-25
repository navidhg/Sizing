
public class Measurement {
	
	private int id;
	private int brandID;
	private String type;
	private int bustLower;
	private int bustHigher;
	private int waistLower;
	private int waistHigher;
	private int hipLower;
	private int hipHigher;
	private String UKsize;
	private String sizeLabel;
	private int relativeSize;
	
	public int getId() {
		return id;
	}
	public int getBrandID() {
		return brandID;
	}
	public String getType() {
		return type;
	}
	public int getBustLower() {
		return bustLower;
	}
	public int getBustHigher() {
		return bustHigher;
	}
	public int getWaistLower() {
		return waistLower;
	}
	public int getWaistHigher() {
		return waistHigher;
	}
	public int getHipLower() {
		return hipLower;
	}
	public int getHipHigher() {
		return hipHigher;
	}
	public String getUKsize() {
		return UKsize;
	}
	public String getSizeLabel() {
		return sizeLabel;
	}
	public int getRelativeSize() {
		return relativeSize;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setBustLower(int bustLower) {
		this.bustLower = bustLower;
	}
	public void setBustHigher(int bustHigher) {
		this.bustHigher = bustHigher;
	}
	public void setWaistLower(int waistLower) {
		this.waistLower = waistLower;
	}
	public void setWaistHigher(int waistHigher) {
		this.waistHigher = waistHigher;
	}
	public void setHipLower(int hipLower) {
		this.hipLower = hipLower;
	}
	public void setHipHigher(int hipHigher) {
		this.hipHigher = hipHigher;
	}
	public void setUKsize(String uKsize) {
		UKsize = uKsize;
	}
	public void setSizeLabel(String sizeLabel) {
		this.sizeLabel = sizeLabel;
	}
	public void setRelativeSize(int relativeSize) {
		this.relativeSize = relativeSize;
	}
	
	public Measurement() {
		this.id = -1;
		this.brandID = -1;
		this.type = "";
		this.bustLower = -1;
		this.bustHigher = -1;
		this.waistLower = -1;
		this.waistHigher = -1;
		this.hipLower = -1;
		this.hipHigher = -1;
		this.UKsize = "";
		this.sizeLabel = "";
		this.relativeSize = -1;
	}
	
	public Measurement(int id, int brandID, String type, int bustLower,
			int bustHigher, int waistLower, int waistHigher, int hipLower,
			int hipHigher, String uKsize, String sizeLabel, int relativeSize) {
		super();
		this.id = id;
		this.brandID = brandID;
		this.type = type;
		this.bustLower = bustLower;
		this.bustHigher = bustHigher;
		this.waistLower = waistLower;
		this.waistHigher = waistHigher;
		this.hipLower = hipLower;
		this.hipHigher = hipHigher;
		UKsize = uKsize;
		this.sizeLabel = sizeLabel;
		this.relativeSize = relativeSize;
	}
	@Override
	public String toString() {
		return "Measurement [id=" + id + ", brandID=" + brandID + ", type="
				+ type + ", bustLower=" + bustLower + ", bustHigher="
				+ bustHigher + ", waistLower=" + waistLower + ", waistHigher="
				+ waistHigher + ", hipLower=" + hipLower + ", hipHigher="
				+ hipHigher + ", UKsize=" + UKsize + ", sizeLabel=" + sizeLabel
				+ ", relativeSize=" + relativeSize + "]";
	}	
}
