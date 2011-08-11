$(document).ready(function() {
	$('#loading-div').hide()
	$('#photos').show().galleryView( {
		panel_width : document.documentElement.clientWidth - 76,
		panel_height : 10,
		frame_width : 100,
		frame_height : 80,
		auto_transition : true,
		transition_interval : 0,
		transition_speed : 'slow',
		background_color : '#333',
		background_fill_color : '#444',
		border : 'none',
		easing : 'easeInOutBack',
		nav_theme : 'custom',
		overlay_height : 52,
		filmstrip_position : 'top',
		overlay_position : 'top',
		pause_on_hover : true
	});
	$(".wbox").wBox( {
		isFrame : true,
		drag : true,
		iframeWH : {
			width : 700,
			height : 580
		},
		title : '评论|详情'
	});
	$(".wbox2").each(function() {
		$(this).wBox( {
			images : [ 'view?id=' + $(this).attr('pid') ],
			isImage : true
		});
	});

	$(".control").click(function() {
		$("#head").slideUp();
	});

	var lastPosYTime = new Date().getTime();
	$(document).mousemove(function(e) {
		if ($("#head").is(":hidden")) {
			if (e.clientY < 20) {
				if ($("#head").is(":hidden")) {
					var t = new Date().getTime() - lastPosYTime;
					if (t > 500 && t < 1000)
						slideDown();
				}
			} else
				lastPosYTime = new Date().getTime();
		}
	});
	slideUp();
});

function slideUp() {
	setTimeout(function() {
		$("#head").slideUp();
	}, 5000);
}

function slideDown() {
	$("#head").slideDown();
	slideUp();
}

function checkSize(img) {
	if (typeof (img) != 'object')
		img = document.getElementById(img);
	if (img == null)
		return;
	var image = document.createElement("img");
	image.onload = function() {
		var width = this.width;
		var height = this.height;
		if (width % 2 == 1)
			width = width + 1;
		if (height % 2 == 1)
			height = height + 1;
		$("#photonav1").attr("coords", "0,0," + width / 2 + "," + height);
		$("#photonav2")
				.attr("coords", width / 2 + ",0," + width + "," + height);
	};
	image.src = img.src;
}

var manClose = false;
function closeHelp() {
	manClose = true;
	$('#help-div').hide();
}

window.onscroll = function() {
	if (manClose)
		return;
	var help = document.getElementById('help-div');
	var scrollTop = document.documentElement.scrollTop
			|| document.body.scrollTop || 0;
	help.style.visibility = scrollTop > 142 ? "visible" : "hidden";
	var scrollLeft = document.documentElement.scrollLeft
			|| document.body.scrollLeft || 0;
	help.style.top = scrollTop + 'px';
	help.style.left = scrollLeft + 'px';
}
window.onresize = window.onscroll;