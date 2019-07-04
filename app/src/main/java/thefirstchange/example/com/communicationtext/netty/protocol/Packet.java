package thefirstchange.example.com.communicationtext.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    @JSONField(serialize = false)
    public abstract int getCommand();


	public Byte getVersion() {
		return version;
	}


	public void setVersion(Byte version) {
		this.version = version;
	}

	public Packet(){};
    
}
