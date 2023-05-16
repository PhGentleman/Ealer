package org.ph.ealer.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class OssUtil implements AutoCloseable{
	OSS ossClient;
	String endpoint = "YOUR OSS ENDPOINT";
	String id = "YOUR OSS ID";
	String key = "YOUR OSS KEY";
	String bucket = "YOUR OSS BUCKET";
	
	public OssUtil() {
		ossClient = new OSSClientBuilder().build(endpoint, id, key);
	}
	
	public void upload(String path, InputStream is) {
		ossClient.putObject(bucket, "content/" + path, is);
	}
	
	public InputStream download(String path) {
		OSSObject object = ossClient.getObject(bucket, "content/" + path);
		return object.getObjectContent();
	}
	
	public void delete(String path) {
		ossClient.deleteObject(bucket, "content/" + path);
	}
	
	public URL completeShow(String path) {
		Date expiration = new Date(new Date().getTime() + 1000*60*10);
		return ossClient.generatePresignedUrl(bucket, "content/" + path, expiration);
	}

	public URL thumbnailShow(String path) {
		Date expiration = new Date(new Date().getTime() + 1000*60*10);
		return ossClient.generatePresignedUrl(bucket, "content/" + path + "/thumbnail", expiration);
	}

	public void close(){
		ossClient.shutdown();
	}
}
