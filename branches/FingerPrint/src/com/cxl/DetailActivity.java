package com.cxl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.cxl.fingerprint.R;
import com.waps.AppConnect;

public class DetailActivity extends Activity
{
	private void showDialog()
	{
		new AlertDialog.Builder(DetailActivity.this)
				.setIcon(R.drawable.happy2)
				.setTitle("当前积分：" + MainActivity.currentPointTotal)
				.setMessage(
						"只要积分满足"
								+ MainActivity.requirePoint
								+ "，就可以消除本提示信息！！ 您当前的积分不足"
								+ MainActivity.requirePoint
								+ "，所以会有此 提示。\n\n【免费获得积分方法】：请点击【确认键】进入推荐下载列表 , 下载并安装软件获得相应积分，消除本提示！！")
				.setPositiveButton("确认", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialoginterface, int i)
					{
						// 显示推荐安装程序（Offer）.
						AppConnect.getInstance(DetailActivity.this).showOffers(
								DetailActivity.this);
					}
				}).setNegativeButton("取消",
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog,
									int which)
							{
								// finish();
							}
						}).show();
	}

	private WebView mWebView;

	private Button returnButton;

	private ProgressDialog progressDialog = null;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		progressDialog = new ProgressDialog(DetailActivity.this);
		progressDialog.setTitle("请稍等...");
		progressDialog.setMessage("获取数据中...");
		progressDialog.setIndeterminate(true);
		progressDialog.setIndeterminateDrawable(this.getResources()
				.getDrawable(R.drawable.wait));
		progressDialog.show();

		new Thread(new Runnable()
		{
			public void run()
			{
				Bundle bundle = getIntent().getExtras();
				mWebView = (WebView) findViewById(R.id.myWebView);
				String content = "<div class=\"entry\">  <div class=\"csense\" id=\"c001\">   <script language=\"javascript\" type=\"text/javascript\" src=\"http://www.supfree.net/images/gooooo.js\">   </script>   <br />  </div>     <hr size=\"1\" />  <table border=\"0\" style=\"border-collapse: collapse;vertical-align:middle; text-align:center;\" height=\"91\">   <tbody>   <tr>     <td height=\"23\">大拇指</td>     <td height=\"23\">食指</td>     <td height=\"23\">中指</td>     <td height=\"23\">无名指 </td>     <td height=\"23\">小指 </td>    </tr>    <tr>     <td height=\"45\"><img src=\"0.gif\" /></td>     <td height=\"45\"><img src=\"1.gif\" /></td>     <td height=\"45\"><img src=\"1.gif\" /></td>     <td height=\"45\"><img src=\"0.gif\" /></td>     <td height=\"45\"><img src=\"1.gif\" /></td>    </tr>    <tr>     <td>流纹</td>     <td>涡纹</td>     <td>涡纹</td>     <td>流纹</td>     <td>涡纹</td>    </tr>   </tbody> </table>  <b>属性类型：</b> <br /> 开朗活泼型 <br />  <br />  <b>个性分析：</b> <br /> 开朗活动，健谈。 <br /> 你容易抓住好的机会。 <br /> 容易上当受骗。 <br /> 道德感对你还说是比生命还重的事。 <br /> 感情丰富。 <br />  <br />  <b>运势分析：</b> <br /> 中年有成就大事的机会。 <br /> 你天赋聪明。 <br /> 对婚姻比较不是那麽在乎。 <br /> 非常独立自主。 <br /> 你应是次女或次子。 <br />  <br />  <b>婚姻解析：</b> <br /> 你常单恋，对方却只想跟你保持单纯的友谊。 <br /> 多听长辈的意见，对结婚对象要好好地挑选，不然是容易走到离婚的地步。 <br />  <br />  <b>你适合的职业：</b> <br /> 科学家。 <br /> 太空人。 <br /> 工程师。 <br /> 建筑业。 <br /> 广告商。 <br />  <br />  <b>健康上，应多注意的部位：</b> <br /> 呼吸器官 <br /> 肝脏.皮肤 <br />  <br />  <b>流年运势：</b> <br /> &nbsp;1岁： <font color=\"blue\">平</font> &nbsp;2岁： <font color=\"blue\">平</font> &nbsp;3岁： <font color=\"blue\">平</font> &nbsp;4岁： <font color=\"blue\">平</font> &nbsp;5岁： <font color=\"blue\">平</font> <br /> &nbsp;6岁： <font color=\"blue\">平</font> &nbsp;7岁： <font color=\"blue\">平</font> &nbsp;8岁： <font color=\"blue\">平</font> &nbsp;9岁： <font color=\"blue\">平</font> 10岁： <font color=\"green\">吉</font> <br /> 11岁： <font color=\"blue\">平</font> 12岁： <font color=\"red\">凶</font> 13岁： <font color=\"green\">吉</font> 14岁： <font color=\"red\">凶</font> 15岁： <font color=\"blue\">平</font> <br /> 16岁： <font color=\"green\">吉</font> 17岁： <font color=\"blue\">平</font> 18岁： <font color=\"blue\">平</font> 19岁： <font color=\"green\">吉</font> 20岁： <font color=\"blue\">平</font> <br /> 21岁： <font color=\"blue\">平</font> 22岁： <font color=\"green\">吉</font> 23岁： <font color=\"blue\">平</font> 24岁： <font color=\"red\">凶</font> 25岁： <font color=\"green\">吉</font> <br /> 26岁： <font color=\"red\">凶</font> 27岁： <font color=\"blue\">平</font> 28岁： <font color=\"green\">吉</font> 29岁： <font color=\"blue\">平</font> 30岁： <font color=\"blue\">平</font> <br /> 31岁： <font color=\"green\">吉</font> 32岁： <font color=\"blue\">平</font> 33岁： <font color=\"blue\">平</font> 34岁： <font color=\"green\">吉</font> 35岁： <font color=\"blue\">平</font> <br /> 36岁： <font color=\"blue\">平</font> 37岁： <font color=\"green\">吉</font> 38岁： <font color=\"blue\">平</font> 39岁： <font color=\"blue\">平</font> 40岁： <font color=\"green\">吉</font> <br /> 41岁： <font color=\"blue\">平</font> 42岁： <font color=\"red\">凶</font> 43岁： <font color=\"green\">吉</font> 44岁： <font color=\"blue\">平</font> 45岁： <font color=\"blue\">平</font> <br /> 46岁： <font color=\"green\">吉</font> 47岁： <font color=\"blue\">平</font> 48岁： <font color=\"red\">凶</font> 49岁： <font color=\"green\">吉</font> 50岁： <font color=\"blue\">平</font> <br /> 51岁： <font color=\"blue\">平</font> 52岁： <font color=\"green\">吉</font> 53岁： <font color=\"blue\">平</font> 54岁： <font color=\"red\">凶</font> 55岁： <font color=\"green\">吉</font> <br /> 56岁： <font color=\"blue\">平</font> 57岁： <font color=\"blue\">平</font> 58岁： <font color=\"green\">吉</font> 59岁： <font color=\"blue\">平</font> 60岁： <font color=\"red\">凶</font> <br /> 61岁： <font color=\"green\">吉</font> 62岁： <font color=\"blue\">平</font> 63岁： <font color=\"blue\">平</font> 64岁： <font color=\"green\">吉</font> 65岁： <font color=\"blue\">平</font> <br /> 66岁： <font color=\"red\">凶</font> 67岁： <font color=\"green\">吉</font> 68岁： <font color=\"blue\">平</font> 69岁： <font color=\"blue\">平</font> 70岁： <font color=\"green\">吉</font> <br /> 71岁： <font color=\"blue\">平</font> 72岁： <font color=\"red\">凶</font> 73岁： <font color=\"blue\">平</font> 74岁： <font color=\"blue\">平</font> 75岁： <font color=\"blue\">平</font> <br /> 76岁： <font color=\"blue\">平</font> 77岁： <font color=\"blue\">平</font> 78岁： <font color=\"blue\">平</font> 79岁： <font color=\"blue\">平</font> 80岁： <font color=\"blue\">平</font> <br />  <br />  <b>运势结论：</b> <br /> 你是个天资聪明的人，你做事不喜欢被限制。中年以後有好运会与你相伴，容易成功于事业。虽然你天性善良，但一发起脾气会像火山爆发。 </div>";
				// mWebView.loadDataWithBaseURL(
				// FingerPrintUtil.getFingerPrintDetailUrl,
				// FingerPrintUtil.getFingerPrintDetail(bundle
				// .getString("wo1"), bundle.getString("wo2"),
				// bundle.getString("wo3"), bundle
				// .getString("wo4"), bundle
				// .getString("wo5")), "text/html",
				// FingerPrintUtil.UTF8, "");
				String fileName = "content_" + bundle.getString("wo1")
						+ bundle.getString("wo2") + bundle.getString("wo3")
						+ bundle.getString("wo4") + bundle.getString("wo5");

				String fileContent = getFileContent(DetailActivity.this,
						getResources().getIdentifier(fileName, "raw",
								getPackageName()));
				mWebView.loadDataWithBaseURL(
						FingerPrintUtil.getFingerPrintDetailUrl, fileContent,
						"text/html", FingerPrintUtil.UTF8, "");
				progressDialog.dismiss();

			}
		}).start();

		if (!MainActivity.hasEnoughRequrePoint)
		{// 没达到积分
			showDialog();
		}

		returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new Button.OnClickListener()
		{

			public void onClick(View arg0)
			{
				finish();
			}
		});
		Button owns = (Button) findViewById(R.id.OwnsButton);
		owns.setText("更多免费应用...");
		owns.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View arg0)
			{
				// 显示自家应用列表.
				AppConnect.getInstance(DetailActivity.this).showMore(
						DetailActivity.this);
			}
		});
		// Button offers = (Button) findViewById(R.id.OffersButton);
		// offers.setText("其他推荐的免费应用...");
		// offers.setOnClickListener(new Button.OnClickListener() {
		// public void onClick(View arg0) {
		// // 显示推荐安装程序（Offer）.
		// AppConnect.getInstance(DetailActivity.this).showOffers(DetailActivity.this);
		// }
		// });
	}

	public String getFileContent(Context context, int x)
	{// 规划了file参数、ID参数，方便多文件写入。
		InputStream in = null;
		BufferedReader bufferedReader = null;
		StringBuilder sBuffer = new StringBuilder("");
		try
		{
			in = context.getResources().openRawResource(x);

			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null)
			{
				sBuffer.append(strLine + "\n");
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (bufferedReader != null)
				try
				{
					bufferedReader.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			if (in != null)
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		return sBuffer.toString();
	}
}
