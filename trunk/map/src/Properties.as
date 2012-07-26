// ActionScript file 属性编辑

/*属性窗口编辑*/
internal function onGridEditEnd(event: DataGridEvent): void{
	
	var tf: TextInput = TextInput(PropertyEditor(dgProp.itemEditorInstance).NewText);
	trace(event.rowIndex);
	trace(event.target);
	var value: String = tf.text;
	//如果只有一个图标被选择
	if(this.pSelectedItem != null){
		switch(event.rowIndex){
			case 0:
				pSelectedItem.name = value;
				break;
			case 1:
				pSelectedItem.label = value;
				pSelectedItem.redraw();
				break;
			case 2://填充颜色 16进制 ”0x”
				pSelectedItem.fillColor = uint("0x"+value);
				pSelectedItem.redraw();
				break;
			case 3:
				var n: Number = Number(value);
				if(n < 0) n = 0;
				pSelectedItem.startX = n;
				tf.text = n + "";
				break;
			case 4:
				n = Number(value);
				if(n < 0) n = 0;
				pSelectedItem.startY = n;
				tf.text = n + "";
				break;
			case 5:
				var n: Number = Number(value);
				if(n < 0) n = 0;
				pSelectedItem.endX = n;
				tf.text = n + "";
				break;
			case 6:
				n = Number(value);
				if(n < 0) n = 0;
				pSelectedItem.endY = n;
				tf.text = n + "";
				break;
		}
	}
}

//设置属性
private function setProperties(pBox:ItemBox): void{
	properties.removeAll();
	
	properties.addItem({name: "名称", value: pBox.name});	
	properties.addItem({name: "标签", value: pBox.label});
	properties.addItem({name: "颜色", value: pBox.fillColor.toString(16)});
	properties.addItem({name: "startX坐标", value: pBox.startX});	
	properties.addItem({name: "startY坐标", value: pBox.startY});	
	properties.addItem({name: "endX坐标", value: pBox.endX});	
	properties.addItem({name: "endY坐标", value: pBox.endY});	
	properties.addItem({name: "X坐标", value: pBox.x});	
	properties.addItem({name: "Y坐标", value: pBox.y});	
			
}
