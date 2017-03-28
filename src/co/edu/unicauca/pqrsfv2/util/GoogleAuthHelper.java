package co.edu.unicauca.pqrsfv2.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * A helper class for Google's OAuth2 authentication API.
 * @version 20130224
 * @author Matyas Danter
 */
@Stateless
@LocalBean
public class GoogleAuthHelper {

	/**
	 * Please provide a value for the CLIENT_ID constant before proceeding, set this up at https://code.google.com/apis/console/
	 */
	private String CLIENT_ID = "706330254364-ehhurbjl95b0bvj5qo65bp2pjsclbjic.apps.googleusercontent.com";
	private String CLIENT_SECRET = "BXjJVoemqM2OCKwBIPjwBBpI";
	private String CALLBACK_URI = "http://localhost:8000/PQRSFv2/admin/principal.xhtml";
	private Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
	private String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private JsonFactory JSON_FACTORY = new JacksonFactory();
	private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private String stateToken;
	
	private GoogleAuthorizationCodeFlow flow;
	
	/**
	 * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE 
	 */
	public GoogleAuthHelper() {
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
				JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
		
		generateStateToken();
	}

	/**
	 * Builds a login URL based on client ID, secret, callback URI, and scope 
	 */
	public String buildLoginUrl() {
		
		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		
		return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
	}
	
	/**
	 * Generates a secure state token 
	 */
	private void generateStateToken(){
		
		SecureRandom sr1 = new SecureRandom();
		
		stateToken = "google;"+sr1.nextInt();
		
	}
	
	/**
	 * Accessor for state token
	 */
	public String getStateToken(){
		return stateToken;
	}
	
	/**
	 * Expects an Authentication Code, and makes an authenticated request for the user's profile information
	 * @return JSON formatted user profile information
	 * @param authCode authentication code provided by google
	 */
	public String getUserInfoJson(String authCode) throws IOException {

		GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		Credential credential = flow.createAndStoreCredential(response, null);
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		GenericUrl url = new GenericUrl(USER_INFO_URL);
		HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		String jsonIdentity = request.execute().parseAsString();

		return jsonIdentity;

	}

	public String getCallbackUri() {
		return CALLBACK_URI;
	}
	
}
