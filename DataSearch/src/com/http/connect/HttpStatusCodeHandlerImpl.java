package com.http.connect;


import java.util.HashMap;

public class HttpStatusCodeHandlerImpl {
	private HashMap<Integer, String> status;
	private int code;

	public HttpStatusCodeHandlerImpl(int code) {
		this.initStatus();
	}

	public void discard(HttpConnect httpconnect) {
	}

	public void success(HttpConnect htttpconnect) {

	}

	public void block(HttpConnect httpconnect) {
	}

	public void redirect(HttpConnect httpconnect) {
		String redirecturl = httpconnect.getHttpresponseheader().getLocation()
				.get(0);
		httpconnect.setUrl(redirecturl);
		try {
			httpconnect.Connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pp(HttpConnect httpconnect) {
		/*
		 * 返回值 ：0表示丢弃，1表示成功，2表示阻塞等待,3重定向
		 */
		switch (code) {
		// 提供信息
		case 100:
			System.out.println("继续请求");
			this.discard(httpconnect);
			break;
		case 101:
			System.out.println("服务器同意协议");
			this.discard(httpconnect);
			break;
		// 成功
		case 200:
			System.out.println("请求成功");
			this.success(httpconnect);
			break;
		case 201:
			System.out.println("新的URL被创建");
			break;
		case 202:
			System.out.println("请求被接受");
			this.block(httpconnect);
			break;
		case 204:
			System.out.println("主体中没有内容");
			this.discard(httpconnect);
			break;
		// 重定向
		case 300:
			System.out.println();
			this.discard(httpconnect);
		case 301:
			System.out.println("服务器已不再使用所请求的URL");
			this.redirect(httpconnect);
			break;
		case 302:
			System.out.println("所请求的URL已被暂时删除");
			this.redirect(httpconnect);
			break;
		case 304:
			System.out.println("文档还没有被修改");
			this.discard(httpconnect);
			break;
		// 客户差错
		case 400:
			System.out.println("请求中有语法错误");
			this.discard(httpconnect);
			break;
		case 401:
			System.out.println("请求缺少适当的权限");
			this.discard(httpconnect);
			break;
		case 403:
			System.out.println("服务被拒绝");
			this.discard(httpconnect);
			break;
		case 404:
			System.out.println("文档未找到");
			this.discard(httpconnect);
			break;
		case 405:
			System.out.println("这个方法不被这个URL支持");
			this.discard(httpconnect);
			break;
		case 406:
			System.out.println("所请求的格式不被支持");
			this.discard(httpconnect);
			break;
		// 服务器差错
		case 500:
			System.out.println("服务器端有差错，如崩溃");
			this.discard(httpconnect);
			break;
		case 501:
			System.out.println("所请求的动作不能完成");
			this.discard(httpconnect);
			break;
		case 503:
			System.out.println("服务暂停不可");
			this.discard(httpconnect);
			break;
		default:
			System.out.println("error");
			this.discard(httpconnect);
			break;
		}
	}

	private void initStatus() {
		// 提供信息的状态码
		status.put(101, "Continue");
		status.put(102, "Switching");
		// 成功状态码
		status.put(200, "OK");
		status.put(201, "Created");
		status.put(202, "Accepted");
		status.put(204, "No content");
		// 重定向状态码
		status.put(301, "Moved permanently");
		status.put(302, "Moved temporarily");
		status.put(304, "Not modified");
		// 客户差错状态码
		status.put(400, "Bad request");
		status.put(401, "Unauthorized");
		status.put(403, "Forbidden");
		status.put(404, "Not found");
		status.put(405, "Method not allowed");
		status.put(406, "Not acceptable");
		// 服务器差错状态码
		status.put(500, "Internal server error");
		status.put(501, "Not implemented");
		status.put(503, "Service unavailabel");
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void HttpCodeHandler(HttpConnect httpconnect) {
		// TODO Auto-generated method stub
		/*
		 * 返回值 ：0表示丢弃，1表示成功，2表示阻塞等待,3重定向
		 */
		switch (code) {
		// 提供信息
		case 100:
			System.out.println("继续请求");
			this.discard(httpconnect);
			break;
		case 101:
			System.out.println("服务器同意协议");
			this.discard(httpconnect);
			break;
		// 成功
		case 200:
			System.out.println("请求成功");
			this.success(httpconnect);
			break;
		case 201:
			System.out.println("新的URL被创建");
			break;
		case 202:
			System.out.println("请求被接受");
			this.block(httpconnect);
			break;
		case 204:
			System.out.println("主体中没有内容");
			this.discard(httpconnect);
			break;
		// 重定向
		case 300:
			System.out.println();
			this.discard(httpconnect);
		case 301:
			System.out.println("服务器已不再使用所请求的URL");
			this.redirect(httpconnect);
			break;
		case 302:
			System.out.println("所请求的URL已被暂时删除");
			this.redirect(httpconnect);
			break;
		case 304:
			System.out.println("");
			this.discard(httpconnect);
			break;
		// 客户差错
		case 400:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 401:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 403:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 404:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 405:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 406:
			System.out.println("");
			this.discard(httpconnect);
			break;
		// 服务器差错
		case 500:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 501:
			System.out.println("");
			this.discard(httpconnect);
			break;
		case 503:
			System.out.println("");
			this.discard(httpconnect);
			break;
		default:
			System.out.println("error");
			this.discard(httpconnect);
			break;
		}
	}
}
