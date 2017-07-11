var pointLion = function(){
		/***
		 * 选择一个组织结构方法
		 */
		var selectOneOrgNode={};//机构数据
		var selectOneOrg = function(callback){
			layer.open({
				  type: 2,
				  title: false, //不显示标题栏
				  area: ['300px', '550px'],
				  shade: 0.8,
				  id: 'selectOneOrg', //设定一个id，防止重复弹出
				  resize: false,
				  closeBtn: false,
				  isOutAnim : false , 
				  btn: ['确定', '取消'], 
				  btnAlign: 'c',
				  content: ctx+'/admin/org/getSelectOneOrgPage',
				  success: function(layero){
					  
				  },
				  yes: function(){
					  if( callback != null ){
						  callback(selectOneOrgNode);
					  }
					  layer.closeAll();
				  }
			});
			
		};
		//获取选择好的机构数据---提供给弹出层调用
		var  setOneOrgNode = function(treeNode){
			selectOneOrgNode = treeNode;
		};
		/***
		 * 选择多个组织结构方法
		 */
		var selectManyOrgNode;//机构数据
		var selectManyOrg = function(callback){
			layer.open({
			  type: 2,
			  title: false, //不显示标题栏
			  area: ['300px', '550px'],
			  shade: 0.8,
			  id: 'selectOneOrg', //设定一个id，防止重复弹出
			  resize: false,
			  closeBtn: false,
			  isOutAnim : false , 
			  btn: ['确定', '取消'], 
			  btnAlign: 'c',
			  content: ctx+'/admin/org/getSelectManyOrgPage',
			  success: function(layero){
				  
			  },
			  yes: function(){
				  if( callback != null ){
					  callback(selectManyOrgNode);
				  }
				  layer.closeAll();
			  }
			});
			
		};
		//获取选择好的机构数据---提供给弹出层调用
		var  setManyOrgNode = function(treeNode){
			 selectManyOrgNode = treeNode;
		};
		/***
		 * 公用弹出提醒
		 */
		var alertMsg = function(msg,type,size,callback){
			var t = "mint";//默认颜色
			var s = "small";
			if(type){
				t = type;
			}
			if(size){
				s = size;
			}
			bootbox.dialog({  
	            buttons: {  
	            	ok: {  
	                    label: '确定',
	                    className: "btn-"+t,
	                    callback : function(){
	    	            	if(callback!=null){
	    	    				callback();
	    	    			}
	    	            },
	                }  
	            },  
	            message: msg,  
	            title : '提示信息',
	            size: s,
	            animateIn: 'flipInX',
	            animateOut : 'flipOutX' 
	        });  
		};
		/***
		 * 公用弹出提醒
		 */
		var confimMsg = function(msg,size,callback){
			var s = "small";
			if(size){
				s = size;
			}
			bootbox.dialog({  
	            buttons: {
	            	cancel : {
	                	label: '取消',
	                    className: "btn-default",
	                    callback : function(){
	    	            	
	    	            }
	                },
	            	ok: {  
	                    label: '确定',
	                    className: "btn-mint",
	                    callback : function(){
	    	            	if(callback!=null){
	    	    				callback();
	    	    			}
	    	            }
	                }
	                
	            },  
	            message: msg,  
	            title : '向您确认',
	            size: s,
	            animateIn: 'swing',
	            animateOut : 'hinge'
	        });  
		};
		/***
		 * 弹出即时聊天页面
		 */
		var chatId;
		var openChat = function(uid){
			chatId = layer.open({
				  type: 2,
				  title: false, //不显示标题栏
				  area: ['500px', '552px'],
				  shade: 0.8,
				  id: 'openChat', //设定一个id，防止重复弹出
				  resize: false,
				  closeBtn: false,
				  isOutAnim : false , 
				  btnAlign: 'c',
				  content: ctx+'/admin/chat/getChatPage?id='+uid,
				  success: function(layero){
					  
				  }
				});
		};
		var closeChat = function(){
			layer.close(chatId);
		};
		return {
			selectOneOrg : selectOneOrg,
			setOneOrgNode : setOneOrgNode,
			alertMsg : alertMsg ,
			confimMsg : confimMsg,
			openChat : openChat,
			closeChat : closeChat,
			selectManyOrg : selectManyOrg,
			setManyOrgNode : setManyOrgNode 
		};
}();		