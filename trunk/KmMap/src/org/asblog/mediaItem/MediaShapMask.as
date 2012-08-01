package org.asblog.mediaItem
{
	import org.asblog.core.IRelatedObject;
	import org.asblog.core.MediaMask;	

	public class MediaShapMask extends MediaMask implements IRelatedObject
	{
		private var shap : MediaShape;

		public function MediaShapMask()
		{
			super( );
			width = 80;
			height = 80;
			shap = new MediaShape( );
			addChild( shap );
			relatedObject = shap;
		}

		public function draw(_shapeType : String = null) : void
		{
			switch(_shapeType)
			{
				case MediaShape.Circle:
					shap.doDrawCircle(  );
					break;
				case MediaShape.RoundRect:
					shap.doDrawRoundRect( );
					break;
				case MediaShape.Rect:
					shap.doDrawRect( );
					break;
			}
		}
	}
}