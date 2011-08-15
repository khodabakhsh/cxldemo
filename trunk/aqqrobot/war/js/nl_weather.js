var _xmlhttp = getXmlHttpObject();

function getXmlHttpObject() {
	var xmlHttp = null;
	try {
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp;
}

function getTotalCount() {
	_xmlhttp.open("get", "/webManager?action=getTotalCount&r=" + Math.random());
	_xmlhttp.onreadystatechange = onreadystatechange;
	_xmlhttp.send(null);
}

function onreadystatechange() {
	if (_xmlhttp.readyState == 4) {
		if (_xmlhttp.status == 200) {
			var data = eval("(" + _xmlhttp.responseText + ")");
			if (data.result) {
				document.getElementById("nl_total").innerHTML = data.total;
			}
		}
		document.getElementById("nl_count_loading").style.visibility = "hidden";
		document.getElementById("nl_refreshCount").value = "刷新";
		document.getElementById("nl_refreshCount").disabled = "";
	}
}
getTotalCount();
function nl_refreshCount() {
	document.getElementById("nl_count_loading").style.visibility = "visible";
	document.getElementById("nl_refreshCount").value = "请稍候";
	document.getElementById("nl_refreshCount").disabled = "disabled";
	getTotalCount();
}