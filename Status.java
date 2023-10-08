import java.time.LocalDateTime;

public class Status {
	
	private long id;
	private int no;
	private short areaId;
	private boolean valid;
	private String version;
	private LocalDateTime currentTime;
	
	Status(long id, int no, short areaId, String version){
		this.id = id;
		this.no = no;
		this.areaId = areaId;
		//this.valid = valid;
		this.version = version;
		this.currentTime = LocalDateTime.now();
	}
	public long getId() {
		return id;
	}
	public int getNo() {
		return no;
	}
	public short getAreaId() {
		return areaId;
	}
	public boolean isValid() {
		return valid;
	}
	public String getVersion() {
		return version;
	}
	public LocalDateTime getCurrentTime() {
		return currentTime;
	}
	public void printData(){
		System.out.println("id:"+this.id+" no:"+this.no+" areaId:"+this.areaId+" valid:"+this.valid+" version:"+this.version+" currentTime:"+ this.currentTime);
		
	}
}
