
//选中category指定的分类
function selectCategory(category) {
	cancelActive(category);
	gift_category_active = category;
	setActive();
	getHtmlData("space.jsp?do=gift&view=list&inajax=1", "type="+category, "giftData");
}

//取消category选中
function cancelActive() {
	if(gift_category_active != "defGift" && gift_category_active != "advGift") {
		$("feeGift").className = "";
		$(gift_category_active).className = "";
	} else {
		$(gift_category_active).className = "";
	}
	if(gift_category_active == "advGift") {
		$("advTips").style.display = "none";
	} else if(gift_category_active != "defGift") {
		$("feesAdd").style.display = "none";
		$("feesCategory").style.display = "none";
		$("feeOrder").style.display = "none";
		$("regular").style.display = "none";
	}
}

//设置category选中
function setActive() {
	if(gift_category_active != "defGift" && gift_category_active != "advGift") {
		$("feeGift").className = "active";
	} else {
		$(gift_category_active).className = "active";
	}
	if(gift_category_active == "advGift") {
		getHtmlData("space.jsp?do=gift&view=list&inajax=1", "reqtype=tips", "advTips");
		$("advTips").style.display = "block";
	} else if(gift_category_active != "defGift") {
		getHtmlData("space.jsp?do=gift&view=list&inajax=1", "reqtype=balance", "virtualCurrency");
		$("feesAdd").style.display = "block"; //充值
		getHtmlData("space.jsp?do=gift&view=list&inajax=1", "type="+gift_category_active+"&reqtype=feescate", "feesCategory");
		$("feesCategory").style.display = "block"; //收费子分类
		$("feeOrder").style.display = "block"; //排序
		$("regular").style.display = "block"; //定时发送
	}
}

//ajax方式从服务器获取category数据
function getHtmlData(url, sendString, divId) {
	//加随机数防止缓存 
    if(url.indexOf("?") > 0) { 
       url += "&randnum="+Math.random(); 
    } else { 
       url += "?randnum="+Math.random(); 
    }
	var x = new Ajax();
	x.post(url, sendString, function(s) {
		$(divId).innerHTML = s;
	});
}
