<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="utf-8" />
<script type="text/javascript">
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}

	function clearForm() {
		$('#gasFf').form('clear');
	}

	function doSearch() {
		getData();
	}
	function getData() {
		$.post('oilStorage/queryGasPriceList.do?' + Math.random(), $('#queryOilPriceForm')
				.serializeObject(), function(data) {
			$('#gasPrice').datagrid({
				loadFilter : pagerFilter
			}).datagrid('loadData', data);
		});
	}

	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
			data = {
				total : data.length,
				rows : data
			}
		}
		var dg = $('#gasPrice');
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg.datagrid('loadData', data);
			}
		});
		if (!data.originalRows) {
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = (data.originalRows.slice(start, end));
		return data;
	}


	$(function() {
		getData();

	})

	function addOilPrice() {
		$('#oilPriceDlg').dialog('open').dialog('setTitle', '添加油品价格');
		$('#oilPriceForm').form('clear');
	}
	
	function saveOilPrice() {
		$('#oilPriceForm').form('submit', {
			url : "oilStorage/saveOliPrice.do",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.alert({
						title : 'Error',
						msg : result.msg
					});
				} else {
					$.messager.alert('提示', '保存成功!');
					$('#oilPriceDlg').dialog('close'); // close the dialog
					$('#gasPrice').datagrid('reload',getData()); // reload the user data
				}
			}
		});
	}
	
	function delOliPrice() {
		var row = $('#gasPrice').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm', '确认删除所选数据么?', function(r) {
				if (r) {
					debugger
					$.post('oilStorage/delGasPrice.do', {
						gasPriceId : row.gaspriceid
					}, function(result) {
						if (result.success) {
							$.messager.alert('提示', '删除成功!');
							$('#oildg').datagrid('reload',getData());// reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : '删除失败'
							});
						}
					}, 'json');
				}
			});
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	
	function editOilPrice() {
		var row = $('#gasPrice').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "oilStorage/getGasPriceInfo.do",
				data : {
					gasPriceId : row.gaspriceid
				},
				async : true,
				success : function(data) {
					$('#oilPriceDlg').dialog('open').dialog('setTitle', '修改');
					$('#oilPriceForm').form('load', data);
					$("#gasType").attr("readonly",true);
				},
			})
		} else {
			$.messager.alert('提示', '请选中一行!');
		}
	}
	
	function formatValue(val, row) {
		return '$'+val;
	}
	
	function clearForm() {
		$('#queryOilPriceForm').form('clear');
	}

	function doSearch() {
		getData();
	}
</script>
<form id="queryOilPriceForm" method="post" style="margin-top: 20px;">
	<div style="margin-bottom: 7px;">
		<label for="oilType">油品类型:</label> <input id="cc3"
			class="easyui-combobox" name="gasType"
			style="width: 200px; height: 30px;"
			data-options="valueField:'dictvalue',textField:'dictname',url:'oilStorage/queryOilType.do',editable:false">
			 <input
			class="easyui-linkbutton" type="button" value="查询"
			style="width: 98px; height: 30px; margin-left: 550px"
			onclick="doSearch()"> <input class="easyui-linkbutton"
			type="button" value="重置" style="width: 98px; height: 30px;"
			onclick="clearForm()" />
	</div>

</form>
<table id="gasPrice" title="油品价格列表" style="width: 1050px; height: 78%;"
	toolbar="#toolbar4GasPrice"
	data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				fitColumns :true,
				pageSize:10">
	<thead>
		<tr>
			<th field="gas_type" width="60px" align="center">油品类型</th>
			<th field="gas_price" width="60px" align="center" formatter="formatValue">油品价格</th>
			<th field="gas_price_time" width="60px" align="center">创建时间</th>
		</tr>
	</thead>
</table>
<div id="toolbar4GasPrice">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="addOilPrice()">新增</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-edit" plain="true" onclick="editOilPrice()">修改</a> <a
		href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="delOliPrice()">移除</a>
</div>

<div id="oilPriceDlg" class="easyui-dialog"
	style="width: 390px; height: 200px; padding: 10px 20px" closed="true"
	buttons="#oilDlg-buttons" align="center" modal="true">
	<form id="oilPriceForm" method="post">
		<div style="margin-bottom: 7px;">
			<input name="gasPriceId" hidden="true" /> 
			<label>油品类型：</label> <input
				id="cc" class="easyui-combobox" name="gasType"
				style="width: 200px; height: 30px;"
				data-options="valueField:'dictvalue',textField:'dictname',url:'oilStorage/queryOilType.do',editable:false"
				required="true" id="gasType">
		</div>
		<div style="margin-bottom: 7px;">
			<label>油品价格：</label> <input name=gasPrice class="easyui-numberbox"
				data-options="required:true,groupSeparator:',',decimalSeparator:'.',prefix:'$'" style="width: 200px; height: 30px;"
				precision="2">
		</div>
	</form>
</div>
<div id="oilDlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
		onclick="saveOilPrice()">保存</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:$('#oilPriceDlg').dialog('close')">取消</a>
</div>