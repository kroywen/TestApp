package com.fourpay.testapp.data.network;

import com.fourpay.testapp.data.model.Transaction;

import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

	String BASE_URL = "https://test.4pay.com";

	@GET("/history")
	Observable<List<Transaction>> getHistory(
			@Query("userid") String userId,
			@Query("datefrom") String dateFrom,
			@Query("dateTo") String dateTo
	);


	class Creator {

		public static ApiService newApiService() {
			try {
				HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
				loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

				TrustManager[] trustAllCerts = new TrustManager[]{
						new X509TrustManager() {
							@Override
							public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
							@Override
							public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
							@Override
							public java.security.cert.X509Certificate[] getAcceptedIssuers() {
								return new java.security.cert.X509Certificate[]{};
							}
						}
				};

				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
				SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

				OkHttpClient okHttpClient = new OkHttpClient.Builder()
						.connectTimeout(30, TimeUnit.SECONDS)
						.readTimeout(30, TimeUnit.SECONDS)
						.addInterceptor(loggingInterceptor)
						.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
						.hostnameVerifier((hostname, session) -> true)
						.build();

				Retrofit retrofit = new Retrofit.Builder()
						.baseUrl(BASE_URL)
						.client(okHttpClient)
						.addConverterFactory(GsonConverterFactory.create())
						.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
						.build();

				return retrofit.create(ApiService.class);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}

	}

}