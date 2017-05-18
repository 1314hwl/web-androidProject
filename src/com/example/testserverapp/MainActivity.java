package com.example.testserverapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String url = "http://10.0.2.2:8080/FirstServer/firstlogin";
	private static final String url1 = "http://10.0.2.2:8080/FirstServer/LoginServlet2";
	private static final String url2 = "http://10.0.2.2:8080/FirstServer/LoginServlet3";
	private static final String url3 = "http://10.0.2.2:8080/FirstServer/LoginServlet4";
	private Button loginBtn, loginXutilsBtn, loginHibernateBtn, loginSpringBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		x.Ext.init(getApplication());
		x.Ext.setDebug(true);
		loginBtn = (Button) findViewById(R.id.test_btn);
		loginXutilsBtn = (Button) findViewById(R.id.test_xutils_btn);
		loginHibernateBtn = (Button) findViewById(R.id.test_hibernate_btn);
		loginSpringBtn = (Button) findViewById(R.id.test_spring_btn);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginServer();

			}
		});
		loginXutilsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xUtilsLogin();
			}
		});
		loginHibernateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xUtilsHiberLogin();
			}
		});
		loginSpringBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xUtilsSpringLogin();
			}
		});
	}

	public void xUtilsHiberLogin() {

		RequestParams p = new RequestParams(url2);
		p.addBodyParameter("name", "test");
		p.addBodyParameter("password", "111111");
		p.addBodyParameter("wd", "xUtils");
		x.http().post(p, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				loginHibernateBtn.setText("XutilsHibernateµÇÂ½Ê§°Ü");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				loginHibernateBtn.setText("XutilsHibernateµÇÂ½³É¹¦");
			}
		});
	}

	public void xUtilsSpringLogin() {

		RequestParams p = new RequestParams(url3);
		p.addBodyParameter("name", "test");
		p.addBodyParameter("password", "111111");
		p.addBodyParameter("wd", "xUtils");
		x.http().post(p, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				loginSpringBtn.setText("XutilsSpirngµÇÂ½Ê§°Ü");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				loginSpringBtn.setText("XutilsSpirngµÇÂ½³É¹¦");
			}
		});
	}

	public void xUtilsLogin() {

		RequestParams p = new RequestParams(url1);
		p.addBodyParameter("name", "test");
		p.addBodyParameter("password", "111111");
		p.addBodyParameter("wd", "xUtils");
		x.http().post(p, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				loginXutilsBtn.setText("XutilsµÇÂ½Ê§°Ü");
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				loginXutilsBtn.setText("XutilsµÇÂ½³É¹¦");
			}
		});
	}

	private void loginServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BasicHttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 5 * 1000);
				HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
				HttpPost post = new HttpPost(url);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("name", "test"));
				nvps.add(new BasicNameValuePair("password", "111111"));
				try {
					post.setEntity(new UrlEncodedFormEntity(nvps));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				try {
					HttpClient client = new DefaultHttpClient(httpParams);
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() == 200) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loginBtn.setText("µÇÂ½³É¹¦");

							}
						});

						String responseMsg = EntityUtils.toString(response.getEntity());
						System.out.println("res:" + responseMsg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					loginBtn.setText("µÇÂ½Ê§°Ü");
				}
			}
		}).start();

	}
}
