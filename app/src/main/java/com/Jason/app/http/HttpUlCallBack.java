package com.Jason.app.http;

/**
 * @author: Jason
 * @date: 2016/6/3.
 * @desc: 上传类型的接口请求回调
 */
public interface HttpUlCallBack {

	/**
	 * 上传过程中的回调
	 * @param currentSize
	 * @param totalSize
	 * @param progress
	 * @param networkSpeed
	 */
	public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed);
	
	/**
	 * 上传成功的回调
	 */
	public void onSuccess();
	
	/**
	 * 上传失败的回调
	 */
	public void onFailure();
	
}
