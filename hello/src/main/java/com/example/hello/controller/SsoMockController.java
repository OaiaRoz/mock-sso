/**
 * 
 */
package com.example.hello.controller;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/sso")
public class SsoMockController extends HttpServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SsoMockController.class);
	
	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.sendRedirect(req.getContextPath() + "/redirected");
		
		// ---
		
		// RequestDispatcher dispatcher = req.getRequestDispatcher("/forwarded");
        // dispatcher.forward(req, resp);
    }
	
	/**
	 * Get the HTTPS without verification of the SSL
	 * 
	 * @param url
	 * @return
	 * 
	 * for https://expired.badssl.com, 
	 * expected response:
	 * ERROR: Could not extract response: no suitable HttpMessageConverter found for response type [class java.lang.Object] and content type [text/html]
	 * 
	 */
	@GetMapping(path = "")
	public String getNoSSL(
			@RequestParam(name = "from", defaultValue = "http://localhost/api/service/auth/login") String from,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		//LOGGER.info("No verification SSL test!");
		String URL = from;
		LOGGER.info("Send to URL :: " + URL);
		
		// Object obj = restTemplate().getForObject(URL, Object.class);
		
		/*
			

		try {
			CloseableHttpClient httpClient = null;
			if (URL.contains("https")) {
				httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			} 
			else {
				httpClient = HttpClients.createDefault();
			}
			
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			
			// https://springbootdev.com/2017/11/21/spring-resttemplate-exchange-method/
			// http://techie-mixture.blogspot.com/2016/07/spring-rest-template-sending-post.html
			// http://techie-mixture.blogspot.com/2016/07/sending-multipart-files-using-spring.html
			// 
			//setting up the request headers
	        HttpHeaders requestHeaders = new HttpHeaders();
	        requestHeaders.setLocation(new URI(from));
	        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        
	        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	        map.add("from", "");
	        map.add("SAMLResponse", "PH...pSZXNw...zZT4=");
	        
			// request entity is created with request body and headers
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, requestHeaders);

			//new RestTemplate(requestFactory).exchange(URL, HttpMethod.POST, null, String.class);
			// new RestTemplate(requestFactory).exchange(URL, HttpMethod.POST, requestEntity, Object.class);
			
			//Object response = new RestTemplate(requestFactory).exchange(URL, HttpMethod.POST, requestEntity, String.class);
			
			
			//ResponseEntity<String> response = restTemplate().exchange(URL, HttpMethod.POST, requestEntity, String.class);
			
			
			//resp.sendRedirect(URL);
			
			//RequestDispatcher dispatcher = req.getRequestDispatcher(URL);
	        //dispatcher.forward(req, resp);
			
			
			
			
			
//			ObjectMapper objMapper = new ObjectMapper();
//			String strJson = null;
//			try {
//				strJson = objMapper.writeValueAsString(response);
//			} catch (JsonProcessingException e) {
//				LOGGER.info("JsonProcessingException :: " + e.getMessage());
//			} catch (IOException e) {
//				LOGGER.info("IOException :: " + e.getMessage());
//			}
//			
//			LOGGER.info("response (JSON) :: " + strJson);
			
			
			//LOGGER.info("response (JSON) :: " + response);
			//LOGGER.info("response (JSON) :: " + response.getBody());
			
			

		} catch (Exception e) {
			LOGGER.error("Exception :: " + e.getMessage());
		}
		return;
		*/

		Map<String, String> params = new HashMap<>();
		params.put("SAMLResponse", "PHNhbWxw...ZXNwb25zZT4=");
		params.put("from", "");
		
		return buildHtml("login", URL, params);

	}
	

	
    /**
     * Generate an HTML page that does a browser redirect via a POST.  The HTML document uses Javascript to automatically
     * submit a FORM post when loaded.
     *
     * This is similar to what the SAML Post Binding does.
     *
     * Here's an example
     *
     * <pre>
     * {@code
     * <HTML>
     *   <HEAD>
     *       <TITLE>title</TITLE>
     *   </HEAD>
     *   <BODY Onload="document.forms[0].submit()">
     *       <FORM METHOD="POST" ACTION="actionUrl">
     *           <INPUT TYPE="HIDDEN" NAME="param" VALUE="value"/>
     *           <NOSCRIPT>
     *               <P>JavaScript is disabled. We strongly recommend to enable it. Click the button below to continue.</P>
     *               <INPUT TYPE="SUBMIT" VALUE="CONTINUE"/>
     *           </NOSCRIPT>
     *       </FORM>
     *   </BODY>
     * </HTML>
     * }
     * </pre>
     *
     * @param title may be null.  Just the title of the HTML document
     * @param actionUrl URL to redirect to
     * @param params must be encoded so that they can be placed in an HTML form hidden INPUT field value
     * @return
     */
    public String buildHtml(String title, String actionUrl, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        builder.append("<HTML>")
                .append("<HEAD>");
        if (title != null) {
            builder.append("<TITLE>SAML HTTP Post Binding</TITLE>");
        }
        builder.append("</HEAD>")
                .append("<BODY Onload=\"document.forms[0].submit()\">")

                .append("<FORM METHOD=\"POST\" ACTION=\"").append(actionUrl).append("\">");
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.append("<INPUT TYPE=\"HIDDEN\" NAME=\"").append(param.getKey()).append("\"").append(" VALUE=\"").append(param.getValue()).append("\"/>");
        }


        builder.append("<NOSCRIPT>")
                .append("<P>JavaScript is disabled. We strongly recommend to enable it. Click the button below to continue.</P>")
                .append("<INPUT TYPE=\"SUBMIT\" VALUE=\"CONTINUE\" />")
                .append("</NOSCRIPT>")

                .append("</FORM></BODY></HTML>");

        return builder.toString();
    }

	
}
