package io.ucandoit.mtg.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import io.ucandoit.mtg.exception.TechnicalErrorCode;
import io.ucandoit.mtg.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUtils {
	/**
	 * default socket timeout (in millisecond)
	 */
	private static final int SO_TIMEOUT = 120 * 1000;
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String XXX_FORM_URLENCODED = "application/x-www-form-urlencoded";
	private static final String HTTPS = "https";

	public static InputStream post(String url, Map<String, Object> params, int timeout) throws TechnicalException {
		try {
			CloseableHttpClient httpClient = getHttpClient(isHttps(url), timeout);

			HttpPost httpPost = new HttpPost(buildURI(url, params));
			httpPost.addHeader(CONTENT_TYPE, XXX_FORM_URLENCODED + "; charset=utf-8");

			CloseableHttpResponse response = httpClient.execute(httpPost);
			return dealResponse(response);
		} catch (Exception e) {
			throw new TechnicalException(TechnicalErrorCode.HTTP_CLIENT, e);
		}
	}

	public static InputStream post(String url, Map<String, Object> params) {
		return post(url, params, SO_TIMEOUT);
	}

	public static InputStream get(String url, Map<String, Object> params) {
		try {
			boolean isHttps = url.toLowerCase().startsWith(HTTPS);
			CloseableHttpClient httpClient = getHttpClient(isHttps, SO_TIMEOUT);

			HttpGet httpGet = new HttpGet(buildURI(url, params));

			CloseableHttpResponse response = httpClient.execute(httpGet);
			return dealResponse(response);
		} catch (Exception e) {
			throw new TechnicalException(TechnicalErrorCode.HTTP_CLIENT, e);
		}
	}

	public static InputStream get(String url) {
		return get(url, null);
	}

	/**
	 * Construct the http client.
	 * 
	 * @param isHttps
	 *            true if the request protocol is https
	 * @param timeout
	 *            socket timeout
	 * @return the http client
	 */
	private static CloseableHttpClient getHttpClient(boolean isHttps, int timeout) {
		try {
			RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(timeout).build();
			ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
			if (isHttps) {
				// use the TrustSelfSignedStrategy to allow Self Signed Certificates
				SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy())
						.build();
				// disable hostname verification.
				HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
				// create an SSL Socket Factory to use the SSLContext with the trust self signed
				// certificate strategy and allow all hosts verifier.
				SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext,
						allowAllHosts);
				Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
						.<ConnectionSocketFactory>create().register("https", connectionFactory).build();
				PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
						socketFactoryRegistry);
				connectionManager.setDefaultConnectionConfig(connectionConfig);
				return HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig)
						.setConnectionManager(connectionManager).build();
			} else {
				PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
				connectionManager.setDefaultConnectionConfig(connectionConfig);
				return HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig)
						.setConnectionManager(connectionManager).build();
			}
		} catch (Exception e) {
			throw new TechnicalException(TechnicalErrorCode.HTTP_CLIENT, e);
		}
	}

	private static boolean isHttps(String url) {
		return url.toLowerCase().startsWith(HTTPS);
	}

	/**
	 * Build the url with request parameters.
	 * 
	 * @param url
	 *            base url
	 * @param params
	 *            request parameters
	 * @return the full url
	 * @throws URISyntaxException
	 */
	private static URI buildURI(String url, Map<String, Object> params) throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			for (String param : params.keySet()) {
				String value = params.get(param).toString();
				if (log.isDebugEnabled()) {
					log.debug(String.format("%1$s = %2$s", param, value));
				}
				uriBuilder.addParameter(param, value);
			}
		}
		return uriBuilder.build();
	}

	private static InputStream dealResponse(CloseableHttpResponse response)
			throws UnsupportedOperationException, IOException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200 || statusCode == 201) {
			return response.getEntity().getContent();
		} else {
			String errorMessage = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
			log.error("Erreur lors de l'appel : {}", response.getStatusLine());
			throw new TechnicalException(TechnicalErrorCode.HTTP_CLIENT, statusCode, errorMessage);
		}
	}
}
