package com.coder.nosandroid.niceosandroid.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class LocalImgSet {
	

	public static ImageDataSet<ImgInfo> getImageInfos(Context context) {
		Cursor cursor = null;
		ImageDataSet<ImgInfo> imginfos = new ImageDataSet<ImgInfo>();
		String[] proj = 
	        {
	            MediaStore.MediaColumns._ID,            // INTEGER
	            MediaStore.MediaColumns.DISPLAY_NAME,   // TEXT
	            MediaStore.MediaColumns.TITLE,          // TEXT
	            MediaStore.MediaColumns.DATA,           // TEXT
	            MediaStore.MediaColumns.SIZE,           // LONG
	            MediaStore.MediaColumns.DATE_ADDED,     // INTEGER
	            MediaStore.MediaColumns.DATE_MODIFIED,  // INTEGER
	            MediaStore.MediaColumns.MIME_TYPE,      // TEXT
	            
	            MediaStore.Images.ImageColumns.DESCRIPTION, // TEXT
	            MediaStore.Images.ImageColumns.IS_PRIVATE,  // INTEGER
	            MediaStore.Images.ImageColumns.LATITUDE,    // DOUBLE
	            MediaStore.Images.ImageColumns.LONGITUDE,   // DOUBLE
	            MediaStore.Images.ImageColumns.MINI_THUMB_MAGIC,    // INTEGER
	            MediaStore.Images.ImageColumns.BUCKET_ID,   // TEXT
	            MediaStore.Images.ImageColumns.ORIENTATION, // TEXT
	            MediaStore.Images.ImageColumns.DATE_TAKEN,  // INTEGER
	            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, // TEXT
	            MediaStore.Images.ImageColumns.PICASA_ID,   // TEXT
	        };
	        
	        Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		
		try{
			cursor = context.getContentResolver().query(baseUri, proj, null, null, null);
			for(int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToNext();
				ImgInfo imginfo = new ImgInfo();
				//fill the imge info
				imginfo.setId(cursor.getLong(0));
				imginfo.setDisplayName(cursor.getString(1));
				imginfo.setTitle(cursor.getString(2));
				imginfo.setFullPath(cursor.getString(3));
				imginfo.setSize(cursor.getLong(4));
				imginfo.setDateAdded(cursor.getLong(5));
				imginfo.setDateModified(cursor.getLong(6));
				imginfo.setMimeType(cursor.getString(7));
				imginfo.setDescription(cursor.getString(8));
				imginfo.setIsPrivate(cursor.getInt(9));
				imginfo.setLatitude(cursor.getDouble(10));
				imginfo.setLongGitude(cursor.getDouble(11));
				imginfo.setMiniThumbMagic(cursor.getInt(12));
				imginfo.setBucketId(cursor.getString(13));
				imginfo.setOrientation(cursor.getInt(14));
				imginfo.setDateTaken(cursor.getLong(15));
				imginfo.setBucketDisplayName(cursor.getString(16));
				imginfo.setPicasaId(cursor.getString(17));
				imginfo.setImageUri(Uri.withAppendedPath(baseUri, String.valueOf(imginfo.getId())));
				
				imginfo.setBytes(new byte[1024*6]);
				imginfos.add(imginfo);		
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(cursor != null)
				cursor.close();			
		}
		return imginfos;
	}
	
	
	
	
}