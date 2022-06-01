/**
 * 
 */
package com.example.hello.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.hello.pojo.ExtendableBean;
import com.example.hello.pojo.ImsAttachmentInfo;
import com.example.hello.pojo.ImsMetadata;
import com.example.hello.pojo.ImsResponse;
import com.example.hello.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author danfundatureanu
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/")
public class SampleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	RestTemplate restTemplate;
	
//	@Bean
//	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
//				.build();
//
//		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//
//		requestFactory.setHttpClient(httpClient);
//		
//		RestTemplate restTemplate = new RestTemplate(requestFactory);
//		return restTemplate;
//	}
	
	
	@GetMapping(path = "")
	public ResponseEntity<?> getAll()
	{
		LOGGER.info("Test endpoint");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * 
	 * 
	 * for https://expired.badssl.com, 
	 * expected response:
	 * ERROR: I/O error on GET request for "https://expired.badssl.com": PKIX path validation failed: 
	 *   java.security.cert.CertPathValidatorException: validity check failed; 
	 *   nested exception is javax.net.ssl.SSLHandshakeException: PKIX path validation failed: 
	 *   java.security.cert.CertPathValidatorException: validity check failed
	 * 
	 */
	@GetMapping(path = "v1/test", produces = "application/json")
	public ResponseEntity<?> getImsTestStatus(@RequestParam(name = "url", defaultValue = "https://gturnquist-quoters.cfapps.io/api/random") String url)
	{
		LOGGER.info("test status");
		String URL = url;
		
		
		String str = "12121999";
//		{
//			// String str = "2016-03-04";
//			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyy");
//			LocalDateTime dateTime = LocalDateTime.parse(str, formatter);	
//			
//			LOGGER.info("String := " + str);
//			LOGGER.info("dateTime :+ " + dateTime);
//		}
		{
			
			DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMddyyyy");
			
			LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(str, FORMATTER), LocalTime.MIDNIGHT);
			LOGGER.info("String := " + str);
			LOGGER.info("dateTime :+ " + dateTime);
			


		}
		
		
		try {
			Object obj = restTemplate.getForObject(URL, Object.class);
			return new ResponseEntity(obj, HttpStatus.OK);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity("ERROR: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

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
	@GetMapping(path = "v1/nossl", produces = "application/json")
	public ResponseEntity<?> getNoSSL(@RequestParam(name = "url", defaultValue = "https://gturnquist-quoters.cfapps.io/api/random") String url)
	{
		LOGGER.info("No verification SSL test!");
		String URL = url;
		
		try {
			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.build();
		    HttpComponentsClientHttpRequestFactory requestFactory 
		      = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    
			ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(URL, HttpMethod.GET, null, String.class);
			
			return response;
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity("ERROR: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	
	@PostMapping(path = "uploadFile", produces = "application/json")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		
        fileStorageService.uploadFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
        
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);

        return response;
    }
	
	@PostMapping(path = "new-ims-meta", produces = "application/json")
	public ImsMetadata createNewObjectWithImage(@RequestBody ImsMetadata imsMetadata) throws JsonProcessingException
	{
		LOGGER.info("Metadata :: " + imsMetadata.toString());
		LOGGER.info("Metadata :: " + imsMetadata.toJson());
		return imsMetadata;
	}
	
	@PostMapping(path = "service-ims", produces = "application/json")
	public ImsResponse createNewObjectWithImage(
			@RequestParam(name = "attachmentInfo") String strMetadata, 
			@RequestParam(name = "file") MultipartFile file) throws Exception, JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		fileStorageService.uploadFile(file);
		LOGGER.info("IMS-Metadata :: " + strMetadata);
		
		// "{\"IMDS_FILE_SIZE\":\"123\",\"IMDS_DOC_TYPE\":\"1\",\"IMDS_USERNAME\":\"admin\",\"IMDS_APP\":\"IMDS\"}"
		// {"IMDS_FILE_SIZE":"123","IMDS_DOC_TYPE":"1","IMDS_USERNAME":"admin","IMDS_APP":"IMDS"}
		
		// ImsMetadata imsMetadata = objectMapper.readValue(strMetadata, ImsMetadata.class);
		ImsAttachmentInfo imsAttachmentInfo = objectMapper.readValue(strMetadata, ImsAttachmentInfo.class);
		LOGGER.info("IMS-Metadata (JSON) :: " + imsAttachmentInfo.toJson());

		// ResponseEntity<ImsMetadata> response = new ResponseEntity<>(imsMetadata, HttpStatus.OK);
		
		Random rand = new Random();
		int rand_int1 = rand.nextInt(1000);
		
		ImsResponse response = new ImsResponse("SUCCESS", "", Integer.toString(rand_int1));
		
		return response;
	}
	
	/*
		   HashMap <String, String> metadata = new HashMap <String, String> ();
           byte[] data = "hello world".getBytes();
           metadata.put("IMDS_FILE_SIZE", Integer.toString(data.length));
           metadata.put("IMDS_DOC_TYPE", "1");
           metadata.put("IMDS_USERNAME","TEST User");
           metadata.put("IMDS_APP", "TEST");
	 */
	
}
