function loadMain(href){
	if(href){
		HtmlRender(_href);
	}
}

$(function(){

	SideRender(BASE_PATH+ "/public/side");
	HtmlRender(BASE_PATH+ "/hebim/welcome");
	
	Huiasidedisplay();
	var resizeID;
	$(window).resize(function(){
		clearTimeout(resizeID);
		resizeID = setTimeout(function(){
			Huiasidedisplay();
		},500);
	});
	
	$(".nav-toggle").click(function(){
		$(".Hui-aside").slideToggle();
	});
	/*左侧菜单
	$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	
	$(".Hui-aside").on("click",".menu_dropdown a",function(){
		if($(this).attr('_href')){
			$(".menu_dropdown.bk_2 dl dd ul li").removeClass("current");
			$(this).parent().addClass("current");
			var _href=$(this).attr('_href');
			var _a=$(this).parent().parent().parent().prev().text();
			var _b=$(this).text();
			$("#breadcrumb_a").html(_a);
			$("#breadcrumb_b").html(_b);
			HtmlRender(_href);
		}
	});
	*/
	
}); 