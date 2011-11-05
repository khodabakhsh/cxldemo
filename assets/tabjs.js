// JavaScript Document

(function(){var G=function(H){return document.getElementById(H)};

var F=function(K,J){
	for(var I=0,H=K.length;I<H;I++){
		J.apply(K[I],[I,K[I]])
	}
};

var D=function(K,J,I,H){
	F(I,function(L,M){
		if(L!=H){
			M.className=J
		}
	});
	I[H].className=K
};
			
var C=function(J){
	var H=[];
	for(var I=0;I<20;I++){
		if(G(J+"-"+I)){
			H.push(G(J+"-"+I))
		}else{
			return H
		}
	}return H};
	
var A=function(H,J){
	var K=C(J.tabctrl+H);
	var I=C(J.tab+H);
	if(H==1){
		F(K,function(L,M){
			M.onclick=function(){
				D("","hide",I,L);
				D("on","",K,L);
				return false;
			}
		})}else{
			F(K,function(L,M){
				M.onclick=function(){
					D("","hide",I,L);
					D("on","",K,L);
					var base = "";
					if(location.href.indexOf('#') == -1){
						base = location.href
					}else{
						base = location.href.substr(0,location.href.indexOf('#'))
					}
					location.href = base + "#"+L;}})}};

	var B={tabctrl:"tbc",tab:"tb",num:1};

	for(var E=0;E<B.num;E++){
		A(E,B)
	}
})();

if(location.href.indexOf('#') != -1 && location.href.substr(location.href.indexOf('#')+1,location.href.length) =="1"){
	G('tbc0-1').onclick();	
}