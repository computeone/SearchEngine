/**
 * 
 */
package com.http.connect.test;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.http.connect.HttpConnect;
import com.http.connect.HttpConnectPool;
import com.http.connect.NOHttpConnectException;

/**
 * @author niubaisui
 * 
 */
public class HttpConnectPoolTest {

	/**
	 * @throws java.lang.Exception
	 */
	private static HttpConnectPool httpconnectpool;
	private HttpConnect httpconnect;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("start");

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		httpconnectpool = HttpConnectPool.GetHttpConnectPool(10);
		System.out.println("setup:" + httpconnectpool.getPOOLSIZE());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("teardown:");

	}

	/**
	 * Test method for {@link com.http.connect.HttpConnectPool#getPOOLSIZE()}.
	 * 
	 */
	@Test
	public void testGetPOOLSIZE() throws Exception {
		httpconnect = HttpConnectPool.getHttpConnect();
		Assert.assertTrue(httpconnectpool.getPOOLSIZE() == 9);
		HttpConnectPool.releaseHttpConnect(httpconnect);
		Assert.assertTrue(httpconnectpool.getPOOLSIZE() == 10);
	}

	/**
	 * Test method for {@link com.http.connect.HttpConnectPool#getHttpConnect()}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHttpConnect() throws Exception {
		httpconnect = HttpConnectPool.getHttpConnect();
		Assert.assertNotNull(httpconnect);
		HttpConnectPool.releaseHttpConnect(httpconnect);
	}

	/**
	 * Test method for
	 * {@link com.http.connect.HttpConnectPool#releaseHttpConnect(com.http.connect.HttpConnect)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test(expected = NOHttpConnectException.class)
	public void testReleaseHttpConnect() throws Exception {
		httpconnect = HttpConnectPool.getHttpConnect();
		HttpConnectPool.releaseHttpConnect(httpconnect);
		System.out.println(httpconnectpool.getPOOLSIZE());
		Assert.assertTrue(httpconnectpool.getPOOLSIZE() == 10);
		for (int i = 0; i < 10; i++) {
			HttpConnectPool.getHttpConnect();
		}
		Assert.assertTrue(httpconnectpool.getPOOLSIZE() == 0);
		HttpConnectPool.getHttpConnect();
		Assert.assertTrue(httpconnectpool.getPOOLSIZE() == 0);
	}

	/**
	 * Test method for
	 * {@link com.http.connect.HttpConnectPool#GetHttpConnectPool(int)}.
	 */
	@Test
	public void testGetHttpConnectPool() {
	}

}
