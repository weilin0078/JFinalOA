/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.common.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;
import com.pointlion.sys.mvc.common.dto.BootstrapValid;
import com.pointlion.sys.mvc.common.dto.RenderBean;


public abstract class BaseController extends Controller {


	
	/**
	 * 全局变量
	 */
	protected String id;				// 主键
	protected List<?> list;				// 公共list
	
	protected static String messageSuccess = "操作成功";	
	protected static String messageFail = "操作失败";
	/**
	 * 重写renderJson，避免出现IE8下出现下载弹出框
	 */
	@Override
	public void renderJson(Object object) {
		String userAgent = getRequest().getHeader("User-Agent");
		if(userAgent.toLowerCase().indexOf("msie") != -1){
			render(new JsonRender(object).forIE());
		}else{
			super.renderJson(object);
		}
	}
	
	/**
	 * 解决IE8下下载失败的问题
	 */
	@Override
	public void renderFile(File file) {
		String userAgent = getRequest().getHeader("User-Agent");
		if(userAgent.toLowerCase().indexOf("msie") != -1){
			getResponse().reset(); 
		}
		super.renderFile(file);
	}

	/**
	 * 解决IE8下下载失败的问题
	 */
	public void renderFile(File file, String downloadSaveFileName) {
		String userAgent = getRequest().getHeader("User-Agent");
		if(userAgent.toLowerCase().indexOf("msie") != -1){
			getResponse().reset(); 
		}
		renderFile(file, downloadSaveFileName);
	}
	
	/***
	 * 分页数据
	 */
	public void renderPage(Object data,String msg ,int count){
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(true);
		renderBean.setRows(data);
		renderBean.setMessage(msg);
		renderBean.setTotal(count);
		renderJson(renderBean);
	}
	
	/**
	 * 自定义render
	 * @param code 状态码
	 * @param data 返回数据
	 * @param description 描述
	 * 描述：公共render，所有的renderJson都必须返回RenderObject，包含处理状态、返回数据、失败下的状态码、失败描述
	 */
	public void renderSuccess(Object data, String description) {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(true);
		renderBean.setData(data);
		if(StrKit.isBlank(description)){
			renderBean.setMessage(messageSuccess);
		}else{
			renderBean.setMessage(description);
		}
		renderJson(renderBean);
	}
	public void renderSuccess(String message) {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(true);
		renderBean.setMessage(message);
		renderJson(renderBean);
	}
	public void renderSuccess() {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(true);
		renderBean.setMessage(messageSuccess);
		renderJson(renderBean);
	}
	public void renderValidSuccess(){
		BootstrapValid v = new BootstrapValid();
		v.setValid(true);
		renderJson(v);
	}
	public void renderValidFail(){
		BootstrapValid v = new BootstrapValid();
		v.setValid(false);
		renderJson(v);
	}
	/**
	 * 自定义render失败
	 * @param code 状态码
	 * @param data 返回数据
	 * @param description 描述
	 * 描述：公共render，所有的renderJson都必须返回RenderObject，包含处理状态、返回数据、失败下的状态码、失败描述
	 */
	public void renderError(Object data, String description) {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(false);
		renderBean.setMessage(description);
		renderJson(renderBean);
	}
	public void renderError(Object data) {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(false);
		renderBean.setMessage(messageFail);
		renderJson(renderBean);
	}
	public void renderError(String message) {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(false);
		renderBean.setMessage(message);
		renderJson(renderBean);
	}
	public void renderError() {
		RenderBean renderBean = new RenderBean();
		renderBean.setSuccess(false);
		renderBean.setMessage(messageFail);
		renderJson(renderBean);
	}

	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @return
	 */
	public <T extends BaseModel<T>> List<T> getModels(Class<? extends T> modelClass){
		String modelName = modelClass.getSimpleName();
		String prefix = StrKit.firstCharToLowerCase(modelName) + "List";
		return getModels(modelClass, prefix, false);
	}

	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param skipConvertError
	 * @return
	 */
	public <T extends BaseModel<T>> List<T> getModels(Class<? extends T> modelClass, boolean skipConvertError){
		String modelName = modelClass.getSimpleName();
		String prefix = StrKit.firstCharToLowerCase(modelName) + "List";
		return getModels(modelClass, prefix, skipConvertError);
	}
	
	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param prefix
	 * @return
	 */
	public <T extends BaseModel<T>> List<T> getModels(Class<? extends T> modelClass, String prefix){
		return getModels(modelClass, prefix, false);
	}
	
	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param prefix
	 * 
	 * 描述：
	 * 		
	 * 		表单	
	 *		<input type="hidden" name="groupList[0].ids" value="111"/>
	 *		<input type="text" name="groupList[0].names" value="222"/>
	 *		
	 *		<input type="hidden" name="groupList[1].ids" value="333"/>
	 *		<input type="text" name="groupList[1].names" value="444"/>
	 *		
	 *		<input type="hidden" name="groupList[3].ids" value="555"/>
	 *		<input type="text" name="groupList[3].names" value="666"/>
	 * 
	 * 		调用方法 
	 * 		List<Group> groupList = ToolModelInjector.injectModels(getRequest(), Group.class, "groupList");
	 * 		
	 * 		// 默认的prefix是Model类的首字母小写加上List
	 * 		List<Group> groupList = ToolModelInjector.injectModels(getRequest(), Group.class); 
	 */
	public <T extends BaseModel<T>> List<T> getModels(Class<? extends T> modelClass, String prefix, boolean skipConvertError){
		int maxIndex = 0;	// 最大的数组索引
		boolean zeroIndex = false; // 是否存在0索引
		
		String arrayPrefix = prefix + "[";
		String key = null;
		Enumeration<String> names = getRequest().getParameterNames();
		while (names.hasMoreElements()) {
			key = names.nextElement();
			if (key.startsWith(arrayPrefix) && key.indexOf("]") != -1) {
				int indexTemp = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
				
				if(indexTemp == 0){
					zeroIndex = true; // 是否存在0索引
				} 
				
				if(indexTemp > maxIndex){
					maxIndex = indexTemp; // 找到最大的数组索引
				}
			}
		}
		
		List<T> modelList = new ArrayList<T>();
		for (int i = 0; i <= maxIndex; i++) {
			if((i == 0 && zeroIndex) || i != 0){ // 避免表单空值时调用产生一个无用的值
				T baseModel = (T) getModel(modelClass, prefix + "[" + i + "]", skipConvertError);
				modelList.add(baseModel);
			}
		}
		
		return modelList;
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @return
	 */
	public <T> List<T> getBeans(Class<T> beanClass){
		String modelName = beanClass.getSimpleName();
		String prefix = StrKit.firstCharToLowerCase(modelName) + "List";
		return getBeans(beanClass, prefix, false);
	}

	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param skipConvertError
	 * @return
	 */
	public <T> List<T> getBeans(Class<T> beanClass, boolean skipConvertError){
		String modelName = beanClass.getSimpleName();
		String prefix = StrKit.firstCharToLowerCase(modelName) + "List";
		return getBeans(beanClass, prefix, skipConvertError);
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param prefix
	 * @return
	 */
	public <T> List<T> getBeans(Class<T> beanClass, String prefix){
		return getBeans(beanClass, prefix, false);
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param prefix
	 */
	public <T> List<T> getBeans(Class<T> beanClass, String prefix, boolean skipConvertError){
		int maxIndex = 0;	// 最大的数组索引
		boolean zeroIndex = false; // 是否存在0索引
		
		String arrayPrefix = prefix + "[";
		String key = null;
		Enumeration<String> names = getRequest().getParameterNames();
		while (names.hasMoreElements()) {
			key = names.nextElement();
			if (key.startsWith(arrayPrefix) && key.indexOf("]") != -1) {
				int indexTemp = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
				
				if(indexTemp == 0){
					zeroIndex = true; // 是否存在0索引
				} 
				
				if(indexTemp > maxIndex){
					maxIndex = indexTemp; // 找到最大的数组索引
				}
			}
		}
		
		List<T> beanList = new ArrayList<T>();
		for (int i = 0; i <= maxIndex; i++) {
			if((i == 0 && zeroIndex) || i != 0){ // 避免表单空值时调用产生一个无用的值
				T baseModel = (T) getBean(beanClass, prefix + "[" + i + "]", skipConvertError);
				beanList.add(baseModel);
			}
		}
		
		return beanList;
	}

	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @return
	 */
	public <T extends BaseModel<T>> Controller keepModels(Class<? extends T> modelClass) {
		String modelName = StrKit.firstCharToLowerCase(modelClass.getSimpleName());
		return keepModels(modelClass, modelName, false);
	}
	
	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param skipConvertError
	 * @return
	 */
	public <T extends BaseModel<T>> Controller keepModels(Class<? extends T> modelClass, boolean skipConvertError){
		String modelName = StrKit.firstCharToLowerCase(modelClass.getSimpleName());
		return keepModels(modelClass, modelName, skipConvertError);
	}

	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param modelName
	 * @return
	 */
	public <T extends BaseModel<T>> Controller keepModels(Class<? extends T> modelClass, String modelName) {
		return keepModels(modelClass, modelName, false);
	}
	
	/**
	 * 表单数组映射List<Model>
	 * @param modelClass
	 * @param modelName
	 * @param skipConvertError
	 * @return
	 */
	public <T extends BaseModel<T>> Controller keepModels(Class<? extends T> modelClass, String modelName, boolean skipConvertError) {
		if (StrKit.notBlank(modelName)) {
			List<T> model = getModels(modelClass, modelName, skipConvertError);
			setAttr(modelName, model);
		} else {
			keepPara();
		}
		return this;
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @return
	 */
	public <T> Controller keepBeans(Class<T> beanClass) {
		String modelName = StrKit.firstCharToLowerCase(beanClass.getSimpleName());
		return keepBeans(beanClass, modelName, false);
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param skipConvertError
	 * @return
	 */
	public <T> Controller keepBeans(Class<T> beanClass, boolean skipConvertError){
		String modelName = StrKit.firstCharToLowerCase(beanClass.getSimpleName());
		return keepBeans(beanClass, modelName, skipConvertError);
	}

	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	public <T> Controller keepBeans(Class<T> beanClass, String beanName) {
		return keepBeans(beanClass, beanName, false);
	}
	
	/**
	 * 表单数组映射List<Bean>
	 * @param beanClass
	 * @param beanName
	 * @param skipConvertError
	 * @return
	 */
	public <T> Controller keepBeans(Class<T> beanClass, String beanName, boolean skipConvertError) {
		if (StrKit.notBlank(beanName)) {
			List<T> model = getBeans(beanClass, beanName, skipConvertError);
			setAttr(beanName, model);
		} else {
			keepPara();
		}
		return this;
	}
	
	/**
	 * 获取checkbox值，数组
	 * @param name
	 * @return
	 */
	protected String[] getParas(String name) {
		return getRequest().getParameterValues(name);
	}
	

}
