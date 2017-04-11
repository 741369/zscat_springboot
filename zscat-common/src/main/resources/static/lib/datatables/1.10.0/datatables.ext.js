/*
 add this plug in
 // you can call the below function to reload the table with current state
 Datatables刷新方法
 oTable.fnReloadAjax(oTable.fnSettings());
 */
$.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings) {
	// oSettings.sAjaxSource = sNewSource;
	this.fnClearTable(this);
	this.oApi._fnProcessingDisplay(oSettings, true);
	var that = this;

	$.getJSON(oSettings.sAjaxSource, null, function(json) {
		/* Got the data - add it to the table */
		for (var i = 0; i < json.aaData.length; i++) {
			that.oApi._fnAddData(oSettings, json.aaData[i]);
		}
		oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		that.fnDraw(that);
		that.oApi._fnProcessingDisplay(oSettings, false);
	});
}

$.fn.dataTableExt.oApi.fnAjaxReload = function(oSettings, sNewSource, fnCallback, bStandingRedraw) {
	if (sNewSource !== undefined && sNewSource !== null) {
		oSettings.sAjaxSource = sNewSource;
	}
	if (oSettings.oFeatures.bServerSide) {
		this.fnDraw();
		return;
	}

	this.oApi._fnProcessingDisplay(oSettings, true);
	var that = this;
	var iStart = oSettings._iDisplayStart;
	var aData = [];
	this.oApi._fnServerParams(oSettings, aData);
	oSettings.fnServerData.call(oSettings.oInstance, oSettings.sAjaxSource, aData, function(json) {
		that.oApi._fnClearTable(oSettings);
		var aData = (oSettings.sAjaxDataProp !== "") ? that.oApi._fnGetObjectDataFn(oSettings.sAjaxDataProp)(json) : json;
		for (var i = 0; i < aData.length; i++){
			that.oApi._fnAddData(oSettings, aData[i]);
		}
		oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		that.fnDraw();
		if (bStandingRedraw === true){
			oSettings._iDisplayStart = iStart;
			that.oApi._fnCalculateEnd(oSettings);
			that.fnDraw(false);
		}
		that.oApi._fnProcessingDisplay(oSettings, false);
		if (typeof fnCallback == 'function' && fnCallback !== null) {
			fnCallback(oSettings);
		}
	}, oSettings);
};

$.fn.dataTable.ext.errMode = function(s,h,m){}