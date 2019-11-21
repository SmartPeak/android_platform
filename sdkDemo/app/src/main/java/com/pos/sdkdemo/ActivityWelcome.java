package com.pos.sdkdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.basewin.define.ConstParam;
import com.basewin.log.LogUtil;
import com.basewin.services.ServiceManager;
import com.basewin.utils.FileOperate;
import com.basewin.widgets.IndicatorView;
import com.pos.sdkdemo.guides.GuiderActivity;
import com.pos.sdkdemo.utils.GlobalData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * welcome and init data
 */
public class ActivityWelcome extends Activity  implements ViewPager.OnPageChangeListener ,EasyPermissions.PermissionCallbacks{
	public static final int REQUEST_PERMISSION=0x01;
	protected int[] mImgBgRes = new int[]{R.drawable.p2000l,R.drawable.p2000l2,R.drawable.p6000,R.drawable.p8000,R.drawable.p80002,R.drawable.k6};
//	protected int[] mImgBgRes = new int[]{R.drawable.title1,R.drawable.title2,R.drawable.title3,R.drawable.title4};
	private ViewPager mPager;

	private boolean mIsFirst;

	private Editor mEdit;

	private IndicatorView mIndicator;

	private View mLayout;

	private ArrayList<View> mAllLayout;

	private int selectPage = 0;

	private int oldScrollState = 0;
	private TextView textView = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		permission();
		textView = (TextView) findViewById(R.id.textView);
		textView.setText("init sdkdemo...");
		mAllLayout = new ArrayList<View>();
		for (int i = 0; i < mImgBgRes.length; i++) {
			mLayout = getLayoutInflater().inflate(R.layout.activity_guidepage, null);
			mAllLayout.add(mLayout);
		}
		mIndicator = (IndicatorView) findViewById(R.id.indicatorView1);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOnPageChangeListener(this);
		mPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(View container, int position) {
				LogUtil.i(getClass(), "instantiateItem " + position);
				View layout = mAllLayout.get(position);
				layout.setBackgroundResource(mImgBgRes[position]);
				mPager.addView(layout);
				return layout;
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				LogUtil.i(getClass(), "destroyItem " + position);
				View layout = mAllLayout.get(position);
				mPager.removeView(layout);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				LogUtil.i(getClass(),  "isViewFromObject ");
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				LogUtil.i(getClass(),  "getCount ");
				return mAllLayout.size();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				initData();
				textView.setText("init data success!");
				/**
				 * init Device Server
				 */
			}
		});

		if (!GlobalData.getInstance().getWelcomeShows())
		{
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			startActivity(new Intent(ActivityWelcome.this,com.pos.sdkdemo.trade.activity.MenuActivity.class));
			finish();
		}
	}

	/**
	 * init data
	 */
	private void initData()
	{
		try {
			textView.setText("copy IDCertificationDemo.apk to "+ ConstParam.SD_Path);
			FileOperate.copyFileToSD(this, "IDCertificationDemo.apk", ConstParam.SD_Path, "IDCertificationDemo.apk");
			textView.setText("copy IDCertificationDemo.apk success!");
			textView.setText("copy ewm.bmp to "+ ConstParam.SD_Path);
			FileOperate.copyFileToSD(this, "ewm.bmp", ConstParam.SD_Path, "ewm.bmp");
			textView.setText("copy ewm.bmp success!");
			textView.setText("copy ywm.bmp to "+ ConstParam.SD_Path);
			FileOperate.copyFileToSD(this, "ywm.bmp", ConstParam.SD_Path, "ywm.bmp");
			textView.setText("copy ywm.bmp success!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		LogUtil.i(getClass(),  "onPageScrollStateChanged " + arg0);
		if (oldScrollState == 1 && arg0 == 0) {
			if (selectPage == (mImgBgRes.length-1)) {
				startDemo();
			}
		}
		else
		{
			oldScrollState = arg0;
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		LogUtil.i(getClass(), "onPageScrolled " + position + " " + positionOffset);
		mIndicator.refreshIndictor(position, positionOffset, mImgBgRes.length);
	}

	@Override
	public void onPageSelected(int arg0) {
		LogUtil.i(getClass(),  "onPageSelected " + arg0);
		selectPage = arg0;
		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	private void startDemo()
	{
		finish();
		GlobalData.getInstance().setGuiderShows(false);
		if (GlobalData.getInstance().getGuiderShows())
		{
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			startActivity(new Intent(ActivityWelcome.this, com.pos.sdkdemo.trade.activity.MenuActivity.class));
		}
		else
		{
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			startActivity(new Intent(ActivityWelcome.this,MenuActivity.class));
		}
	}

	@SuppressLint("NewApi")
	public void skip(View view)
	{
		GlobalData.getInstance().setWelcomeShows(false);
		finish();
		if (GlobalData.getInstance().getGuiderShows())
		{
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			startActivity(new Intent(ActivityWelcome.this,com.pos.sdkdemo.trade.activity.MenuActivity.class));
		}
		else
		{
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			startActivity(new Intent(ActivityWelcome.this,MenuActivity.class));
		}
	}

	@AfterPermissionGranted(REQUEST_PERMISSION)
	private void permission(){
		String[] perms = {
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.ACCESS_FINE_LOCATION,
		"com.pos.permission.SECURITY",
		"com.pos.permission.ACCESSORY_DATETIME",
		"com.pos.permission.ACCESSORY_LED",
		"com.pos.permission.ACCESSORY_BEEP",
		"com.pos.permission.ACCESSORY_RFREGISTER",
		"com.pos.permission.CARD_READER_ICC",
		"com.pos.permission.CARD_READER_PICC",
		"com.pos.permission.CARD_READER_MAG",
		"com.pos.permission.COMMUNICATION",
		"com.pos.permission.PRINTER",
		"com.pos.permission.ACCESSORY_RFREGISTER",
		"com.pos.permission.EMVCORE"
		};
		if (EasyPermissions.hasPermissions(this, perms)) {
			Toast.makeText(this,"Already Permission",Toast.LENGTH_SHORT).show();
			ServiceManager.getInstence().init(getApplicationContext());
			LogUtil.openLog();
		} else {
			// Do not have permissions, request them now
			EasyPermissions.requestPermissions(
					new PermissionRequest
							.Builder(this,REQUEST_PERMISSION,perms)
							.setRationale("Dear users\n need to apply for storage Permissions for\n your better use of this application")
							.setNegativeButtonText("NO")
							.setPositiveButtonText("YES")
							.build()
			);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		Log.e("Granted", "onRequestPermissionsResult:" + requestCode);
		if(requestCode == 1){
			ServiceManager.getInstence().init(getApplicationContext());
			LogUtil.openLog();
		}
	}

	@Override
	public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
		Log.e("Granted", "onPermissionsGranted:" + requestCode + ":" + perms.toString());
	}

	@Override
	public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
		Log.e("Denied", "onPermissionsDenied:" + requestCode + ":" + perms.toString());
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			new AppSettingsDialog
					.Builder(this)
					.setTitle("温馨提示")
					.setRationale("尊敬的用户为了您能更好的使用本应用需要申请存储权限")
					.setNegativeButton("拒绝")
					.setPositiveButton("去设置")
					.setRequestCode(0x001)
					.build()
					.show();
		}
	}
}
