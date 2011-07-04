// Basic request
Ext.Ajax.request({
			url : 'foo.php',
			success : someFn,
			failure : otherFn,
			headers : {
				'my-header' : 'foo'
			},
			params : {
				foo : 'bar'
			}
		});

// Simple ajax form submission
Ext.Ajax.request({
			form : 'some-form',
			params : 'foo=bar'
		});

// 使用Ext.Ajax.request提交数据的代码如下（这段代码在一个Js文件中，为避免中文乱码，js文件必须是utf-8编码）：

Ext.Ajax.request({

			url : 'http://localhost:8080/myapp/ExtHandler',

			jsonData : Ext.util.JSON.encode(info),

			params : {
				action : 'up'
			},

			success : function(resp, opts) {

				var respText = Ext.util.JSON.decode(resp.responseText);

				Ext.Msg.alert('提示', respText.info);

			},

			failure : function(resp, opts) {

				var respText = Ext.util.JSON.decode(resp.responseText);

				Ext.Msg.alert('错误', respText.error);

			}

		});

//
// 代码中的url参数指定的是一个Java
// Servlet，通过jsonData参数提交JSON格式的数据到Servlet处理，你也可以提交其它参数，在params参数中定义；然后根据服务器的处理结果Ext.Ajax调用相应成功或失败的回调函数进行处理；
//
// 
//
// 在Servlet中如何得到jsonData参数中定义的数据呢？看以下代码：

// StringBuffer json = new StringBuffer();
//
// String line = null;
//
// try {
//
// BufferedReader reader = req.getReader();
//
// while ((line = reader.readLine()) != null) {
//
// //读取jsonData中定义的数据
//
// json.append(line);
//
// }
//
// } catch (Exception e) {
//
// }

//
//
// 服务端处理数据成功，设置返回信息：

// 1. //success回调函数将调用执行，输出respText.info信息
//
// 2. rsp.setContentType("text/json; charset=utf-8");
//
// 3. rsp.getWriter().write(
//
// 4. "{success:true,info:'更新信息成功'}");

//
// 服务器端处理数据失败，设置返回信息：
//
//
//
// 1. rsp.setContentType("text/json; charset=utf-8");
//
// 2. rsp.getWriter().write(
//
// 3. "{success:false,error:'更新信息失败，原因为:" + err + "'}");
//
// 4. rsp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED); //设置失败标识
//
// 5. //failure回调函数将调用执行，输出respText.error信息
