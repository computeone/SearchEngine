package com.http.connect;

public interface HttpStatusCodeHandler {
	public void HttpCodeHandler(HttpConnect httpconnect);

	public void discard(HttpConnect httpconnect);

	public void success(HttpConnect httpconnect);

	public void block(HttpConnect httpconnect);

	public void redirect(HttpConnect httpconnect);

}
