package com.component.redis;

public class JedisConfig {

	private String ip;
	
	private int port;
	
	private int maxTotal;
	
	private int maxIdel;
	
	private int minIdel;
	
	private long timeout;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdel() {
		return maxIdel;
	}

	public void setMaxIdel(int maxIdel) {
		this.maxIdel = maxIdel;
	}

	public int getMinIdel() {
		return minIdel;
	}

	public void setMinIdel(int minIdel) {
		this.minIdel = minIdel;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JedisConfig [ip=");
		builder.append(ip);
		builder.append(", port=");
		builder.append(port);
		builder.append(", maxTotal=");
		builder.append(maxTotal);
		builder.append(", maxIdel=");
		builder.append(maxIdel);
		builder.append(", minIdel=");
		builder.append(minIdel);
		builder.append(", timeout=");
		builder.append(timeout);
		builder.append("]");
		return builder.toString();
	}
	
}
