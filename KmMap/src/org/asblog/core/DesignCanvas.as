package org.asblog.core
{
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.events.ContextMenuEvent;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.ui.*;
	import flash.ui.Keyboard;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.graphics.ImageSnapshot;
	import mx.managers.DragManager;
	
	import org.asblog.contextmenu.ContextMenuLabels;
	import org.asblog.contextmenu.ContextMenuPlus;
	import org.asblog.event.LineControlEvent;
	import org.asblog.event.MediaContainerEvent;
	import org.asblog.event.MediaItemEvent;
	import org.asblog.frameworks.view.*;
	import org.asblog.mediaItem.MediaImage;
	import org.asblog.mediaItem.MediaLine;
	import org.asblog.mediaItem.MediaShape;
	import org.asblog.mediaItem.MediaText;
	import org.asblog.mxml.IMXML;
	import org.asblog.transform.TransformTool;
	import org.asblog.transform.TransformToolControl;
	import org.asblog.transform.TransformToolEvent;
	import org.asblog.utils.LineControl;
	import org.puremvc.as3.patterns.facade.Facade;
	
	import ui.Snapshot;
	import ui.material.GraphicsList;

	/**
	 * 当有东西被拖近来时触发
	 */
	[Event(name="onAddChild", type="org.asblog.event.MediaContainerEvent")]

	/**
	 * 当拖近来的东西被选中时触发
	 */
	[Event(name="onSelect", type="org.asblog.event.MediaItemEvent")]

	/**
	 * 对某一对象进行转换时触发(比如大小，位置之类的改变)
	 */
	[Event(name="transformTarget", type="flash.events.Event")]
	/**
	 * 索引改变
	 */
	[Event(name="indexChange", type="flash.events.Event")]
	/**
	 * 画布
	 * @author Halley
	 */
	public class DesignCanvas extends Canvas implements IMXML
	{
		private var _bgPolicy:String="lashen";
		private var _transformTool:TransformTool;
		private var _selectedItem:IMediaObject;
		private var _selectedItems:Vector.<IMediaObject>=new Vector.<IMediaObject>();
		private var _selectedIndices:Array=[];
		private var _selectedArea:IMediaObjectContainer;
		private var _itemList:ArrayCollection;
		private var _lineList:ArrayCollection=new ArrayCollection(); //存放线条

		public var isBeginDrawLine:Boolean=false;
		private var beginDrawLineMouseX:int;
		private var beginDrawLineMouseY:int;
		private var currentDrawMediaLine:MediaLine=null;

		public static var _lineControl:LineControl=new LineControl(); //线控制工具

		public var imageID_position_dictionary:Dictionary=new Dictionary(); //存放imageId和position关系


		/**
		 * 背景容器
		 */
		private var _background:IMediaObject;
		/**
		 * 画布容器（只存放可选中的东西,不包括转换工具，背景）
		 */
		private var _canvasContent:UIComponent;

		public static var viewOnly:Boolean=false; //模式

		/**
		 * 右键菜单
		 */
		private var _cm:ContextMenuPlus;

		public function get lineList():ArrayCollection
		{
			return _lineList;
		}

		public function set lineList(value:ArrayCollection):void
		{
			_lineList=value;
		}

		public function get lineControl():LineControl
		{
			return _lineControl;
		}

		public function set lineControl(value:LineControl):void
		{
			_lineControl=value;
		}

		/**
		 * 背景
		 * @return  IMediaObject
		 */
		public function get background():IMediaObject
		{
			return _background;
		}

		/**
		 * 设置背景是平铺还是拉伸
		 */
		[Bindable]

		public function get bgPolicy():String
		{
			return _bgPolicy;
		}

		public function set bgPolicy(v:String):void
		{
			_bgPolicy=v;
			if (v == "tile")
			{

			}
			else
			{
				background.relatedObject.width=background.width=1024;
				background.relatedObject.height=background.height=768;
			}
		}


		override public function set width(value:Number):void
		{
			super.width=value;
			background.relatedObject.width=width;
		}

		override public function set height(value:Number):void
		{
			super.height=value;
			background.relatedObject.height=height;
		}

		/**
		 * 返回对主容器的引用
		 */
		public function get canvasContent():UIComponent
		{
			return _canvasContent;
		}

		/**
		 * 是否选中的是画布
		 */
		[Bindable]
		public var isCanvasSelected:Boolean=true;

		public function DesignCanvas()
		{
			var mode:String=FlexGlobals.topLevelApplication.parameters.mode;
			viewOnly=((mode != null) && ("view" == mode));

			super();
			chageBackground();
			initItemList();
			initContent();
			if (viewOnly)
			{
				return;
			}
			initTransformTool();
			initMultiSelection();
			initContextMenu();
			initLineControl();

			this.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
			this.addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
			this.addEventListener(MouseEvent.MOUSE_UP, mouseUp);
			this.addEventListener(DragEvent.DRAG_ENTER, onDragEnter);
			this.addEventListener(Event.ADDED_TO_STAGE, onAddToStage);

			this.addEventListener(LineControlEvent.Select, onSelectLineControl);

		}

		private function initLineControl():void
		{
			this._canvasContent.addChild(lineControl);
		}

		private function onSelectLineControl(event:LineControlEvent):void
		{
			var point2:Point=globalToLocal(new Point(event.point.x, event.point.y));
			lineControl.x=event.point.x;
			lineControl.y=event.point.y;
		}

		private function onAddToStage(event:Event):void
		{
			stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyboardEvent);
		}

		//------------------公有方法-------------------
		/**
		 * 通过UID找到IMediaObject
		 * @param uid 目标元件的UID
		 * @return 目标元件
		 */
		public function getItemByUid(uid:String):IMediaObject
		{
			var item:IMediaObject;
			for (var i:int=0; i < itemList.length; i++)
			{
				item=IMediaObject(itemList.getItemAt(i));
				if (item.uid == uid)
					return item;
			}
			return null;
		}

		/**
		 * 把工具移动到最上方
		 * @param target
		 */
		public function setToTop(target:DisplayObject):void
		{
			setChildIndex(target, numChildren - 1);
		}

		/**
		 * 里面会调用seekIMediaObject查找IMediaObject类型的元件
		 * @param v 目标元件，可以是对象内部的可视元素或者元件的uid
		 * @param canMove 是不是拖拽进来的(选择后是否可立刻移动)
		 */
		public function setSelection(v:*, canMove:Boolean=true, triggerEvent:Boolean=true):void
		{
			if (viewOnly)
			{
				return;
			}
			if (v != null)
			{
				var target:IMediaObject;
				if (v is String)
				{
					target=getItemByUid(String(v));
				}
				else
				{
					//得到目标对象	为IMediaObject。否则返回空
					target=seekIMediaObject(v) as IMediaObject;
				}
				//目标不为空
				if (target != null && target.selectEnabled)
				{
					/*
					//触发这个事件有利与外界改变被选对象
					var eve : MediaItemEvent = new MediaItemEvent( MediaItemEvent.BEFORE_SELECT );
					v.dispatchEvent( eve );*/

					if (canMove)
					{
						//trace( "可以立即移动" );
						_transformTool.moveNewTargets=true;
						_transformTool.moveEnabled=true;
					}
					else
					{
						//trace( "不可立即移动" );
						_transformTool.moveNewTargets=false;
					}
					_transformTool.target=target;
//					Alert.show(target.transform.matrix.a.toString());
					isCanvasSelected=false;
					if (target is IMediaObjectContainer)
					{
						_transformTool.registration=_transformTool.boundsTopLeft;
					}
					else
					{
						_transformTool.registration=_transformTool.boundsCenter;
					}
					//v.selected = true
					setToTop(_transformTool);
					if (triggerEvent)
					{
						target.dispatchEvent(new MediaItemEvent(MediaItemEvent.SELECTED, target.mediaLink));
					}
					selectedItem=target;
					//如果targetObject被改变，将会选择更改后的对象
					setContextMenu();
					GraphicsList.isMediaLineSelecting=false;
					dispatchEvent(new MediaItemEvent(MediaItemEvent.CHANGE, target.mediaLink));

					this.lineControl.selectNull();
				}
			}
			else
			{
				isCanvasSelected=true;
				_transformTool.target=null;
				selectedItem=null;
				if (triggerEvent)
					dispatchEvent(new TransformToolEvent(TransformToolEvent.SETSELECTION));
			}
		}

		/**
		 * 如果有东西被拖近来就触发MediaContainerEvent的ADD_CHILD事件
		 * @param child
		 * @return
		 */
		public function addMediaItem(child:IMediaObject):IMediaObject
		{
			var c:UIComponent=UIComponent(child);
			_canvasContent.addChild(c);

			_itemList.addItemAt(child, 0);
			if (child.isComplete)
			{
				childAdded(child);
			}
			else
			{
				if (child is MediaImage)
				{
					child.addEventListener(Event.COMPLETE, childAdded);
				}
				else
				{

					child.addEventListener(FlexEvent.CREATION_COMPLETE, childAdded);
				}
			}
			return child;
		}

		public function chageBackground(child:IMediaObject=null):void
		{
			if (_background && _itemList.getItemIndex(_background) != -1)
			{
				_itemList.removeItemAt(_itemList.getItemIndex(_background));
			}

			var bg:DisplayObject;
			if (_background)
			{
				_background.dispose();
				_background=null;
			}
			if (child)
			{
				bg=DisplayObject(child);
				if (child.mediaLink.isBackground)
				{
					_background=child;
					child.addEventListener(Event.COMPLETE, backgroundLoaded);
					_itemList.addItemAt(child, 0);
				}
			}
			else
			{
				_background=new MediaShape();
				var link:ShapeLink=new ShapeLink(MediaShape.Rect);
				link.classRef=MediaShape;
				link.isBackground=true;
				link.width=width;
				link.height=height;
				link.color=0xFFFFFF;
				link.bgColor=0xFFFFFF;
				_background.mediaLink=link;
				_background.cacheAsBitmap=true;
				_background.percentHeight=100;
				_background.percentWidth=100;
				MediaShape(_background).draw();
				bg=DisplayObject(_background);
			}
			addChild(bg);
			setChildIndex(bg, 0);
		}

		/**
		 * 添加一组元素
		 * @param array
		 */
		public function addMediaItems(array:Vector.<IMediaObject>):void
		{
			for each (var child:IMediaObject in array)
			{
				addMediaItem(child);
			}
		}

		/**
		 * 删除指定的元素，并把转换工具置空
		 * @param child
		 * @return
		 */
		private function removeMediaItem(child:IMediaObject, cleanRef:Boolean=true):void
		{
			if (!child)
			{
				trace("要删除的元素为空");
				return;
			}
			itemList.removeItemAt(itemList.getItemIndex(child));
			child.dispose();
			if (cleanRef)
				cleanReference();
		}

		/**
		 * 通过UID删除元素
		 * @param uid UID的字符串
		 * @return 被删除的元素
		 */
		public function removeMediaItemByUID(uid:String):void
		{
			removeMediaItem(getItemByUid(uid));
		}

		/**
		 * 删除选中的元素
		 */
		public function removeSelectedMediaItems():void
		{
			removeMediaItem(_selectedItem);
			for each (var obj:IMediaObject in _selectedItems)
			{
				removeMediaItem(obj);
			}
		}

		/**
		 * 删除所有元素，不包括背景和转换工具，只是被拖进来的东西
		 */
		public function removeAllMediaItem():Vector.<MediaLink>
		{
			var removedLinks:Vector.<MediaLink>;
			var removedItem:*;
			while (_canvasContent.numChildren)
			{
				removedItem=_canvasContent.removeChildAt(0);
				if (removedItem is IMediaObject)
				{
					if (!removedLinks)
						removedLinks=new Vector.<MediaLink>();
					removedLinks.push(IMediaObject(removedItem).mediaLink);
				}
			}
			itemList=null;
			initItemList();
			cleanReference();
			return removedLinks;
		}

		public function parseMXML(mxml:MXML):void
		{
		}

		public function createMXML(container:IMediaObjectContainer=null):MXML
		{
			return null;
		}

		private function initContent():void
		{
			_canvasContent=new UIComponent();
			_canvasContent.percentWidth=100;
			_canvasContent.percentHeight=100;
			this.addChild(_canvasContent);
		}

		private function initContextMenu():void
		{
			_cm=new ContextMenuPlus();
			this.contextMenu=_cm.contextMenu;
		}

		/**
		 *转变工具初始化
		 *
		 */
		private function initTransformTool():void
		{
			_transformTool=new TransformTool();
			//_transformTool.addControl(new CustomRotationControl())
			//不进行层级控制(就选择的东西原来什么级别就是什么级别)，如果是true点到的东西将被置顶
			_transformTool.raiseNewTargets=false;
			//_transformTool.livePreview = false;
			_transformTool.moveUnderObjects=false;
//			_transformTool.constrainRotation = true;
			//_transformTool.constrainRotationAngle = 90 / 4;
			_transformTool.skewEnabled=false;
			_transformTool.registrationEnabled=false;
//			_transformTool.registrationEnabled=false;
			_transformTool.scaleEnabled=true;
//			_transformTool.maxScaleX=2;
//			_transformTool.maxScaleY=2;
			_transformTool.constrainScale=false;
			_transformTool.rotationEnabled=false;
			_transformTool.outlineEnabled=false;
			
//			_transformTool.livePreview =false;

			//如果用了工具，此事件会一直发出
			_transformTool.addEventListener(TransformTool.TRANSFORM_TARGET, transformTarget);
			_transformTool.addEventListener(TransformToolEvent.TARGET_MATRIX_CHANGE, targetMatrixChange);
			addChild(_transformTool);
		}


		/**
		 * 初始化元素集合
		 */
		private function initItemList():void
		{
			itemList=new ArrayCollection();

			//让深度高的排在前面
		/*
		var s : Sort = new Sort( );
		s.fields = [ new SortField( "depthAtParent", true, true ) ];
		itemList.sort = s;
		itemList.refresh( );*/
		}

		private function initMultiSelection():void
		{
		/*
		_selectionCanvas = new Canvas( );
		_selectionCanvas.setStyle( "backgroundAlpha", 0.3 );
		_selectionCanvas.setStyle( "backgroundColor", 0x316AC5 );
		addChild( _selectionCanvas );*/
		}

		/**
		 * 利用此事件实时更新被选元素的X，Y等属性
		 * @param eve
		 */
		private function transformTarget(event:Event):void
		{
			dispatchEvent(event.clone());
		}

		/**
		 * 目标的矩阵模型变更了
		 */
		private function targetMatrixChange(event:TransformToolEvent):void
		{
			dispatchEvent(new TransformToolEvent(TransformToolEvent.TARGET_MATRIX_CHANGE, event.uid, event.oldMatrix, event.newMatrix));
		}

		/**
		 * 设置转变工具
		 * @param _transformTool
		 */
		public function set transformTool(t:TransformTool):void
		{
			_transformTool.removeEventListener(TransformTool.TRANSFORM_TARGET, transformTarget);
			_transformTool=t;
			_transformTool.addEventListener(TransformTool.TRANSFORM_TARGET, transformTarget);
		}

		/**
		 * @return 得到关联的转变工具
		 */
		[Bindable]

		public function get transformTool():TransformTool
		{
			return _transformTool;
		}

		/**
		 * 此属性多用与绑定
		 * @return 当前选中的元素
		 */
		[Bindable]
		public function get selectedItem():IMediaObject
		{
			return _selectedItem;
		}

		public function set selectedItem(v:IMediaObject):void
		{
			_selectedItem=v;
		}

		[Bindable]
		public function get selectedArea():IMediaObjectContainer
		{
			return _selectedArea;
		}

		public function set selectedArea(v:IMediaObjectContainer):void
		{
			if (v == null)
			{
				for each (var obj:IMediaObject in selectedItems)
				{
					//DisplayObject(obj).transform.matrix = _selectedArea.transform.matrix;
					v.addChild(DisplayObject(obj));
				}
			}
			_selectedArea=v;
		}

		[Bindable]

		public function get selectedItems():Vector.<IMediaObject>
		{
			return _selectedItems;
		}

		public function set selectedItems(v:Vector.<IMediaObject>):void
		{
			_selectedItems=v;
		}

		[Bindable]

		public function get selectedIndices():Array
		{
			return _selectedIndices;
		}

		public function set selectedIndices(v:Array):void
		{
			_selectedIndices=v;
		}

		/**
		 * 此属性多用于绑定,提供数据给图层管理
		 * @return 画布里所有显示元素的集合(不包括背景和TransformTool)
		 */
		[Bindable]

		public function get itemList():ArrayCollection
		{
			return _itemList;
		}

		public function set itemList(v:ArrayCollection):void
		{
			_itemList=v;
		}

		private function onMouseDown(event:MouseEvent):void
		{
			var selectedObj:DisplayObject=DisplayObject(event.target);
			var toolEvent:TransformToolEvent;

			if (selectedObj is TransformToolControl)
			{
				return;
			}
			//单击了画布空白区
			else if (!GraphicsList.isMediaLineSelecting && (_background.contains(selectedObj) || selectedObj == this))
			{
				toolEvent=new TransformToolEvent(TransformToolEvent.SETSELECTION);
				toolEvent.selectedUid=null;
				toolEvent.oldSelectedUid=_selectedItem != null ? _selectedItem.uid : null;
				dispatchEvent(toolEvent);
					//setSelection( null );
			}
			//单击选中了画布中的某个元素（不包括连线）
			else if ((selectedObj != _background) && (seekIMediaObject(selectedObj) is IMediaObject))
			{
				var target:IMediaObject;
				target=seekIMediaObject(selectedObj) as IMediaObject;
				if (target != null && target != _selectedItem)
				{
					toolEvent=new TransformToolEvent(TransformToolEvent.SETSELECTION, target.uid);

					toolEvent.oldSelectedUid=_selectedItem != null ? _selectedItem.uid : null;
					toolEvent.selectedUid=target.uid;
					dispatchEvent(toolEvent);
				}
					//setToTop( _selectionCanvas );
					//_selecting = true;
					//setSelection( selectTarget );
					//onSelect( selectedObj );
			}
			//开始画线
			else if (GraphicsList.isMediaLineSelecting && !this.lineControl.isBeginDragStartControl && !this.lineControl.isBeginDragEndControl)
			{
				this.lineControl.selectNull();
				isBeginDrawLine=true;
				beginDrawLineMouseX=mouseX;
				beginDrawLineMouseY=mouseY;
			}

		}


		private function onMouseMove(event:MouseEvent):void
		{
			if (isBeginDrawLine)
			{
				if (currentDrawMediaLine == null)
				{
					currentDrawMediaLine=new MediaLine(beginDrawLineMouseX, beginDrawLineMouseY);
//					trace("currentDrawMediaLine   x: " + currentDrawMediaLine.x + " ,  currentDrawMediaLine  y: " + currentDrawMediaLine.y);
					currentDrawMediaLine.x=0;
					currentDrawMediaLine.y=0;
					this._canvasContent.addChild(currentDrawMediaLine);
					this._lineList.addItem(currentDrawMediaLine);
					currentDrawMediaLine.designCanvas=this;
				}
				currentDrawMediaLine.toX=mouseX;
				currentDrawMediaLine.toY=mouseY;
				currentDrawMediaLine.draw();
			}
		}


		private function mouseUp(event:MouseEvent):void
		{
			isBeginDrawLine=false;
			currentDrawMediaLine=null;

		}

		/**
		 * 如果新添元件初始化完毕才触发MediaContainerEvent.ADD_CHILD事件
		 * @param eve
		 */
		private function childAdded(eve:*, triggerEvent:Boolean=true):void
		{
			var targetItem:IMediaObject;
			if (eve is Event)
			{
				targetItem=IMediaObject(eve.currentTarget);
				targetItem.isComplete=true;
				if (targetItem is MediaImage)
				{
					targetItem.removeEventListener(Event.COMPLETE, childAdded);
				}
				else
				{
					targetItem.removeEventListener(FlexEvent.CREATION_COMPLETE, childAdded);
				}
			}
			else
			{
				targetItem=eve;
			}
			if (!viewOnly)
			{
				setSelection(targetItem, false, false);
			}
			if (triggerEvent)
				dispatchEvent(new MediaContainerEvent(MediaContainerEvent.ADD_CHILD, targetItem.mediaLink));
		}

		private function backgroundLoaded(event:Event):void
		{
			var bg:MediaImage=MediaImage(event.currentTarget);
			bg.removeEventListener(Event.COMPLETE, backgroundLoaded);
			if (bg.mediaLink.isAdjuestImage)
			{
				width=bg.transform.pixelBounds.width;
				height=bg.transform.pixelBounds.height;
			}
			else
			{
				bg.width=width;
				bg.height=height;
			}

		}

		/**
		 * 清除相关引用，个选
		 * 用在对象被删除，或者选中了画布，背景
		 */
		private function cleanReference():void
		{
			setSelection(null, false, false);
		}

		/**
		 * 递归寻找目标是否为IMediaObject如果不是找他的父级，次数小与500。
		 * @param target
		 * @return
		 */
		private function seekIMediaObject(target:DisplayObject):DisplayObject
		{
			var conut:int;
			conut++;
			if (target is IMediaObject)
				return target;
			else if (conut > 500)
				return null;
			else if (target.parent)
				return seekIMediaObject(target.parent);
			else
				return null;
		}

		/**
		 * 当有拖拽进入画布区域
		 * @param event
		 */
		private function onDragEnter(event:DragEvent):void
		{
			if (event.dragInitiator is Snapshot)
			{
				if (!Snapshot(event.dragInitiator).mediaLink.isMask)
				{
					DragManager.acceptDragDrop(this);
					DragManager.showFeedback(DragManager.COPY);
				}
			}
		}


		/**
		 * 监听键盘
		 * @param event
		 */
		private function onKeyboardEvent(event:KeyboardEvent):void
		{
			switch (event.keyCode)
			{
				case Keyboard.DELETE:
					deleteSelectItem();
					break;
			}
		}

		private function deleteSelectItem():void
		{
			if (_selectedItem != null)
			{
				dispatchEvent(new MediaContainerEvent(MediaContainerEvent.REMVOE_CHILD, _selectedItem.mediaLink));
			}
			if (lineControl.currentControlMediaLine != null)
			{

//				trace("delete    MediaLine:", MediaLine);
				lineList.removeItemAt(lineList.getItemIndex(lineControl.currentControlMediaLine));
				lineControl.currentControlMediaLine.dispose();
				lineControl.selectNull();
			}
		}

		/**
		 * 设置右键餐单
		 */
		private function setContextMenu():void
		{
			_cm.addGroup(deleteControl, true, Vector.<String>([ContextMenuLabels.DELETE]));
//			_cm.addGroup( basicControl, true, ContextMenuLabels.BASIC_LABELS );
//			_cm.addGroup(layerControl, true, ContextMenuLabels.LAYER_LABELS);
//			_cm.addGroup( lockControl, true, ContextMenuLabels.LOCK_LABELS );
//			_cm.addGroup( maskControl, true, ContextMenuLabels.MASK_LABELS );

			_cm.addGroup(upMostControl, true, ContextMenuLabels.UP_MOST_LABELS);


			this.contextMenu=_cm.contextMenu;
		}

		private function deleteControl(eve:ContextMenuEvent):void
		{
			switch (ContextMenuItem(eve.currentTarget).caption)
			{
				case ContextMenuLabels.DELETE:
				{
					deleteSelectItem();
					break;
				}

				default:
				{
					break;
				}
			}
		}

		private function basicControl(eve:ContextMenuEvent):void
		{

		}

		private function upMostControl(eve:ContextMenuEvent):void
		{
			if (_selectedItem != null)
			{
				canvasContent.setChildIndex(DisplayObject(_selectedItem), canvasContent.numChildren - 1);
			}
			if (lineControl.currentControlMediaLine != null)
			{
				canvasContent.setChildIndex(lineControl.currentControlMediaLine, canvasContent.numChildren - 1);
			}

		}

		private function layerControl(eve:ContextMenuEvent):void
		{
			if (_selectedItem == null && (lineControl.currentControlMediaLine == null))
			{
				return;
			}
			switch (ContextMenuItem(eve.currentTarget).caption)
			{
				case ContextMenuLabels.UP:
				{
//					var layersManagerMdeiator:LayersManagerMdeiator=LayersManagerMdeiator(Facade.getInstance().retrieveMediator(LayersManagerMdeiator.NAME));
//					layersManagerMdeiator.switchItemDepth("up", getItemByUid(MediaLink(_selectedItem.mediaLink).uid));
					if (_selectedItem != null)
					{
						switchItemDepth("up", getItemByUid(MediaLink(_selectedItem.mediaLink).uid));
					}
					if (lineControl.currentControlMediaLine != null)
					{
						switchLineDepth("up", lineControl.currentControlMediaLine);
					}
					break;
				}
				case ContextMenuLabels.DOWN:
				{
//					var layersManagerMdeiator:LayersManagerMdeiator=LayersManagerMdeiator(Facade.getInstance().retrieveMediator(LayersManagerMdeiator.NAME));
//					layersManagerMdeiator.switchItemDepth("down", getItemByUid(MediaLink(_selectedItem.mediaLink).uid));


					if (_selectedItem != null)
					{
						switchItemDepth("down", getItemByUid(MediaLink(_selectedItem.mediaLink).uid));
					}
					if (lineControl.currentControlMediaLine != null)
					{
						switchLineDepth("down", lineControl.currentControlMediaLine);
					}
					break;
				}
				default:
				{
					break;
				}
			}
		}

		private function lockControl(eve:ContextMenuEvent):void
		{
		}

		private function maskControl(eve:ContextMenuEvent):void
		{
		}

		public function BuildXml():XML
		{
			var xml:XML=new XML("<Media/>");
			for (var i:int=_itemList.length - 1; i >= 0; i--)
			{
				xml.appendChild(itemBuildXml(_itemList.getItemAt(i)));
			}
//			for each (var mediaObject:MediaObject in _itemList)
//			{
//				xml.appendChild(itemBuildXml(mediaObject));
//			}
			for each (var mediaLine:MediaLine in _lineList)
			{
				xml.appendChild(itemBuildXml(mediaLine));
			}
			return xml;
		}

		public function BuildImg():ByteArray
		{
			var toolEvent:TransformToolEvent=new TransformToolEvent(TransformToolEvent.SETSELECTION);
			toolEvent.selectedUid=null;
			toolEvent.oldSelectedUid=_selectedItem != null ? _selectedItem.uid : null;
			dispatchEvent(toolEvent);

			imageID_position_dictionary=new Dictionary();
			var bytes:ByteArray=new ByteArray();
			var startPosition:uint;
			var rect:Rectangle;
			var obj:Object;
			var LINE_WIDTH:Number=1;
			for each (var mediaObject:MediaObject in _itemList)
			{
				if (mediaObject is MediaImage)
				{
					mediaObject=MediaImage(mediaObject);

					startPosition=bytes.position;

					var imageWidth:Number=MediaImage(mediaObject).imageWidth;
					var imageHeight:Number=MediaImage(mediaObject).imageHeight;
					bytes.writeUnsignedInt(imageWidth);
					bytes.writeUnsignedInt(imageHeight);


					rect=new flash.geom.Rectangle(0, 0, imageWidth, imageHeight);
					bytes.writeBytes(MediaImage(mediaObject).source.bitmapData.getPixels(rect));
					obj=new Object();
					obj.startPosition=startPosition;
					obj.endPosition=bytes.position;
					imageID_position_dictionary[mediaObject.obj_id]=obj;

				}
			}
			if (bytes.length != 0)
			{
				bytes.compress();
			}
			return bytes;
		}

		public function itemBuildXml(item:Object):XML
		{
			var xml:XML;
			if (item is MediaShape)
			{
				item=MediaShape(item);
				xml=new XML("<MediaShape/>");
				xml.@obj_id=item.obj_id;
				xml.@shapType=item.mediaLink.shapType;
				xml.@x=item.x;
				xml.@y=item.y;
				xml.@width=item.width;
				xml.@height=item.height;
				xml.@rotation=item.rotation;
				xml.@rotationX=item.rotationX;
				xml.@rotationY=item.rotationY;
				xml.@rotationZ=item.rotationZ;
				xml.@rm=item.rm;
				xml.@rq=item.rq;
				xml.@rk=item.rk;
				xml.@matrixA = item.transform.matrix.a==-1?1:item.transform.matrix.a.toString();
				xml.@matrixD = item.transform.matrix.d==-1?1:item.transform.matrix.d.toString();
				
//				xml.@matrixA = item.transform.matrix.a.toString();
//				xml.@matrixD = item.transform.matrix.d.toString();
			}
			if (item is MediaImage)
			{
				item=MediaImage(item);
				xml=new XML("<MediaImage/>");
				xml.@obj_id=item.obj_id;
//				xml.@shapType=item.mediaLink.shapType;
				xml.@x=item.x;
				xml.@y=item.y;
				xml.@width=item.width;
				xml.@height=item.height;
				xml.@imageWidth=item.imageWidth;
				xml.@imageHeight=item.imageHeight;
				xml.@rotation=item.rotation;
				xml.@rotationX=item.rotationX;
				xml.@rotationY=item.rotationY;
				xml.@rotationZ=item.rotationZ;
				xml.@rm=item.rm;
				xml.@rq=item.rq;
				xml.@rk=item.rk;
				//position
				if (imageID_position_dictionary[item.obj_id] != null)
				{
					xml.@startPosition=imageID_position_dictionary[item.obj_id].startPosition;
					xml.@endPosition=imageID_position_dictionary[item.obj_id].endPosition;
				}
				if (MediaImage(item).mediaLink.isBackground)
				{
					xml.@isBackground="1";
				}
				else
				{
					xml.@isBackground="0";
				}
			}
			if (item is MediaText)
			{
				item=MediaText(item);
				xml=new XML("<MediaText/>");
				xml.@obj_id=item.obj_id;
//				xml.@shapType=item.mediaLink.shapType;
				xml.@x=item.x;
				xml.@y=item.y;
				xml.@width=item.width;
				xml.@height=item.height;
				xml.@rotation=item.rotation;
				xml.@rotationX=item.rotationX;
				xml.@rotationY=item.rotationY;
				xml.@rotationZ=item.rotationZ;
				xml.@text=item.text;
				xml.@scaleX=item.scaleX;
				xml.@scaleY=item.scaleY;

				xml.@fontColor=item.textLink.fontColor;
				xml.@fontSize=item.textLink.fontSize;
			}
			if (item is MediaLine)
			{
				item=MediaLine(item);
				xml=new XML("<MediaLine/>");
				xml.@shapType="MediaLine";
//				xml.@x=item.x;
//				xml.@y=item.y;
//				xml.@Width=item.width;
//				xml.@Height=item.height;
				xml.@fromX=item.fromX;
				xml.@fromY=item.fromY;
				xml.@toX=item.toX;
				xml.@toY=item.toY;
			}
			return xml;
		}

		private function swapItemsAt(fromIndex:int, toIndex:int, collection:ArrayCollection):void
		{
			var fromItem:Object=collection.getItemAt(fromIndex);
			var toItem:Object=collection.getItemAt(toIndex);

			collection.setItemAt(fromItem, toIndex)
			collection.setItemAt(toItem, fromIndex);
			collection.refresh();
		}

		public function switchItemDepth(s:String, item:IMediaObject):void
		{
			var itemIndex:int=itemList.getItemIndex(item);
			if (s == "up")
			{
				if ((itemIndex != 0) && itemList.getItemAt(itemIndex - 1) != null)
				{
					var childInUp:IMediaObject=IMediaObject(itemList.getItemAt(itemIndex - 1));
					swapItemsAt(itemIndex, itemIndex - 1, itemList);
					canvasContent.swapChildren(DisplayObject(childInUp), DisplayObject(item));
				}
			}
			else
			{
				if ((itemIndex != itemList.length - 1) && itemList.getItemAt(itemIndex + 1) != null)
				{
					var childInDown:IMediaObject=IMediaObject(itemList.getItemAt(itemIndex + 1));
					swapItemsAt(itemIndex, itemIndex + 1, itemList);
					canvasContent.swapChildren(DisplayObject(childInDown), DisplayObject(item));
				}
			}
		}

		public function switchLineDepth(s:String, item:MediaLine):void
		{
			var itemIndex:int=canvasContent.getChildIndex(DisplayObject(item));
			if (s == "down")
			{
				if ((itemIndex != 0) && canvasContent.getChildAt(itemIndex - 1) != null)
				{
					canvasContent.swapChildren(DisplayObject(item), canvasContent.getChildAt(itemIndex - 1));
				}
			}
			else
			{
				if ((itemIndex != canvasContent.numChildren - 1) && canvasContent.getChildAt(itemIndex + 1) != null)
				{
					canvasContent.swapChildren(DisplayObject(item), canvasContent.getChildAt(itemIndex + 1));
				}
			}
		}

	}
}
